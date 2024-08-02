package org.xodium.illyriacore.interfaces;

/**
 * The Messages Interface.
 */
public interface MI {
    // General
    String NAMESPACE = "illyriacore";
    String SERVER_V = ACI.CYAN + "For PaperMC version: " + ACI.RESET;
    String PLUGIN_ENABLED = ACI.GREEN + "Successfully Enabled" + ACI.RESET;
    String PLUGIN_RELOADED = ACI.GREEN + "Successfully Reloaded" + ACI.RESET;
    String PLUGIN_DISABLED = ACI.GREEN + "Successfully Disabled" + ACI.RESET;
    String LOADING = ACI.CYAN + "Loading: " + ACI.RESET;
    String LOADED = ACI.CYAN + "Loaded: " + ACI.RESET;
    // ModuleHandler
    String MODULES_LOADED = ACI.PURPLE + "[" + ACI.RESET + "%d" + ACI.PURPLE + "] Module(s)" + ACI.RESET;
    // Config
    String CONFIGS_LOADED = ACI.PURPLE + "[" + ACI.RESET + "%d" + ACI.PURPLE + "] Config(s)" + ACI.RESET;
}