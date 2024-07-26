package eu.illyrion.illyriacore.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.checkerframework.checker.nullness.qual.NonNull;

import eu.illyrion.illyriacore.interfaces.MessagesInterface;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

public class IllyriaUtils implements MessagesInterface {

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

    /**
     * Displays a title to the specified audience.
     *
     * @param target    the audience to display the title to
     * @param maintitle the main title text (can be null or empty for no main title)
     * @param subtitle  the subtitle text (can be null or empty for no subtitle)
     */
    public static void showTitle(final @NonNull Audience target, String maintitle, String subtitle) {
        final MiniMessage mm = MiniMessage.miniMessage();
        final Component maintitle_component = (maintitle == null || maintitle.isEmpty()) ? Component.empty()
                : mm.deserialize(maintitle);
        final Component subtitle_component = (subtitle == null || subtitle.isEmpty()) ? Component.empty()
                : mm.deserialize(subtitle);
        target.showTitle(Title.title(maintitle_component, subtitle_component));
    }
}
