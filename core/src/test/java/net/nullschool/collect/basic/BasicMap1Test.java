package net.nullschool.collect.basic;

import net.nullschool.collect.ConstMap;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

import static net.nullschool.collect.CollectionTestingTools.*;
import static org.junit.Assert.*;


/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
public class BasicMap1Test {

    @Test
    public void test_comparison() {
        compare_maps(Collections.singletonMap("a", 1), new BasicMap1<>("a", 1));
    }

    @Test
    public void test_immutable() {
        assert_map_immutable(new BasicMap1<>("a", 1));
    }

    @Test
    public void test_with() {
        ConstMap<Object, Object> map;

        map = new BasicMap1<>("a", 1);
        compare_maps(asMap("a", 1, "b", 2), map.with("b", 2));
        compare_maps(asMap("a", 2), map.with("a", 2));
        compare_maps(asMap("a", 1, null, null), map.with(null, null));
        compare_maps(asMap("a", null), map.with("a", null));
        assertSame(map, map.with("a", 1));

        map = new BasicMap1<>(null, null);
        assertSame(map, map.with(null, null));
    }

    @Test
    public void test_withAll() {
        ConstMap<Object, Object> map = new BasicMap1<>("a", 1);
        compare_maps(asMap("a", 2, "b", 2, "c", 3), map.withAll(asMap("b", 2, "c", 3, "a", 2)));

        compare_maps(map, map.withAll(asMap("a", 1)));
        assertSame(map, map.withAll(asMap()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        new BasicMap1<>("a", 1).withAll(null);
    }

    @Test
    public void test_without() {
        ConstMap<Object, Object> map = new BasicMap1<>("a", 1);
        assertSame(BasicMap0.instance(), map.without("a"));
        assertSame(map, map.without("b"));
        assertSame(map, map.without(null));
    }

    @Test
    public void test_withoutAll() {
        ConstMap<Object, Object> map = new BasicMap1<>("a", 1);
        assertSame(BasicMap0.instance(), map.withoutAll(Arrays.asList("a")));
        assertSame(BasicMap0.instance(), map.withoutAll(Arrays.asList("b", "a", "b", "a")));
        assertSame(map, map.withoutAll(Arrays.asList("b")));
        assertSame(map, map.withoutAll(Arrays.asList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        new BasicMap1<>("a", 1).withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstMap<Object, Object> map = new BasicMap1<>("a", "1");

        out.writeObject(map);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0%net.nullschool.collect.basic.MapProxy00000001300xpw40001t01at011x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstMap<?, ?> read = (ConstMap)in.readObject();
        compare_maps(map, read);
        assertSame(map.getClass(), read.getClass());
    }

    @Test
    public void test_get_key() {
        assertEquals("a", new BasicMap1<>("a", 1).getKey(0));
    }

    @Test
    public void test_get_value() {
        assertEquals(1, new BasicMap1<>("a", 1).getValue(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_key() {
        new BasicMap1<>("a", 1).getKey(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_value() {
        new BasicMap1<>("a", 1).getValue(1);
    }

    @Test
    public void test_non_equality() {
        assertFalse(new BasicMap1<>("a", 1).equals(asMap("a", 2)));
        assertFalse(new BasicMap1<>("a", 1).equals(asMap("b", 1)));
        assertFalse(asMap("a", 2).equals(new BasicMap1<>("a", 1)));
        assertFalse(asMap("b", 1).equals(new BasicMap1<>("a", 1)));
    }
}
