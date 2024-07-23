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
    private static final String GENERAL_PREFIX = "general.";
    public static final String CHAT_PREFIX = GENERAL_PREFIX + "prefix";
    // Modules
    private static final String MODULES_PREFIX = "modules.";
    public static final String CUSTOM_ITEM_HANDLER = MODULES_PREFIX + "CustomItems";
    public static final String IMMUNITY_HANDLER = MODULES_PREFIX + "OnJoinImmunity";
    public static final String CUSTOM_ANVIL_HANDLER = MODULES_PREFIX + "CustomAnvil";
    // Localization
    private static final String LOCALIZATION_PREFIX = "localization.";
    public static final String INITIALIZING = "Initializing";
    public static final String INITIALIZED = "Initialized";
    public static final String IMMUNITY_TIMER = LOCALIZATION_PREFIX + "ImmunityTimer";
    public static final String IMMUNITY_TIMER_DURATION = "ImmunityTimerDuration";
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

        conf.addDefault(CUSTOM_ITEM_HANDLER, false);
        conf.addDefault(IMMUNITY_HANDLER, false);
        conf.addDefault(CUSTOM_ANVIL_HANDLER, false);

        conf.addDefault(IMMUNITY_TIMER, "Immunity Timer");
        conf.addDefault(IMMUNITY_TIMER_DURATION, 10);

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
