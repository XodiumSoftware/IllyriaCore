package org.xodium.illyriacore;

import org.bukkit.plugin.java.JavaPlugin;
import org.xodium.illyriacore.commands.ReloadCommand;
import org.xodium.illyriacore.configs.Config;
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
    ModuleHandler moduleHandler = new ModuleHandler();
    moduleHandler.init(this, Config.init());
    ReloadCommand.init(getLifecycleManager());
    saveDefaultConfig();
    getLogger().info(ENABLED_MSG);
  }

  public void reload() {
    saveDefaultConfig();
    reloadConfig();
    ModuleHandler moduleHandler = new ModuleHandler();
    moduleHandler.init(this, Config.init());
  }

  @Override
  public void onDisable() {
    getLogger().info(DISABLED_MSG);
  }
}