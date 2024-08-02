package org.xodium.illyriacore.interfaces;

import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

/**
 * The Sound Interface.
 */
public interface SI {
    @NotNull
    Sound IMMUNITY_BAR = Sound.sound(
            Key.key("minecraft", "block.note_block.pling"),
            Sound.Source.MASTER, 1f, 1f);
}
