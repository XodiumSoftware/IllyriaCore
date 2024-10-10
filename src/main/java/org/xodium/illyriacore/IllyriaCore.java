package org.xodium.illyriacore;

import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.xodium.illyriacore.interfaces.DEP;
import org.xodium.illyriacore.interfaces.MSG;
import org.xodium.illyriacore.interfaces.PERM;
import org.xodium.illyriacore.listeners.EventListener;

import net.luckperms.api.LuckPermsProvider;

public class IllyriaCore extends JavaPlugin {

  @Override
  public void onEnable() {
    if (!isCompatibleEnv()) {
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    getLogger().info(MSG.ILLYRIA_CORE_ENABLED);

    Map<EntityType, String> entityPermMap = Map.of(
        EntityType.GUARDIAN, PERM.GUARDIAN,
        EntityType.WITHER, PERM.WITHER,
        EntityType.WARDEN, PERM.WARDEN,
        EntityType.ENDER_DRAGON, PERM.ENDER_DRAGON);

    getServer().getPluginManager().registerEvents(new EventListener(LuckPermsProvider.get(), entityPermMap), this);
  }

  @Override
  public void onDisable() {
    getLogger().info(MSG.ILLYRIA_CORE_DISABLED);
  }

  private boolean isCompatibleEnv() {
    if (!getServer().getVersion().contains(DEP.VERSION)) {
      getLogger().severe(MSG.WRONG_VERSION);
      return false;
    }

    if (getServer().getPluginManager().getPlugin(DEP.LP) == null) {
      getLogger().severe(MSG.LP_MISSING);
      return false;
    }

    return true;
  }
}