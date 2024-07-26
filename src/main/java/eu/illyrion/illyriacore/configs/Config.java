package eu.illyrion.illyriacore.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import eu.illyrion.illyriacore.IllyriaCore;
import eu.illyrion.illyriacore.interfaces.ConfigInferface;

public class Config implements ConfigInferface {

    // TODO: make the config.yml be generated without having it in the resources.
    // TODO: move init to utils.
    /**
     * Initializes the configuration for the IllyriaUtils plugin.
     * This method sets default values for various configuration options.
     */
    public static FileConfiguration init() {
        IllyriaCore plugin = IllyriaCore.getInstance();
        File confFile = new File(plugin.getDataFolder(), CONFIG_FILE);
        if (!confFile.exists()) {
            try {
                confFile.getParentFile().mkdirs();
                confFile.createNewFile();
                IllyriaCore.getInstance().saveResource(CONFIG_FILE, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration conf = YamlConfiguration.loadConfiguration(confFile);

        conf.addDefault(CHAT_PREFIX, "[IllyriaUtils]");

        conf.addDefault(CUSTOM_ITEM_HANDLER, false);
        conf.addDefault(IMMUNITY_HANDLER, false);
        conf.addDefault(CUSTOM_ANVIL_HANDLER, false);

        conf.addDefault(IMMUNITY_TIMER, "Immunity");
        conf.addDefault(IMMUNITY_TIMER_DURATION, 10);

        conf.addDefault(DEBUG, false);
        conf.addDefault(DEBUG_PREFIX, "[DEBUG] ");

        if (conf.getKeys(false).isEmpty()) {
            conf.options().copyDefaults(true);
            try {
                conf.save(confFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return conf;
    }
}
