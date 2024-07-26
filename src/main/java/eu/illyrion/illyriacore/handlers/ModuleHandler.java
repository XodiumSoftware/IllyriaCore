package eu.illyrion.illyriacore.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import eu.illyrion.illyriacore.IllyriaCore;
import eu.illyrion.illyriacore.commands.UpdateCustomItemsCommand;
import eu.illyrion.illyriacore.configs.Config;
import eu.illyrion.illyriacore.interfaces.ConfigInferface;
import eu.illyrion.illyriacore.interfaces.MessagesInterface;

public class ModuleHandler implements MessagesInterface, ConfigInferface {

    /**
     * Loads the modules if enabled.
     */
    public static void init() {
        IllyriaCore plugin = IllyriaCore.getInstance();
        FileConfiguration conf = Config.init();
        int modulesLoaded = 0;
        if (conf.getBoolean(Config.CUSTOM_ITEM_HANDLER)) {
            CustomItemHandler.init();
            UpdateCustomItemsCommand.init(plugin.getLifecycleManager());
            plugin.getLogger().info(LOADING + conf.getString(CUSTOM_ITEM_HANDLER));
            modulesLoaded++;
        }
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
