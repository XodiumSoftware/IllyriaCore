package org.xodium.illyriacore.interfaces;

import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

/**
 * The Sound Interface.
 */
public interface SI {
    /**
     * The sound played when the immunity bar is activated.
     */
    @NotNull
    Sound IMMUNITY_BAR_SOUND = Sound.sound(
            Key.key("minecraft", "block.note_block.pling"),
            Sound.Source.MASTER, 1f, 1f);
}
