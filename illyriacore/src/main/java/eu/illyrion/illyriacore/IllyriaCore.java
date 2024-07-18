package eu.illyrion.illyriacore;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import eu.illyrion.illyriacore.commands.UpdateCustomItems;
import eu.illyrion.illyriacore.config.Config;
import eu.illyrion.illyriacore.events.PlayerImmunityOnJoin;
import eu.illyrion.illyriacore.handlers.CustomItemHandler;
import eu.illyrion.illyriacore.utils.Utils;

public class IllyriaCore extends JavaPlugin {

  public static final String NAMESPACE = "illyriautils";

  private static final String DEBUG_PREFIX = "[DEBUG] ";
  private static final String MODULE_YML = "module.yml";
  private static final String SERVER_VERSION_MSG = "Server version: ";
  private static final String CONFIG_FILE = "config.yml";
  private static final String DISABLED_MSG = "illyriautils disabled";
  private static final String ENABLED_MSG = "illyriautils enabled";
  private static final String COMP_VERSION_MSG = "This plugin is only compatible with Minecraft version 1.20.6";
  private static final String VERSION = Bukkit.getBukkitVersion();

  private static IllyriaCore instance;

  /**
   * Returns the instance of the Plugin.
   *
   * @return the instance of the Plugin
   */
  public static IllyriaCore getInstance() {
    return instance;
  }

  /**
   * Prints the specified debug text to the console if the debug mode is enabled.
   *
   * @param text the debug text to print
   */
  public void debug(String text) {
    if (getConfig().getBoolean(Config.DEBUG)) {
      getLogger().warning(DEBUG_PREFIX + text);
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

    loadModule();

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
    getLogger().info(DISABLED_MSG);
  }

  /**
   * Loads the modules if enabled.
   */
  public void loadModule() {
    FileConfiguration module = YamlConfiguration.loadConfiguration(new File(getDataFolder(), MODULE_YML));

    if (module.getBoolean(Config.CUSTOM_ITEM_HANDLER, true)) {
      CustomItemHandler.init();
      UpdateCustomItems.init(getLifecycleManager());
    }

    if (module.getBoolean(Config.PLAYER_IMMUNITY_ON_JOIN, true)) {
      getServer().getPluginManager().registerEvents(new PlayerImmunityOnJoin(), this);
    }
  }

}