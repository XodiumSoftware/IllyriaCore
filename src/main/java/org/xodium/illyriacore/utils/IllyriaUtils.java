package org.xodium.illyriacore.utils;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.ConfigurateException;
import org.xodium.illyriacore.interfaces.MessagesInterface;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

public class IllyriaUtils implements MessagesInterface {

    /**
     * Displays a title to the specified audience.
     *
     * @param target    the audience to display the title to
     * @param maintitle the main title text (can be null or empty for no main title)
     * @param subtitle  the subtitle text (can be null or empty for no subtitle)
     */
    public static void showTitle(final @NonNull Audience target, String maintitle, String subtitle) {
        final Component maintitle_component = formatter(maintitle);
        final Component subtitle_component = formatter(subtitle);
        target.showTitle(Title.title(maintitle_component, subtitle_component));
    }

    /**
     * Creates a BossBar with the specified text, progress, color, and overlay.
     *
     * @param text     the text to display on the BossBar
     * @param progress the progress of the BossBar (between 0.0 and 1.0)
     * @param color    the color of the BossBar
     * @param overlay  the overlay style of the BossBar
     * @return the created BossBar
     * @throws ConfigurateException if there is an error creating the BossBar
     */
    public static BossBar createBossBar(String text, float progress, BossBar.Color color,
            BossBar.Overlay overlay) throws ConfigurateException {
        return BossBar.bossBar(formatter(text), progress, color, overlay);
    }

    /**
     * Formats a message using MiniMessage.
     *
     * @param msg the message to be formatted
     * @return the formatted message as a string
     */
    public static Component formatter(String msg) {
        return (msg == null || msg.isEmpty()) ? Component.empty() : MiniMessage.miniMessage().deserialize(msg);
    }
}
