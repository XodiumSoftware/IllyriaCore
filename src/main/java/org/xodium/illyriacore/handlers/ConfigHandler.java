package org.xodium.illyriacore.handlers;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.interfaces.ConfigInferface;
import org.xodium.illyriacore.interfaces.MessagesInterface;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigHandler implements ConfigInferface, MessagesInterface {

    private final Map<List<String>, Object> settings = new HashMap<>();

    public ConfigHandler() {
        settings.put(List.of(GENERAL_PREFIX, CHAT_PREFIX), "<gold>[<dark_aqua>IllyriaCore<gold>] <reset>");
        settings.put(List.of(MODULES_PREFIX, IMMUNITY_HANDLER), true);
        settings.put(List.of(MODULES_PREFIX, CUSTOM_ANVIL_HANDLER), true);
        settings.put(List.of(LOC_PREFIX, IMMUNITY_TIMER_TITLE), "<white>Immunity<reset>");
        settings.put(List.of(LOC_PREFIX, IMMUNITY_TIMER_DURATION), 10);
        settings.put(List.of(DEV_PREFIX, DEBUG), false);
    }

    public CommentedConfigurationNode init(IllyriaCore plugin) throws ConfigurateException {
        int configurationsSet = 0;

        ConfigurationLoader<CommentedConfigurationNode> loader = YamlConfigurationLoader.builder()
                .path(Paths.get(plugin.getDataFolder().toURI()).resolve(CONFIG_FILE))
                .nodeStyle(NodeStyle.BLOCK)
                .build();
        CommentedConfigurationNode conf;

        try {
            plugin.getLogger().info(LOADING + ANSI_YELLOW + CONFIG_FILE + ANSI_RESET);
            conf = loader.load();
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
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

        plugin.getLogger().info(String.format(LOADED + CONFIGS_LOADED, configurationsSet));
        return conf;
    }
}
