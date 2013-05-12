package net.nullschool.grains.generate;

import net.nullschool.collect.basic.BasicConstSet;
import net.nullschool.grains.GrainProperty;
import net.nullschool.grains.GrainProperty.Flag;
import net.nullschool.grains.SimpleGrainProperty;
import net.nullschool.reflect.LateParameterizedType;
import net.nullschool.reflect.TypeTools;

import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

import static net.nullschool.grains.generate.GenerateTools.*;


/**
 * 2013-05-09<p/>
 *
 * Organizes and constructs the {@link Symbol} instances used for code generation.
 *
 * @author Cameron Beccario
 */
final class SymbolTable {

    private final Class<?> schema;
    private final TypeTable typeTable;
    private final TypePrinterFactory printerFactory;
    private final Member constPolicyMember;

    SymbolTable(Class<?> schema, TypeTable typeTable, TypePrinterFactory printerFactory, Member constPolicyMember) {
        this.schema = schema;
        this.typeTable = typeTable;
        this.printerFactory = printerFactory;
        this.constPolicyMember = constPolicyMember;
    }

    private static Type cook(Type type) {
        return new Cook().apply(type);
    }

    private Type immutify(Type type) {
        return new Immutify(typeTable).apply(type);
    }

    private static Type dewildcard(Type type) {
        return new DeWildcard().apply(type);
    }

    private static Set<Flag> flagsFor(PropertyDescriptor pd) {
        return pd.getReadMethod().getName().startsWith("is") ?
            BasicConstSet.setOf(Flag.IS_PROPERTY) :
            BasicConstSet.<Flag>emptySet();
    }

    private static List<GrainProperty> collectBeanPropertiesOf(Type type) throws IntrospectionException {
        List<GrainProperty> properties = new ArrayList<>();
        BeanInfo bi = Introspector.getBeanInfo(TypeTools.erase(type));
        for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {

            Type returnType = pd.getReadMethod().getGenericReturnType();
            LateParameterizedType lpt = asLateParameterizedType(type);
            if (lpt != null) {
                returnType = lpt.resolve(returnType);
            }
            properties.add(new SimpleGrainProperty(pd.getName(), returnType, flagsFor(pd)));
        }
        return properties;
    }

    private static List<GrainProperty> collectBeanProperties(Type type) throws IntrospectionException {
        List<GrainProperty> results = new ArrayList<>();
        Set<Type> visited = new HashSet<>();
        visited.add(null);
        Deque<Type> workList = new LinkedList<>();
        workList.add(type);

        while (!workList.isEmpty()) {

            Type current = workList.removeFirst();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            results.addAll(collectBeanPropertiesOf(current));

            workList.add(genericSuperclass(current));
            Collections.addAll(workList, genericInterfaces(current));
        }
        return results;
    }

    private static List<GrainProperty> resolveProperties(List<GrainProperty> properties) {
        // UNDONE: use a proper algorithm to handle name collisions.
        Set<String> names = new HashSet<>();
        List<GrainProperty> results = new ArrayList<>();
        for (GrainProperty prop : properties) {
            if (!names.add(prop.getName())) {
                continue;
            }
            results.add(prop);
        }
        return results;
    }

    GrainSymbol buildGrainSymbol() throws IntrospectionException {
        int typeTokenIndex = 0;

        Map<Type, TypeTokenSymbol> typeTokens = new LinkedHashMap<>();
        List<GrainProperty> properties = resolveProperties(collectBeanProperties(schema));
        Collections.sort(properties, GrainPropertyComparator.INSTANCE);

        List<PropertySymbol> symbols = new ArrayList<>();

        for (GrainProperty prop : properties) {
            Type immutableType = dewildcard(immutify(cook(prop.getType())));
            GrainProperty immutableProp = new SimpleGrainProperty(prop.getName(), immutableType, prop.getFlags());
            TypeSymbol immutableTypeSymbol = new TypeSymbol(immutableType, printerFactory);

            TypeTokenSymbol typeTokenSymbol = null;
            if (immutableType instanceof ParameterizedType) {
                typeTokenSymbol = typeTokens.get(immutableType);
                if (typeTokenSymbol == null) {
                    String name = "$" + typeTokenIndex++;
                    typeTokens.put(
                        immutableType,
                        typeTokenSymbol = new TypeTokenSymbol(
                            name,
                            immutableTypeSymbol,
                            new FieldSymbol(name + "Cast", immutableTypeSymbol)));
                }
            }
            symbols.add(new PropertySymbol(immutableProp, printerFactory, typeTokenSymbol));
        }

        Symbol constPolicyLoadExpression = null;
        if (!typeTokens.isEmpty()) {
            if (constPolicyMember instanceof Method) {
                constPolicyLoadExpression =
                    new StaticMethodInvocationExpression((Method)constPolicyMember, printerFactory);
            }
            else if (constPolicyMember instanceof Field) {
                constPolicyLoadExpression = new StaticFieldLoadExpression((Field)constPolicyMember, printerFactory);
            }
        }
        return new GrainSymbol(symbols, typeTokens.values(), constPolicyLoadExpression);
    }

    Map<String, Symbol> buildTypeSymbols() {
        Map<String, Symbol> map = new HashMap<>();
        for (Map.Entry<String, Type> entry : typeTable.wellKnownTypes().entrySet()) {
            map.put(entry.getKey(), new TypeSymbol(entry.getValue(), printerFactory));
        }
        for (Map.Entry<String, Type> entry : typeTable.schemaTypes(schema).entrySet()) {
            map.put(entry.getKey(), new TypeSymbol(entry.getValue(), printerFactory));
        }
        return map;
    }
}
