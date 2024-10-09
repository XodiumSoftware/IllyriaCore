package org.xodium.illyriacore;

import org.bukkit.plugin.java.JavaPlugin;

import org.xodium.illyriacore.configs.Config;
import org.xodium.illyriacore.handlers.ModuleHandler;
import org.xodium.illyriacore.interfaces.MessagesInterface;
import org.xodium.illyriacore.utils.IllyriaUtils;

public class IllyriaCore extends JavaPlugin implements MessagesInterface {

  @Override
  public void onEnable() {
    getLogger().info("");
  }

  @Override
  public void onDisable() {
    getLogger().info("");
  }
}