package eu.illyrion.illyriautils.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    private static final String INVALID_VERSION_FORMAT_MSG = "Invalid version format: ";

    private static final int COMP_MAJOR = 1;
    private static final int COMP_MINOR = 20;
    private static final int COMP_PATCH = 6;

    /**
     * Checks if the given version is compatible with the plugin.
     *
     * @param version the version to check
     * @return true if the version is compatible, false otherwise
     */
    public static boolean isCompatible(String version) {
        try {
            String[] parts = version.split("\\.");
            int major = Integer.parseInt(parts[0]);
            int minor = Integer.parseInt(parts[1]);
            int patch = Integer.parseInt(parts[2].split("-")[0]);

            return (major > COMP_MAJOR) || (major == COMP_MAJOR && minor > COMP_MINOR)
                    || (major == COMP_MAJOR && minor == COMP_MINOR && patch >= COMP_PATCH);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(INVALID_VERSION_FORMAT_MSG + version, e);
        }
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
