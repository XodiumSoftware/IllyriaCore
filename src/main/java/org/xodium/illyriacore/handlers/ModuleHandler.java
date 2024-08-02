package org.xodium.illyriacore.handlers;

import org.bukkit.event.Listener;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.interfaces.ACI;
import org.xodium.illyriacore.interfaces.CI;
import org.xodium.illyriacore.interfaces.MI;
import org.xodium.illyriacore.modules.CustomAnvilModule;
import org.xodium.illyriacore.modules.ImmunityModule;

import java.util.HashMap;
import java.util.Map;

/**
 * The ModuleHandler class is responsible for managing modules in the
 * IllyriaCore plugin.
 * It allows modules to be initialized and registered with the server's event
 * manager.
 */
public class ModuleHandler {

    private final Map<String, Listener> modules = new HashMap<>() {
        {
            put(CI.IMMUNITY_MODULE, new ImmunityModule());
            put(CI.CUSTOM_ANVIL_MODULE, new CustomAnvilModule());
        }
    };

    /**
     * Initializes the modules based on the provided plugin and configuration.
     *
     * @param plugin The IllyriaCore plugin instance.
     * @param conf   The configuration node containing module settings.
     */
    public void init(IllyriaCore plugin, CommentedConfigurationNode conf) {
        int modulesLoaded = 0;

        for (Map.Entry<String, Listener> entry : modules.entrySet()) {
            if (conf.node(CI.MODULES_PREFIX, entry.getKey(), CI.MODULE_ENABLED).getBoolean()) {
                plugin.getServer().getPluginManager().registerEvents(entry.getValue(), plugin);
                plugin.getLogger().info(MI.LOADING + ACI.YELLOW + entry.getKey() + ACI.RESET);
                modulesLoaded++;
            }
        }
        plugin.getLogger().info(String.format(MI.LOADED + MI.MODULES_LOADED, modulesLoaded));
    }
}
