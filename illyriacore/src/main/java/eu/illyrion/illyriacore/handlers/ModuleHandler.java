package eu.illyrion.illyriacore.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import eu.illyrion.illyriacore.IllyriaCore;
import eu.illyrion.illyriacore.commands.UpdateCustomItemsCmd;
import eu.illyrion.illyriacore.configs.Config;

public class ModuleHandler {

    private static final String INITIALIZING = "Initializing";
    private static final String INITIALIZED = "Initialized";

    /**
     * Loads the modules if enabled.
     */
    public static void init() {
        IllyriaCore plugin = IllyriaCore.getInstance();
        FileConfiguration conf = Config.init();
        int modulesLoaded = 0;

        if (conf.getBoolean(Config.CUSTOM_ITEM_HANDLER)) {
            plugin.getLogger().info(INITIALIZING + Config.CUSTOM_ITEM_HANDLER);
            CustomItemHandler.init();
            UpdateCustomItemsCmd.init(plugin.getLifecycleManager());
            plugin.getLogger().info(Config.CUSTOM_ITEM_HANDLER + INITIALIZED);
            modulesLoaded++;
        }

        if (conf.getBoolean(Config.IMMUNITY_HANDLER)) {
            plugin.getLogger().info(INITIALIZING + Config.IMMUNITY_HANDLER);
            plugin.getServer().getPluginManager().registerEvents(new ImmunityHandler(), plugin);
            plugin.getLogger().info(Config.IMMUNITY_HANDLER + INITIALIZED);
            modulesLoaded++;
        }

        if (conf.getBoolean(Config.CUSTOM_ANVIL_HANDLER)) {
            plugin.getLogger().info(INITIALIZING + Config.CUSTOM_ANVIL_HANDLER);
            plugin.getServer().getPluginManager().registerEvents(new CustomAnvilHandler(), plugin);
            plugin.getLogger().info(Config.CUSTOM_ANVIL_HANDLER + INITIALIZED);
            modulesLoaded++;
        }

        plugin.getLogger().info("[" + modulesLoaded + "] module(s) loaded.");
    }
}
