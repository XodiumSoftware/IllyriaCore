package org.xodium.illyriacore.interfaces;

import org.bukkit.Bukkit;

public interface MessagesInterface extends ANSIColorsInterface {
    // General
    String NAMESPACE = "illyriacore";
    String SERVER_VERSION = ANSI_CYAN + "For PaperMC version: " + ANSI_RESET;
    String PLUGIN_ENABLED = ANSI_GREEN + "Successfully Enabled" + ANSI_RESET;
    String PLUGIN_RELOADED = ANSI_GREEN + "Successfully Reloaded" + ANSI_RESET;
    String PLUGIN_DISABLED = ANSI_GREEN + "Successfully Disabled" + ANSI_RESET;
    String COMP_VERSION = ANSI_YELLOW + "Is only compatible with Minecraft version 1.21" + ANSI_RESET;
    String LOADING = ANSI_CYAN + "Loading: " + ANSI_RESET;
    String LOADED = ANSI_CYAN + "Loaded: " + ANSI_RESET;
    String BUKKIT_VERSION = Bukkit.getBukkitVersion() + ANSI_RESET;
    // IllyriaUtils
    String INVALID_VERSION_FORMAT = ANSI_RED + "Invalid version format: " + ANSI_RESET;
    // ModuleHandler
    String MODULES_LOADED = ANSI_PURPLE + "[" + ANSI_RESET + "%d" + ANSI_PURPLE + "] Module(s)" + ANSI_RESET;
    // Config
    String CONFIGS_LOADED = ANSI_PURPLE + "[" + ANSI_RESET + "%d" + ANSI_PURPLE + "] Config(s)" + ANSI_RESET;
}