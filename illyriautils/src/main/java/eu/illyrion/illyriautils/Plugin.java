package eu.illyrion.illyriautils;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import eu.illyrion.illyriautils.config.Config;
import eu.illyrion.illyriautils.handlers.CustomItemHandler;
import eu.illyrion.illyriautils.utils.Utils;

public class Plugin extends JavaPlugin {

  private static Plugin instance;

  private static final String CUSTOM_ITEM_HANDLER_ENABLED = "CustomItemHandler.enabled";
  private static final String MODULE_YML = "module.yml";
  private static final String SERVER_VERSION_MSG = "Server version: ";
  private static final String CONFIG_FILE = "config.yml";
  private static final String DISABLED_MSG = "illyriautils disabled";
  private static final String ENABLED_MSG = "illyriautils enabled";
  private static final String COMP_VERSION_MSG = "This plugin is only compatible with Minecraft version 1.20.6";
  private static final String VERSION = Bukkit.getBukkitVersion();

  /**
   * Returns the instance of the Plugin.
   *
   * @return the instance of the Plugin
   */
  public static Plugin getInstance() {
    return instance;
  }

  /**
   * Prints the specified debug text to the console if the debug mode is enabled.
   *
   * @param text the debug text to print
   */
  public void debug(String text) {
    if (getConfig().getBoolean(Config.DEBUG)) {
      getLogger().warning("[DEBUG] " + text);
    }
  }

  /**
   * Returns whether the plugin is in debug mode.
   *
   * @return true if the plugin is in debug mode, false otherwise.
   */
  public boolean isDebug() {
    return getConfig().getBoolean(Config.DEBUG);
  }

  /**
   * Called when the plugin is enabled.
   * Checks if the server version is compatible and initializes custom items.
   * If the server version is not compatible, the plugin is disabled.
   */
  @Override
  public void onEnable() {
    getLogger().info(SERVER_VERSION_MSG + VERSION);
    if (!Utils.isCompatible(VERSION)) {
      getLogger().severe(COMP_VERSION_MSG);
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    FileConfiguration moduleConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), MODULE_YML));
    boolean customItemsEnabled = moduleConfig.getBoolean(CUSTOM_ITEM_HANDLER_ENABLED, true);
    if (customItemsEnabled) {
      CustomItemHandler.init();
    }

    saveResource(CONFIG_FILE, isEnabled());
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
    this.getLogger().info(DISABLED_MSG);
  }
}