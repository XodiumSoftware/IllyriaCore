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

    public static CommentedConfigurationNode init() throws ConfigurateException {
        IllyriaCore plugin = JavaPlugin.getPlugin(IllyriaCore.class);
        Path confFile = Paths.get(plugin.getDataFolder().toURI()).resolve(CONFIG_FILE);
        ConfigurationLoader<CommentedConfigurationNode> loader = YamlConfigurationLoader.builder()
                .path(confFile)
                .nodeStyle(NodeStyle.BLOCK)
                .build();
        CommentedConfigurationNode conf;

        try {
            conf = loader.load();
            plugin.getLogger().info(CONFIG_LOADED_MSG);
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
        }

        conf.node(GENERAL_PREFIX, CHAT_PREFIX).set("[IllyriaCore]");

        conf.node(MODULES_PREFIX, IMMUNITY_HANDLER).set(false);
        conf.node(MODULES_PREFIX, CUSTOM_ANVIL_HANDLER).set(false);

        conf.node(LOCALIZATION_PREFIX, IMMUNITY_TIMER_TITLE).set("Immunity");
        conf.node(LOCALIZATION_PREFIX, IMMUNITY_TIMER_DURATION).set(10);

        conf.node(DEV_PREFIX, DEBUG).set(false);
        conf.node(DEV_PREFIX, DEBUG_PREFIX).set("[DEBUG] ");

        try {
            loader.save(conf);
            plugin.getLogger().info(CONFIG_SAVED_MSG);
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
        }
        return conf;
    }
}
