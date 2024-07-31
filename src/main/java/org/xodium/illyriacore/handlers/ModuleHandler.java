package org.xodium.illyriacore.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.configs.Config;
import org.xodium.illyriacore.interfaces.ConfigInferface;
import org.xodium.illyriacore.interfaces.MessagesInterface;

import java.util.HashMap;
import java.util.Map;

public class ModuleHandler implements MessagesInterface, ConfigInferface {

    private static final Map<String, Listener> MODULES = new HashMap<>();
    private static final String MODULES_LOADED = "[%d] module(s) loaded.";

    static {
        MODULES.put(Config.IMMUNITY_HANDLER, new ImmunityHandler());
        MODULES.put(Config.CUSTOM_ANVIL_HANDLER, new CustomAnvilHandler());
    }

    /**
     * Loads the modules if enabled.
     */
    public static void init(IllyriaCore plugin) {
        FileConfiguration conf = Config.init();
        int modulesLoaded = 0;

        for (Map.Entry<String, Listener> entry : MODULES.entrySet()) {
            if (conf.getBoolean(entry.getKey())) {
                plugin.getServer().getPluginManager().registerEvents(entry.getValue(), plugin);
                plugin.getLogger().info(LOADING + conf.getString(entry.getKey()));
                modulesLoaded++;
            }
        }
        plugin.getLogger().info(String.format(MODULES_LOADED, modulesLoaded));
    }
}
