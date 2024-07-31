package org.xodium.illyriacore;

import org.bukkit.plugin.java.JavaPlugin;

import org.xodium.illyriacore.handlers.ModuleHandler;
import org.xodium.illyriacore.interfaces.MessagesInterface;
import org.xodium.illyriacore.utils.IllyriaUtils;

public class IllyriaCore extends JavaPlugin implements MessagesInterface {

  @Override
  public void onEnable() {
    getLogger().info(SERVER_VERSION_MSG + VERSION);
    if (!IllyriaUtils.isCompatible(VERSION)) {
      getLogger().severe(COMP_VERSION_MSG);
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    ModuleHandler.init(this);
    saveDefaultConfig();
    getLogger().info(ENABLED_MSG);
  }

  public void reload() {
    saveDefaultConfig();
    reloadConfig();
  }

  @Override
  public void onDisable() {
    getLogger().info(DISABLED_MSG);
  }
}