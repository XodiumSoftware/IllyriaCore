package org.xodium.illyriacore;

import java.util.Map;
import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.xodium.illyriacore.interfaces.CONST;
import org.xodium.illyriacore.interfaces.DEP;
import org.xodium.illyriacore.interfaces.MSG;

public class IllyriaUtils {
    public static boolean isCompatibleEnv(JavaPlugin plugin) {
        if (!plugin.getServer().getVersion().contains(DEP.VERSION)) {
            plugin.getLogger().severe(MSG.WRONG_VERSION);
            return false;
        }

        if (plugin.getServer().getPluginManager().getPlugin(DEP.LP) == null) {
            plugin.getLogger().severe(MSG.LP_MISSING);
            return false;
        }

        return true;
    }

    public static Map<EntityType, String> loadFromConfig(JavaPlugin plugin) {
        Map<EntityType, String> entityPerms = new HashMap<>();
        ConfigurationSection wrapsSection = plugin.getConfig().getConfigurationSection(CONST.WRAPS);

        if (wrapsSection != null) {
            for (String key : wrapsSection.getKeys(false)) {
                try {
                    EntityType entityType = EntityType.valueOf(key.toUpperCase());
                    String perm = wrapsSection.getString(key);
                    if (entityType != null && perm != null) {
                        entityPerms.put(entityType, perm);
                    }
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning(MSG.INVALID_ENTITY_TYPE_OR_PERM_FOR_KEY + key);
                }
            }
        }
        return entityPerms;
    }
}
