package org.xodium.illyriacore.handlers;

import org.bukkit.event.Listener;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.interfaces.ACI;
import org.xodium.illyriacore.interfaces.CI;
import org.xodium.illyriacore.interfaces.MI;
import java.util.HashMap;
import java.util.Map;

public class ModuleHandler {

    private final Map<String, Listener> modules = new HashMap<>();

    public ModuleHandler() {
        modules.put(CI.IMMUNITY_HANDLER, new ImmunityHandler());
        modules.put(CI.CUSTOM_ANVIL_HANDLER, new CustomAnvilHandler());
    }

    public void init(IllyriaCore plugin, CommentedConfigurationNode conf) {
        int modulesLoaded = 0;

        for (Map.Entry<String, Listener> entry : modules.entrySet()) {
            if (conf.node(CI.MODULES_PREFIX, entry.getKey()).getBoolean()) {
                plugin.getServer().getPluginManager().registerEvents(entry.getValue(), plugin);
                plugin.getLogger().info(MI.LOADING + ACI.YELLOW + entry.getKey() + ACI.RESET);
                modulesLoaded++;
            }
        }
        plugin.getLogger().info(String.format(MI.LOADED + MI.MODULES_LOADED, modulesLoaded));
    }
}
