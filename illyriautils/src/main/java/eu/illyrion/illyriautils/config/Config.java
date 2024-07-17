package eu.illyrion.illyriautils.config;

import eu.illyrion.illyriautils.IllyriaUtils;

import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static final String GENERAL_PREFIX = "general.";
    public static final String CHAT_PREFIX = GENERAL_PREFIX + "prefix";
    public static final String CHECK_FOR_UPDATES = GENERAL_PREFIX + "check-for-updates";
    public static final String CHECK_FOR_UPDATES_INTERVAL = GENERAL_PREFIX + "check-interval";

    private static final String MODULES_PREFIX = "modules.";
    public static final String CUSTOM_ITEM_HANDLER = MODULES_PREFIX + "CustomItemHandler";

    private static final String DEVELOPMENT_PREFIX = "development.";
    public static final String DEBUG = DEVELOPMENT_PREFIX + "debug";

    private static final int METRICS_SERVICE_ID = 22667;

    /**
     * Initializes the configuration for the IllyriaUtils plugin.
     * This method sets default values for various configuration options and
     * initializes metrics.
     */
    public static void init() {
        IllyriaUtils plugin = IllyriaUtils.getInstance();
        FileConfiguration conf = plugin.getConfig();

        conf.addDefault(CHAT_PREFIX, "[IllyriaUtils]");
        conf.addDefault(CHECK_FOR_UPDATES, true);
        conf.addDefault(CHECK_FOR_UPDATES_INTERVAL, 4);

        conf.addDefault(CUSTOM_ITEM_HANDLER, true);

        conf.addDefault(DEBUG, false);

        new Metrics(plugin, METRICS_SERVICE_ID);
    }
}
