package org.xodium.illyriacore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.xodium.illyriacore.interfaces.MSG;
import org.xodium.illyriacore.interfaces.PERM;
import org.xodium.illyriacore.listeners.EventListener;

import net.luckperms.api.LuckPermsProvider;

public class IllyriaCore extends JavaPlugin implements MSG, PERM {

  @Override
  public void onEnable() {
    getLogger().info(MSG.ILLYRIA_CORE_ENABLED);

    Map<EntityType, String> entityPermMap = new HashMap<>();
    entityPermMap.put(EntityType.GUARDIAN, PERM.GUARDIAN);
    entityPermMap.put(EntityType.WITHER, PERM.WITHER);
    entityPermMap.put(EntityType.WARDEN, PERM.WARDEN);
    entityPermMap.put(EntityType.ENDER_DRAGON, PERM.ENDER_DRAGON);

    getServer().getPluginManager().registerEvents(new EventListener(LuckPermsProvider.get(), entityPermMap), this);
  }

  @Override
  public void onDisable() {
    getLogger().info(MSG.ILLYRIA_CORE_DISABLED);
  }
}