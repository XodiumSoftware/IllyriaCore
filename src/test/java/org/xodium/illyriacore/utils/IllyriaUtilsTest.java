package org.xodium.illyriacore.utils;

import org.junit.jupiter.api.Test;
import org.spongepowered.configurate.ConfigurateException;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class IllyriaUtilsTest {

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

    @Test
    public void testCreateBossBar() throws ConfigurateException {
        String text = "Test BossBar";
        float progress = 0.5f;
        BossBar.Color color = BossBar.Color.RED;
        BossBar.Overlay overlay = BossBar.Overlay.PROGRESS;

        BossBar bossBar = IllyriaUtils.createBossBar(text, progress, color, overlay);

        assertEquals(Component.text(text), bossBar.name());
        assertEquals(progress, bossBar.progress(), 0.001);
        assertEquals(color, bossBar.color());
        assertEquals(overlay, bossBar.overlay());
    }

    @Test
    public void testFormatter() {
        String input = "<red>Hello, <blue>world!";
        String expectedOutput = "Hello, world!";

        Component formattedMessage = MiniMessage.miniMessage().deserialize(input);
        String actualOutput = PlainTextComponentSerializer.plainText().serialize(formattedMessage);

        assertEquals(expectedOutput, actualOutput);
    }
}
