package eu.illyrion.illyriautils.config;

import org.bukkit.configuration.file.FileConfiguration;

import eu.illyrion.illyriautils.IllyriaUtils;

import org.bstats.bukkit.Metrics;

public class Config {

    public static final String CHAT_PREFIX = "prefix";
    public static final String DEBUG = "debug";
    public static final String CHECK_FOR_UPDATES = "check-for-updates";
    public static final String CHECK_FOR_UPDATES_INTERVAL = "check-interval";

    private static final int SERVICE_ID = 22667;

    public static void init() {
        IllyriaUtils plugin = IllyriaUtils.getInstance();
        FileConfiguration conf = plugin.getConfig();
        conf.addDefault(CHAT_PREFIX, "[IllyriaUtils] ");
        conf.addDefault(DEBUG, false);
        conf.addDefault(CHECK_FOR_UPDATES, true);
        conf.addDefault(CHECK_FOR_UPDATES_INTERVAL, 4);

        new Metrics(plugin, SERVICE_ID);
    }

}
