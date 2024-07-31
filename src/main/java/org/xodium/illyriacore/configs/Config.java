package org.xodium.illyriacore.configs;

import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.interfaces.ConfigInferface;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Config implements ConfigInferface {

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
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
        }

        conf.node(CHAT_PREFIX).set("[IllyriaCore]");
        conf.node(IMMUNITY_HANDLER).set(false);
        conf.node(CUSTOM_ANVIL_HANDLER).set(false);
        conf.node(IMMUNITY_TIMER_TITLE).set("Immunity");
        conf.node(IMMUNITY_TIMER_DURATION).set(10);
        conf.node(DEBUG).set(false);
        conf.node(DEBUG_PREFIX).set("[DEBUG] ");

        try {
            loader.save(conf);
        } catch (ConfigurateException e) {
            e.printStackTrace();
            throw e;
        }

        return conf;
    }
}
