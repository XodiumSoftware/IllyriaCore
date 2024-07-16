package eu.illyrion.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class Utils {
    private static final Pattern PATTERN = Pattern.compile("\\{(#[a-fA-F0-9]{6})\\}([^\\{]*)");

    /**
     * Parses a text string and replaces color placeholders with formatted text
     * components.
     *
     * @param text the text string to parse
     * @return the parsed text with color placeholders replaced by formatted text
     *         components
     */
    public static String parseColor(String text) {
        Matcher matcher = PATTERN.matcher(text);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String color = matcher.group(1);
            String content = matcher.group(2);
            matcher.appendReplacement(sb, Component.text(content).color(TextColor.fromHexString(color)).toString());
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    /**
     * Casts the elements of a given object to a specified type and returns a typed
     * list.
     *
     * @param obj   the object to cast
     * @param clazz the class representing the desired type
     * @param <T>   the type to cast the elements to
     * @return a list containing the casted elements
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<?> rawList = (obj instanceof List) ? (List<?>) obj : Collections.emptyList();
        List<T> typedList = new ArrayList<>();
        for (Object element : rawList) {
            if (clazz.isInstance(element)) {
                typedList.add((T) element);
            }
        }
        return typedList;
    }

    /**
     * Casts an object to a typed map with specified key and value classes.
     *
     * @param obj        the object to be casted to a map
     * @param keyClass   the class of the keys in the resulting map
     * @param valueClass the class of the values in the resulting map
     * @param <K>        the type of the keys in the resulting map
     * @param <V>        the type of the values in the resulting map
     * @return a typed map with the specified key and value classes
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> castMap(Object obj, Class<K> keyClass, Class<V> valueClass) {
        Map<?, ?> rawMap = (obj instanceof Map) ? (Map<?, ?>) obj : Collections.emptyMap();
        Map<K, V> typedMap = new HashMap<>();
        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            if (keyClass.isInstance(entry.getKey()) && valueClass.isInstance(entry.getValue())) {
                typedMap.put((K) entry.getKey(), (V) entry.getValue());
            }
        }
        return typedMap;
    }
}
