package org.xodium.illyriacore.interfaces;

import org.bukkit.Bukkit;

public interface MessagesInterface {
    // General
    String NAMESPACE = "illyriacore";
    String SERVER_VERSION_MSG = "For PaperMC version: ";
    String DISABLED_MSG = "Plugin successfully Disabled";
    String ENABLED_MSG = "Plugin successfully Enabled";
    String COMP_VERSION_MSG = "This plugin is only compatible with Minecraft version 1.20.6";
    String VERSION = Bukkit.getBukkitVersion();
    // IllyriaUtils
    String INVALID_VERSION_FORMAT_MSG = "Invalid version format: ";
    // ReloadCommand
    String RELOADING = "Reloading the plugin...";
    // ModuleHandler
    String LOADING = "Loading: ";
    // Config
    String CONFIG_LOADED_MSG = "Config loaded";
    String CONFIG_SAVED_MSG = "Config saved";
}