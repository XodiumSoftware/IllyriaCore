package org.xodium.illyriacore;

import org.bukkit.plugin.java.JavaPlugin;

import org.xodium.illyriacore.configs.Config;
import org.xodium.illyriacore.handlers.ModuleHandler;
import org.xodium.illyriacore.interfaces.MessagesInterface;
import org.xodium.illyriacore.utils.IllyriaUtils;

public class IllyriaCore extends JavaPlugin implements MessagesInterface {

  private final boolean IS_DEBUG = getConfig().getBoolean(Config.DEBUG);

  private static IllyriaCore instance;

  /**
   * Returns the singleton instance of the IllyriaCore class.
   *
   * @return the singleton instance of IllyriaCore
   */
  public static IllyriaCore getInstance() {
    return instance;
  }

  /**
   * Prints the specified debug text if debug mode is enabled.
   *
   * @param text the debug text to print
   */
  public void debug(String text) {
    if (IS_DEBUG) {
      getLogger().warning(Config.init().getString(Config.DEBUG_PREFIX) + text);
    }
  }

  /**
   * Returns the current debug mode status.
   *
   * @return true if the application is running in debug mode, false otherwise.
   */
  public boolean isDebug() {
    return IS_DEBUG;
  }

  /**
   * Called when the plugin is enabled.
   * Checks if the server version is compatible and initializes custom items.
   * If the server version is not compatible, the plugin is disabled.
   */
  @Override
  public void onEnable() {
    instance = this;
    getLogger().info(SERVER_VERSION_MSG + VERSION);
    if (!IllyriaUtils.isCompatible(VERSION)) {
      getLogger().severe(COMP_VERSION_MSG);
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    ModuleHandler.init();
    saveDefaultConfig();
    getLogger().info(ENABLED_MSG);
  }

  /**
   * Reloads the plugin configuration.
   * This method saves the default configuration and reloads the configuration
   * from file.
   */
  public void reload() {
    saveDefaultConfig();
    reloadConfig();
  }

  /**
   * Called when the plugin is being disabled.
   * This method is responsible for performing any necessary cleanup or
   * finalization tasks.
   */
  @Override
  public void onDisable() {
    getLogger().info(DISABLED_MSG);
  }
}