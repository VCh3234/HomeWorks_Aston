import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class Tests {

    CustomArrayList<String> customArrayList;

    @Before
    public void beforeEach() {
        customArrayList = new CustomArrayList<>();
        customArrayList.add(0, "One");
        customArrayList.add(0, "Two");
        customArrayList.add(0, "Three");
        customArrayList.add(0, "Four");
        customArrayList.add(2, "Five");
    }

    @Test
    public void newEmpty() {
        CustomArrayList<String> customArrayList = new CustomArrayList<>();
        assertEquals(0, customArrayList.size());
        assertTrue(customArrayList.isEmpty());
    }

    @Test
    public void newEmptyWithCapacity() {
        CustomArrayList<String> customArrayList = new CustomArrayList<>(120);
        assertEquals(0, customArrayList.size());
        assertTrue(customArrayList.isEmpty());
    }

    @Test
    public void add() {
        customArrayList.add(5, "test");
        assertFalse(customArrayList.isEmpty());
        assertEquals(new String[]{"Four", "Three", "Five", "Two", "One", "test"}, customArrayList.toArray());
        assertEquals(6, customArrayList.size());
    }

    @Test
    public void capacity() {
        while (customArrayList.size() != 11) {
            customArrayList.add("test");
        }
        assertEquals(new String[]{"Four", "Three", "Five", "Two", "One", "test", "test", "test", "test", "test", "test"}, customArrayList.toArray());
        assertEquals(11, customArrayList.size());
    }

    @Test
    public void remove() {
        var oldValue = customArrayList.remove(0);
        assertEquals(new String[]{"Three", "Five", "Two", "One"}, customArrayList.toArray());
        assertEquals(4, customArrayList.size());
        assertEquals("Four", oldValue);
    }

    @Test
    public void set() {
        customArrayList.set(0, "test");
        customArrayList.set(1, null);
        assertEquals(new String[]{"test", null, "Five", "Two", "One"}, customArrayList.toArray());
        assertEquals(5, customArrayList.size());
    }

    @Test
    public void trim() throws NoSuchFieldException, IllegalAccessException {
        CustomArrayList<String> customArrayListForReflective = new CustomArrayList<>(100);
        Field field = customArrayListForReflective.getClass().getDeclaredField("elements");
        field.setAccessible(true);
        customArrayListForReflective.add("test");
        assertEquals(100, ((Object[]) field.get(customArrayListForReflective)).length);
        customArrayListForReflective.trimToSize();
        assertEquals(1, ((Object[]) field.get(customArrayListForReflective)).length);
    }

    @Test
    public void clear() {
        customArrayList.clear();
        assertArrayEquals(new String[]{}, customArrayList.toArray());
        assertEquals(0, customArrayList.size());
    }

    @Test
    public void addAll() {
        List<String> test = new ArrayList<>();
        test.add("test");
        test.add("test2");
        customArrayList.addAll(test);
        assertEquals(new String[]{"Four", "Three", "Five", "Two", "One", "test", "test2"}, customArrayList.toArray());
        assertEquals(7, customArrayList.size());
    }

    @Test
    public void get() {
        var actualItem = customArrayList.get(0);
        assertEquals("Four", actualItem);
    }

    @Test
    public void removeByValue() {
        customArrayList.remove("Four");
        assertEquals(new String[]{"Three", "Five", "Two", "One"}, customArrayList.toArray());
        assertEquals(4, customArrayList.size());
    }

    @Test
    public void exceptions() {
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.remove(140));
        assertThrows(IllegalArgumentException.class, () -> new CustomArrayList<>(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.add(-1, "test"));
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.add(6, "test"));
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.set(-1, "test"));
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.set(5, "test"));
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.remove(5));
    }

    @Test
    public void sort() {
        customArrayList.addAll(List.of(new String[]{"1", "22", "333", "4444", "55555", "666666"}));
        customArrayList.sort(Comparator.comparingInt(String::length));
        assertArrayEquals(new String[]{"1", "22", "Two", "333", "One", "Four", "4444", "Five", "Three", "55555", "666666"},
                customArrayList.toArray());
    }

}