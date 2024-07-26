package org.xodium.illyriacore.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilsTest {

    @Test
    public void testIsCompatible() {
        assertTrue(Utils.isCompatible("2.21.7"));
        assertTrue(Utils.isCompatible("1.21.6"));
        assertFalse(Utils.isCompatible("1.19.6"));
        assertThrows(IllegalArgumentException.class, () -> Utils.isCompatible("1.21"));
    }

    @Test
    public void testCastList() {
        List<Object> rawList = Arrays.asList("test", 123, true);
        List<String> stringList = Utils.castList(rawList, String.class);
        assertEquals(1, stringList.size());
        assertEquals("test", stringList.get(0));
    }

    @Test
    public void testCastMap() {
        Map<Object, Object> rawMap = new HashMap<>();
        rawMap.put("key1", "value1");
        rawMap.put("key2", 123);
        rawMap.put(123, "value3");

        Map<String, String> stringMap = Utils.castMap(rawMap, String.class, String.class);
        assertEquals(1, stringMap.size());
        assertEquals("value1", stringMap.get("key1"));
    }

    // Note: Testing `showTitle` method might require a mock `Audience` object and
    // is not included here.
}
