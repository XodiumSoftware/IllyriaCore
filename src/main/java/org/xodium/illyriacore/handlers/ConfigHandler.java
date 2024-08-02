package org.xodium.illyriacore.handlers;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.interfaces.ACI;
import org.xodium.illyriacore.interfaces.CI;
import org.xodium.illyriacore.interfaces.MI;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// TODO: adding missing keys to the correct place in the config and not just the end.
// TODO: add comments on node, have to wait till version 4.20.0.
// TODO: config count on files and not keys.

/**
 * The ConfigHandler class is responsible for handling the configuration
 * settings of the IllyriaCore plugin.
 * It provides methods to initialize the configuration, load and save the
 * configuration file, and set default values for the settings.
 */
public class ConfigHandler {

    private final Map<String, Map<List<String>, Object>> settings = new LinkedHashMap<>() {
        {
            put(CI.CONFIG_FILE, new LinkedHashMap<>() {
                {
                    put(List.of(CI.GENERAL_PREFIX, CI.CHAT_PREFIX), "<gold>[<dark_aqua>IllyriaCore<gold>] <reset>");
                    put(List.of(CI.MODULES_PREFIX, CI.IMMUNITY_MODULE, CI.MODULE_ENABLED), true);
                    put(List.of(CI.MODULES_PREFIX, CI.IMMUNITY_MODULE, CI.IMMUNITY_TIMER_TITLE),
                            "<white>Immunity<reset>");
                    put(List.of(CI.MODULES_PREFIX, CI.IMMUNITY_MODULE, CI.IMMUNITY_TIMER_DURATION), 10);
                    put(List.of(CI.MODULES_PREFIX, CI.CUSTOM_ANVIL_MODULE, CI.MODULE_ENABLED), true);
                }
            });
        }
    };

    public CommentedConfigurationNode init(IllyriaCore plugin) throws ConfigurateException {
        int configurationsSet = 0;
        CommentedConfigurationNode conf = null;

        try {
            for (Map.Entry<String, Map<List<String>, Object>> fileEntry : settings.entrySet()) {
                String configFile = fileEntry.getKey();
                Map<List<String>, Object> fileSettings = fileEntry.getValue();
                ConfigurationLoader<CommentedConfigurationNode> loader = YamlConfigurationLoader.builder()
                        .path(Paths.get(plugin.getDataFolder().toURI()).resolve(configFile))
                        .nodeStyle(NodeStyle.BLOCK)
                        .build();

                plugin.getLogger().info(MI.LOADING + ACI.YELLOW + configFile + ACI.RESET);
                conf = loader.load();
                for (Map.Entry<List<String>, Object> entry : fileSettings.entrySet()) {
                    CommentedConfigurationNode node = conf.node((Object[]) entry.getKey().toArray(new String[0]));
                    node.set(entry.getValue());
                    configurationsSet++;
                }
                loader.save(conf);
            }
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
        }
        plugin.getLogger().info(String.format(MI.LOADED + MI.CONFIGS_LOADED, configurationsSet));
        return conf;
    }
}
