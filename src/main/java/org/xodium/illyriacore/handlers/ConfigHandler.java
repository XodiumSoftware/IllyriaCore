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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: add versioning

public class ConfigHandler {

    private static final int CONFIG_V = 1;
    private static final int CONFIG_CURRENT_V = 1;

    private final Map<List<String>, Object> settings = new HashMap<>();

    public ConfigHandler() {
        settings.put(List.of(CI.GENERAL_PREFIX, CI.CHAT_PREFIX), "<gold>[<dark_aqua>IllyriaCore<gold>] <reset>");
        settings.put(List.of(CI.MODULES_PREFIX, CI.IMMUNITY_HANDLER), true);
        settings.put(List.of(CI.MODULES_PREFIX, CI.CUSTOM_ANVIL_HANDLER), true);
        settings.put(List.of(CI.LOC_PREFIX, CI.IMMUNITY_TIMER_TITLE), "<white>Immunity<reset>");
        settings.put(List.of(CI.LOC_PREFIX, CI.IMMUNITY_TIMER_DURATION), 10);
    }

    public CommentedConfigurationNode init(IllyriaCore plugin) throws ConfigurateException {
        int configurationsSet = 0;

        ConfigurationLoader<CommentedConfigurationNode> loader = YamlConfigurationLoader.builder()
                .path(Paths.get(plugin.getDataFolder().toURI()).resolve(CI.CONFIG_FILE))
                .nodeStyle(NodeStyle.BLOCK)
                .build();
        CommentedConfigurationNode conf;

        try {
            plugin.getLogger().info(MI.LOADING + ACI.YELLOW + CI.CONFIG_FILE + ACI.RESET);
            conf = loader.load();
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
        }

        if (conf.node(CONFIG_V).getInt() < CONFIG_CURRENT_V) {
            conf.node(CONFIG_V).set(CONFIG_CURRENT_V);
        }

        for (Map.Entry<List<String>, Object> entry : settings.entrySet()) {
            CommentedConfigurationNode node = conf.node((Object[]) entry.getKey().toArray(new String[0]));
            node.set(entry.getValue());
            configurationsSet++;
        }

        try {
            loader.save(conf);
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
        }

        plugin.getLogger().info(String.format(MI.LOADED + MI.CONFIGS_LOADED, configurationsSet));
        return conf;
    }
}
