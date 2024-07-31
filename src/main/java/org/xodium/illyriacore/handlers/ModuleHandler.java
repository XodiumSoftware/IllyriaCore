package org.xodium.illyriacore.handlers;

import org.bukkit.event.Listener;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.interfaces.ConfigInferface;
import org.xodium.illyriacore.interfaces.MessagesInterface;
import java.util.HashMap;
import java.util.Map;

public class ModuleHandler implements MessagesInterface, ConfigInferface {

    private final Map<String, Listener> modules = new HashMap<>();

    public ModuleHandler() {
        modules.put(IMMUNITY_HANDLER, new ImmunityHandler());
        modules.put(CUSTOM_ANVIL_HANDLER, new CustomAnvilHandler());
    }

    public void init(IllyriaCore plugin, CommentedConfigurationNode conf) {
        int modulesLoaded = 0;

        for (Map.Entry<String, Listener> entry : modules.entrySet()) {
            if (conf.node(MODULES_PREFIX, entry.getKey()).getBoolean()) {
                plugin.getServer().getPluginManager().registerEvents(entry.getValue(), plugin);
                plugin.getLogger().info(LOADING + ANSI_YELLOW + entry.getKey() + ANSI_RESET);
                modulesLoaded++;
            }
        }
        plugin.getLogger().info(String.format(LOADED + MODULES_LOADED, modulesLoaded));
    }
}
