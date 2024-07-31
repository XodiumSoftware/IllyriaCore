package org.xodium.illyriacore;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.xodium.illyriacore.commands.ReloadCommand;
import org.xodium.illyriacore.configs.Config;
import org.xodium.illyriacore.handlers.ModuleHandler;
import org.xodium.illyriacore.interfaces.MessagesInterface;
import org.xodium.illyriacore.utils.IllyriaUtils;

public class IllyriaCore extends JavaPlugin implements MessagesInterface {

  private final ModuleHandler moduleHandler = new ModuleHandler();
  private final Logger logger = getLogger();
  private final PluginManager pluginManager = getServer().getPluginManager();

  @Override
  public void onEnable() {
    logger.info(SERVER_VERSION_MSG + VERSION);
    if (!IllyriaUtils.isCompatible(VERSION)) {
      logger.severe(COMP_VERSION_MSG);
      pluginManager.disablePlugin(this);
      return;
    }
    moduleHandler.init(this, Config.init());
    ReloadCommand.init(getLifecycleManager());
    saveDefaultConfig();
    logger.info(ENABLED_MSG);
  }

  public void reload() {
    saveDefaultConfig();
    reloadConfig();
    moduleHandler.init(this, Config.init());
  }

  @Override
  public void onDisable() {
    logger.info(DISABLED_MSG);
  }
}