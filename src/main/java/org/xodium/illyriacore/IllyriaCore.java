package org.xodium.illyriacore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.xodium.illyriacore.interfaces.MSG;
import org.xodium.illyriacore.listeners.EventListener;

import net.luckperms.api.LuckPermsProvider;

public class IllyriaCore extends JavaPlugin implements MSG {

  @Override
  public void onEnable() {
    getLogger().info(MSG.ILLYRIA_CORE_ENABLED);

    Map<EntityType, String> entityPermMap = new HashMap<>();
    entityPermMap.put(EntityType.GUARDIAN, "hmcwraps.guardian_set");
    entityPermMap.put(EntityType.WITHER, "hmcwraps.wither_set");
    entityPermMap.put(EntityType.WARDEN, "hmcwraps.warden_set");
    entityPermMap.put(EntityType.ENDER_DRAGON, "hmcwraps.ender_dragon_set");

    getServer().getPluginManager().registerEvents(new EventListener(LuckPermsProvider.get(), entityPermMap), this);
  }

  @Override
  public void onDisable() {
    getLogger().info(MSG.ILLYRIA_CORE_DISABLED);
  }
}