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
import java.util.Map;

public class ConfigHandler implements ConfigInferface, MessagesInterface {

    private static final Map<String, Object> settings = new HashMap<>();

    public ConfigHandler() {
        settings.put(CHAT_PREFIX, "<gold>[<dark_aqua>IllyriaCore<gold>] <reset>");
        settings.put(IMMUNITY_HANDLER, true);
        settings.put(CUSTOM_ANVIL_HANDLER, true);
        settings.put(IMMUNITY_TIMER_TITLE, "Immunity");
        settings.put(IMMUNITY_TIMER_DURATION, 10);
        settings.put(DEBUG, false);
        settings.put(DEBUG_PREFIX, "[DEBUG] ");
    }

    public static CommentedConfigurationNode init(IllyriaCore plugin) throws ConfigurateException {
        int configurationsSet = 0;

        ConfigurationLoader<CommentedConfigurationNode> loader = YamlConfigurationLoader.builder()
                .path(Paths.get(plugin.getDataFolder().toURI()).resolve(CONFIG_FILE))
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

        for (Map.Entry<String, Object> entry : settings.entrySet()) {
            conf.node(entry.getKey()).set(entry.getValue());
            configurationsSet++;
        }

        try {
            loader.save(conf);
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
        }

        plugin.getLogger().info(String.format("Configurations set: %d", configurationsSet));
        return conf;
    }
}
