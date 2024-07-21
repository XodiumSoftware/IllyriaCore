package eu.illyrion.illyriacore.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import eu.illyrion.illyriacore.IllyriaCore;

public class Config {

    // TODO: make the config.yml be generated without having it in the resources
    // folder

    private static final String CONFIG_FILE = "config.yml";
    // General
    public static final String GENERAL_PREFIX = "general.";
    public static final String CHAT_PREFIX = GENERAL_PREFIX + "prefix";
    public static final String CHECK_FOR_UPDATES = GENERAL_PREFIX + "check-for-updates";
    public static final String CHECK_FOR_UPDATES_INTERVAL = GENERAL_PREFIX + "check-interval";
    // Modules
    private static final String MODULES_PREFIX = "modules.";
    public static final String CUSTOM_ITEM_HANDLER = MODULES_PREFIX + "CustomItemHandler";
    public static final String PLAYER_IMMUNITY_ON_JOIN = MODULES_PREFIX + "PlayerImmunityOnJoin";
    public static final String CUSTOM_ANVIL_OPERATIONS = MODULES_PREFIX + "CustomAnvilOperations";
    // Development
    private static final String DEVELOPMENT_PREFIX = "development.";
    public static final String DEBUG = DEVELOPMENT_PREFIX + "debug";

    /**
     * Initializes the configuration for the IllyriaUtils plugin.
     * This method sets default values for various configuration options and
     * initializes metrics.
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
        conf.addDefault(CHECK_FOR_UPDATES, true);
        conf.addDefault(CHECK_FOR_UPDATES_INTERVAL, 4);

        conf.addDefault(CUSTOM_ITEM_HANDLER, false);
        conf.addDefault(PLAYER_IMMUNITY_ON_JOIN, false);
        conf.addDefault(CUSTOM_ANVIL_OPERATIONS, false);

        conf.addDefault(DEBUG, false);

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
