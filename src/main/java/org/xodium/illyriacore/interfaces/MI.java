package org.xodium.illyriacore.interfaces;

import org.bukkit.Bukkit;

/**
 * The Messages Interface.
 */
public interface MI {
    // General
    String NAMESPACE = "illyriacore";
    String SERVER_VERSION = ACI.CYAN + "For PaperMC version: " + ACI.RESET;
    String PLUGIN_ENABLED = ACI.GREEN + "Successfully Enabled" + ACI.RESET;
    String PLUGIN_RELOADED = ACI.GREEN + "Successfully Reloaded" + ACI.RESET;
    String PLUGIN_DISABLED = ACI.GREEN + "Successfully Disabled" + ACI.RESET;
    String LOADING = ACI.CYAN + "Loading: " + ACI.RESET;
    String LOADED = ACI.CYAN + "Loaded: " + ACI.RESET;
    String BUKKIT_VERSION = Bukkit.getBukkitVersion();
    // IllyriaUtils
    String INVALID_VERSION_FORMAT = ACI.RED + "Invalid version format: " + ACI.RESET;
    // ModuleHandler
    String MODULES_LOADED = ACI.PURPLE + "[" + ACI.RESET + "%d" + ACI.PURPLE + "] Module(s)" + ACI.RESET;
    // Config
    String CONFIGS_LOADED = ACI.PURPLE + "[" + ACI.RESET + "%d" + ACI.PURPLE + "] Config(s)" + ACI.RESET;
}