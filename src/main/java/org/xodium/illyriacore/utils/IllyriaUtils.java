package org.xodium.illyriacore.utils;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.ConfigurateException;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

/**
 * The Illyria Utils class.
 */
public class IllyriaUtils {

    /**
     * Displays a title to the specified audience.
     *
     * @param audience  the audience to display the title to
     * @param maintitle the main title of the title (can be null or empty)
     * @param subtitle  the subtitle of the title (can be null or empty)
     */
    public static void showTitle(final @NonNull Audience audience, String maintitle, String subtitle) {
        audience.showTitle(Title.title(formatter(maintitle), formatter(subtitle)));
    }

    /**
     * Creates a BossBar with the specified text, progress, color, and overlay.
     *
     * @param txt      the text to be displayed on the BossBar (can be null or
     *                 empty)
     * @param progress the progress of the BossBar (between 0.0 and 1.0)
     * @param color    the color of the BossBar
     * @param overlay  the overlay style of the BossBar
     * @return the created BossBar
     * @throws ConfigurateException if there is an error creating the BossBar
     */
    public static BossBar createBossBar(String txt, float progress, BossBar.Color color,
            BossBar.Overlay overlay) throws ConfigurateException {
        return BossBar.bossBar(formatter(txt), progress, color, overlay);
    }

    /**
     * Formats the given text into a Minecraft component.
     *
     * @param txt the text to be formatted (can be null or empty)
     * @return the formatted Minecraft component
     */
    public static Component formatter(String txt) {
        return (txt == null || txt.isEmpty()) ? Component.empty() : MiniMessage.miniMessage().deserialize(txt);
    }
}
