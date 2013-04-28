package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSortedSet;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static net.nullschool.collect.CollectionTestingTools.*;
import static java.util.Collections.*;

/**
 * 2013-04-25<p/>
 *
 * @author Cameron Beccario
 */
public class BasicSortedSet1Test {

    @Test
    public void test_comparison() {
        compare_sorted_sets(asSortedSet(null, 1), new BasicSortedSet1<>(null, 1), 0, 1, 1, 2, 0);
        compare_sorted_sets(asSortedSet(reverseOrder(), 1), new BasicSortedSet1<>(reverseOrder(), 1), 2, 1, 1, 0, 2);
    }

    @Test
    public void test_immutable() {
        assert_sorted_set_immutable(new BasicSortedSet1<>(null, 1));
    }

    @Test
    public void test_with() {
        ConstSortedSet<Integer> set;

        set = new BasicSortedSet1<>(null, 1);
        compare_sorted_sets(asSortedSet(null, 1, 2), set.with(2));
        assertSame(set, set.with(1));

        set = new BasicSortedSet1<>(reverseOrder(), 1);
        compare_sorted_sets(asSortedSet(reverseOrder(), 2, 1), set.with(2));
        assertSame(set, set.with(1));
    }

    @Test
    public void test_withAll() {
        ConstSortedSet<Integer> set;

        set = new BasicSortedSet1<>(null, 1);
        compare_sorted_sets(asSortedSet(null, 1, 2, 3), set.withAll(Arrays.asList(1, 2, 3, 3, 2, 1)));
        assertSame(set, set.withAll(Arrays.asList(1, 1, 1, 1, 1, 1)));
        assertSame(set, set.withAll(Collections.<Integer>emptyList()));

        set = new BasicSortedSet1<>(reverseOrder(), 1);
        compare_sorted_sets(asSortedSet(reverseOrder(), 3, 2, 1), set.withAll(Arrays.asList(1, 2, 3, 3, 2, 1)));
        assertSame(set, set.withAll(Arrays.asList(1, 1, 1, 1, 1, 1)));
        assertSame(set, set.withAll(Collections.<Integer>emptyList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        new BasicSortedSet1<>(null, 1).withAll(null);
    }

    @Test
    public void test_without() {
        ConstSortedSet<Integer> set;

        set = new BasicSortedSet1<>(null, 1);
        compare_sorted_sets(BasicSortedSet0.instance(null), set.without(1));
        assertSame(set, set.without(2));

        set = new BasicSortedSet1<>(reverseOrder(), 1);
        compare_sorted_sets(BasicSortedSet0.instance(reverseOrder()), set.without(1));
        assertSame(set, set.without(2));
    }

    @Test
    public void test_withoutAll() {
        ConstSortedSet<Integer> set;

        set = new BasicSortedSet1<>(null, 1);
        compare_sorted_sets(BasicSortedSet0.instance(null), set.withoutAll(Arrays.asList(1)));
        compare_sorted_sets(BasicSortedSet0.instance(null), set.withoutAll(Arrays.asList(2, 1)));
        assertSame(set, set.withoutAll(Arrays.asList(2)));
        assertSame(set, set.withoutAll(Arrays.asList()));

        set = new BasicSortedSet1<>(reverseOrder(), 1);
        compare_sorted_sets(BasicSortedSet0.instance(reverseOrder()), set.withoutAll(Arrays.asList(1)));
        compare_sorted_sets(BasicSortedSet0.instance(reverseOrder()), set.withoutAll(Arrays.asList(2, 1)));
        assertSame(set, set.withoutAll(Arrays.asList(2)));
        assertSame(set, set.withoutAll(Arrays.asList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        new BasicSortedSet1<>(null, 1).withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedSet<Integer> set = new BasicSortedSet1<>(null, "a");

        out.writeObject(set);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedSetProxy00000001300xppw40001t01ax",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSortedSet<?> read = (ConstSortedSet)in.readObject();
        compare_sorted_sets(set, read);
        assertSame(set.getClass(), read.getClass());
    }

    @Test
    public void test_serialization_with_comparator() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedSet<Integer> set = new BasicSortedSet1<>(reverseOrder(), "a");

        out.writeObject(set);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedSetProxy00000001300xpsr0'" +
                "java.util.Collections$ReverseComparatord48af0SNJd0200xpw40001t01ax",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSortedSet<?> read = (ConstSortedSet)in.readObject();
        compare_sorted_sets(set, read);
        assertSame(set.getClass(), read.getClass());
    }

    @Test
    public void test_get() {
        assertEquals(1, new BasicSortedSet1<>(null, 1).get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get() {
        new BasicSortedSet1<>(null, 1).get(1);
    }

    @Test
    public void test_non_equality() {
        assertFalse(new BasicSortedSet1<>(null, 1).equals(asSortedSet(null, 2)));
        assertFalse(asSortedSet(null, 2).equals(new BasicSortedSet1<>(null, 1)));
    }

    @Test
    public void test_different_comparators_still_equal() {
        assertTrue(new BasicSortedSet1<>(null, 1).equals(new BasicSortedSet1<>(reverseOrder(), 1)));
    }
}
