package net.nullschool.grains.generate.model;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.ConstSortedMap;
import net.nullschool.collect.IteratorTools;
import net.nullschool.collect.MapIterator;
import net.nullschool.collect.MapTools;
import net.nullschool.collect.basic.BasicCollections;
import net.nullschool.grains.AbstractGrain;
import net.nullschool.grains.AbstractGrainBuilder;
import net.nullschool.grains.AbstractGrainProxy;
import net.nullschool.grains.GrainFactory;
import net.nullschool.grains.GrainFactoryRef;
import net.nullschool.grains.GrainProperty;
import net.nullschool.grains.GrainTools;
import net.nullschool.grains.SimpleGrainProperty;
import net.nullschool.reflect.PublicInterfaceRef;

/**
 * Factory for constructing Grain instances of Composed.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
public enum ComposedFactory implements GrainFactory {
    INSTANCE;

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.asPropertyMap(
        new SimpleGrainProperty("id", String.class),
        new SimpleGrainProperty("age", int.class),
        new SimpleGrainProperty("giant", boolean.class, GrainProperty.Flag.IS_PROPERTY),
        new SimpleGrainProperty("legCount", int.class),
        new SimpleGrainProperty("name", String.class));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[5]);
    private static final ComposedGrain $DEFAULT = newBuilder().build();
    public static ComposedGrain defaultValue() { return $DEFAULT; }
    public static ComposedBuilder newBuilder() { return new ComposedBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public ComposedGrain getDefaultValue() { return defaultValue(); }
    public ComposedBuilder getNewBuilder() { return newBuilder(); }
    public String toString() { return getClass().getName(); }

    /**
     * Code generated implementation of ComposedGrain.
     */
    @PublicInterfaceRef(ComposedGrain.class)
    @GrainFactoryRef(ComposedFactory.class)
    private static final class ComposedGrainImpl
        extends AbstractGrain
        implements ComposedGrain, Serializable {

        private final String id;
        private final int age;
        private final boolean giant;
        private final int legCount;
        private final String name;

        private final ConstSortedMap<String, Object> $extensions;

        private ComposedGrainImpl(
            String id, int age, boolean giant, int legCount, String name, 
            ConstSortedMap<String, Object> $extensions) {

            this.id = id;
            this.age = age;
            this.giant = giant;
            this.legCount = legCount;
            this.name = name;
            this.$extensions = $extensions;
        }

        public int size() { return 5 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), $extensions.iterator());
        }

        public String getId() { return id; }
        public ComposedGrain withId(String id) {
            return new ComposedGrainImpl(
                id, age, giant, legCount, name, 
                $extensions);
        }

        public int getAge() { return age; }
        public ComposedGrain withAge(int age) {
            return new ComposedGrainImpl(
                id, age, giant, legCount, name, 
                $extensions);
        }

        public boolean isGiant() { return giant; }
        public ComposedGrain withGiant(boolean giant) {
            return new ComposedGrainImpl(
                id, age, giant, legCount, name, 
                $extensions);
        }

        public int getLegCount() { return legCount; }
        public ComposedGrain withLegCount(int legCount) {
            return new ComposedGrainImpl(
                id, age, giant, legCount, name, 
                $extensions);
        }

        public String getName() { return name; }
        public ComposedGrain withName(String name) {
            return new ComposedGrainImpl(
                id, age, giant, legCount, name, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "age": return getAge();
                case "giant": return isGiant();
                case "legCount": return getLegCount();
                case "name": return getName();
                default: return $extensions.get($key);
            }
        }

        private ComposedGrain with(String $key, Object $value, boolean $dissoc) {
            switch ($key) {
                case "id": return withId((String)$value);
                case "age": return withAge($value == null ? 0 : (int)$value);
                case "giant": return withGiant($value == null ? false : (boolean)$value);
                case "legCount": return withLegCount($value == null ? 0 : (int)$value);
                case "name": return withName((String)$value);
            }
            ConstSortedMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new ComposedGrainImpl(
                id, age, giant, legCount, name, 
                $newExtensions);
        }

        public ComposedGrain with(String $key, Object $value) {
            return with($key, $value, false);
        }

        public ComposedGrain withAll(Map<? extends String, ?> $map) {
            return $map.isEmpty() ? this : MapTools.putAll(newBuilder(), $map).build();
        }

        public ComposedGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public ComposedGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(newBuilder(), $keys).build();
        }

        public ComposedBuilder newBuilder() {
            ComposedBuilderImpl $builder = new ComposedBuilderImpl();
            $builder.id = this.id;
            $builder.age = this.age;
            $builder.giant = this.giant;
            $builder.legCount = this.legCount;
            $builder.name = this.name;
            $builder.$extensions.putAll(this.$extensions);
            return $builder;
        }

        public ConstMap<String, Object> extensions() {
            return $extensions;
        }

        private Object writeReplace() { return new ComposedGrainProxy().setPayload(this); }
        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("proxy expected");
        }
    }

    /**
     * Code generated serialization proxy for serializing instances of ComposedGrainImpl.
     */
    private static final class ComposedGrainProxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        protected ComposedBuilder newBuilder() { return ComposedFactory.newBuilder(); }
    }

    /**
     * Code generated implementation of ComposedBuilder.
     */
    @PublicInterfaceRef(ComposedBuilder.class)
    @GrainFactoryRef(ComposedFactory.class)
    private static final class ComposedBuilderImpl
        extends AbstractGrainBuilder
        implements ComposedBuilder {

        private String id;
        private int age;
        private boolean giant;
        private int legCount;
        private String name;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 5 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public String getId() { return id; }
        public ComposedBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public int getAge() { return age; }
        public ComposedBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public boolean isGiant() { return giant; }
        public ComposedBuilder setGiant(boolean giant) {
            this.giant = giant;
            return this;
        }

        public int getLegCount() { return legCount; }
        public ComposedBuilder setLegCount(int legCount) {
            this.legCount = legCount;
            return this;
        }

        public String getName() { return name; }
        public ComposedBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "age": return getAge();
                case "giant": return isGiant();
                case "legCount": return getLegCount();
                case "name": return getName();
                default: return $extensions.get($key);
            }
        }

        private Object put(String $key, Object $value, boolean $dissoc) {
            Object $original;
            switch ($key) {
                case "id":
                    $original = getId();
                    setId((String)$value);
                    return $original;
                case "age":
                    $original = getAge();
                    setAge($value == null ? 0 : (int)$value);
                    return $original;
                case "giant":
                    $original = isGiant();
                    setGiant($value == null ? false : (boolean)$value);
                    return $original;
                case "legCount":
                    $original = getLegCount();
                    setLegCount($value == null ? 0 : (int)$value);
                    return $original;
                case "name":
                    $original = getName();
                    setName((String)$value);
                    return $original;
                default:
                    return $dissoc ? $extensions.remove($key) : $extensions.put($key, $value);
            }
        }

        public Object put(String $key, Object $value) {
            return put($key, $value, false);
        }

        public Object remove(Object $key) {
            return put((String)$key, null, true);
        }

        public ComposedGrain build() {
            return new ComposedGrainImpl(
                id, age, giant, legCount, name, 
                BasicCollections.asSortedMap($extensions));
        }
    }
}
