package org.xodium.illyriacore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.xodium.illyriacore.interfaces.msg;
import org.xodium.illyriacore.listeners.EventListener;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

public class IllyriaCore extends JavaPlugin implements msg {

  @Override
  public void onEnable() {
    getLogger().info(msg.ILLYRIA_CORE_ENABLED);

    Map<EntityType, String> entityPermMap = new HashMap<>();
    entityPermMap.put(EntityType.ZOMBIE, "zombie.permission.node");

    LuckPerms lp = LuckPermsProvider.get();
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new EventListener(lp, entityPermMap), this);
  }

  @Override
  public void onDisable() {
    getLogger().info(msg.ILLYRIA_CORE_DISABLED);
  }
}