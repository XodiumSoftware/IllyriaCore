package org.xodium.illyriacore.configs;

import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.interfaces.ConfigInferface;
import org.xodium.illyriacore.interfaces.MessagesInterface;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Config implements ConfigInferface, MessagesInterface {

    // TODO: Make this a handler. and add counter.

    /**
     * Initializes the configuration for the IllyriaCore plugin.
     * This method loads the configuration from the specified file,
     * sets default values for the configuration options, and saves
     * the configuration back to the file if necessary.
     *
     * @return The root node of the loaded configuration.
     * @throws ConfigurateException If an error occurs while loading or saving the
     *                              configuration.
     */
    public static CommentedConfigurationNode init() throws ConfigurateException {
        IllyriaCore plugin = JavaPlugin.getPlugin(IllyriaCore.class);
        Path confFile = Paths.get(plugin.getDataFolder().toURI()).resolve(CONFIG_FILE);
        ConfigurationLoader<CommentedConfigurationNode> loader = YamlConfigurationLoader.builder()
                .path(confFile)
                .nodeStyle(NodeStyle.BLOCK)
                .build();
        CommentedConfigurationNode conf;

        try {
            plugin.getLogger().info(LOADING + ANSI_YELLOW + CONFIG_FILE + ANSI_RESET);
            conf = loader.load();
            plugin.getLogger().info(LOADED + CONFIG_HANDLER);
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
        }

        conf.node(GENERAL_PREFIX).commentIfAbsent("General settings.");
        conf.node(GENERAL_PREFIX, CHAT_PREFIX).set("[IllyriaCore]");

        conf.node(MODULES_PREFIX).commentIfAbsent("Modules to load, set to true to enable.");
        conf.node(MODULES_PREFIX, IMMUNITY_HANDLER).set(true);
        conf.node(MODULES_PREFIX, CUSTOM_ANVIL_HANDLER).set(true);

        conf.node(LOCALIZATION_PREFIX).commentIfAbsent("Localization settings.");
        conf.node(LOCALIZATION_PREFIX, IMMUNITY_TIMER_TITLE).set("Immunity");
        conf.node(LOCALIZATION_PREFIX, IMMUNITY_TIMER_DURATION).set(10);

        conf.node(DEV_PREFIX).commentIfAbsent("Developer settings, DO NOT change unless you know what you are doing.");
        conf.node(DEV_PREFIX, DEBUG).set(false);
        conf.node(DEV_PREFIX, DEBUG_PREFIX).set("[DEBUG] ");

        try {
            loader.save(conf);
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
        }
        return conf;
    }
}
