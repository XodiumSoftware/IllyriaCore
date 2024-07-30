package org.xodium.illyriacore.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.configs.Config;
import org.xodium.illyriacore.interfaces.ConfigInferface;
import org.xodium.illyriacore.interfaces.MessagesInterface;

public class ModuleHandler implements MessagesInterface, ConfigInferface {

    /**
     * Loads the modules if enabled.
     */
    public static void init() {
        IllyriaCore plugin = IllyriaCore.getInstance();
        FileConfiguration conf = Config.init();
        int modulesLoaded = 0;
        if (conf.getBoolean(Config.IMMUNITY_HANDLER)) {
            plugin.getServer().getPluginManager().registerEvents(new ImmunityHandler(), plugin);
            plugin.getLogger().info(LOADING + conf.getString(IMMUNITY_HANDLER));
            modulesLoaded++;
        }
        if (conf.getBoolean(Config.CUSTOM_ANVIL_HANDLER)) {
            plugin.getServer().getPluginManager().registerEvents(new CustomAnvilHandler(), plugin);
            plugin.getLogger().info(LOADING + conf.getString(CUSTOM_ANVIL_HANDLER));
            modulesLoaded++;
        }
        plugin.getLogger().info("[" + modulesLoaded + "] module(s) loaded.");
    }
}
