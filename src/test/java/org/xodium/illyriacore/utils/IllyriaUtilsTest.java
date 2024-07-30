package org.xodium.illyriacore.utils;

import org.junit.jupiter.api.Test;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IllyriaUtilsTest {

    @Test
    public void testIsCompatible() {
        assertTrue(IllyriaUtils.isCompatible("2.21.7"));
        assertTrue(IllyriaUtils.isCompatible("1.21.6"));
        assertFalse(IllyriaUtils.isCompatible("1.19.6"));
        assertThrows(IllegalArgumentException.class, () -> IllyriaUtils.isCompatible("1.21"));
    }

    @Test
    public void testCastList() {
        List<Object> rawList = Arrays.asList("test", 123, true);
        List<String> stringList = IllyriaUtils.castList(rawList, String.class);
        assertEquals(1, stringList.size());
        assertEquals("test", stringList.get(0));
    }

    @Test
    public void testCastMap() {
        Map<Object, Object> rawMap = new HashMap<>();
        rawMap.put("key1", "value1");
        rawMap.put("key2", 123);
        rawMap.put(123, "value3");

        Map<String, String> stringMap = IllyriaUtils.castMap(rawMap, String.class, String.class);
        assertEquals(1, stringMap.size());
        assertEquals("value1", stringMap.get("key1"));
    }

    @Test
    public void testShowTitle() {
        Audience mockAudience = mock(Audience.class);
        String mainTitle = "<red>Main Title";
        String subTitle = "<blue>Sub Title";

        IllyriaUtils.showTitle(mockAudience, mainTitle, subTitle);

        verify(mockAudience).showTitle(argThat(title -> {
            Component mainTitleComponent = MiniMessage.miniMessage().deserialize(mainTitle);
            Component subTitleComponent = MiniMessage.miniMessage().deserialize(subTitle);
            return title.title().equals(mainTitleComponent) && title.subtitle().equals(subTitleComponent);
        }));
    }
}
