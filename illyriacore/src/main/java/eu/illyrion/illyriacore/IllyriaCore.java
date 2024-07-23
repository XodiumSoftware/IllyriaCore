package eu.illyrion.illyriacore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import eu.illyrion.illyriacore.commands.UpdateCustomItemsCmd;
import eu.illyrion.illyriacore.config.Config;
import eu.illyrion.illyriacore.handlers.CustomAnvilHandler;
import eu.illyrion.illyriacore.handlers.CustomItemHandler;
import eu.illyrion.illyriacore.handlers.ImmunityHandler;
import eu.illyrion.illyriacore.utils.Utils;

public class IllyriaCore extends JavaPlugin {

  public static final String NAMESPACE = "illyriacore";

  private static final String DEBUG_PREFIX = "[DEBUG] ";
  private static final String SERVER_VERSION_MSG = "For PaperMC version: ";
  private static final String DISABLED_MSG = "Plugin successfully Disabled";
  private static final String ENABLED_MSG = "Plugin successfully Enabled";
  private static final String COMP_VERSION_MSG = "This plugin is only compatible with Minecraft version 1.20.6";
  private static final String INITIALIZING = "Initializing";
  private static final String INITIALIZED = "Initialized";
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
    instance = this;

    getLogger().info(SERVER_VERSION_MSG + VERSION);

    if (!Utils.isCompatible(VERSION)) {
      getLogger().severe(COMP_VERSION_MSG);
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    loadModule();

    saveDefaultConfig();

    getLogger().info(ENABLED_MSG);
  }

  /**
   * Loads the modules if enabled.
   */
  public void loadModule() {
    FileConfiguration conf = Config.init();
    int modulesLoaded = 0;

    if (conf.getBoolean(Config.CUSTOM_ITEM_HANDLER)) {
      getLogger().info(INITIALIZING + Config.CUSTOM_ITEM_HANDLER);
      CustomItemHandler.init();
      UpdateCustomItemsCmd.init(getLifecycleManager());
      getLogger().info(Config.CUSTOM_ITEM_HANDLER + INITIALIZED);
      modulesLoaded++;
    }

    if (conf.getBoolean(Config.IMMUNITY_HANDLER)) {
      getLogger().info(INITIALIZING + Config.IMMUNITY_HANDLER);
      getServer().getPluginManager().registerEvents(new ImmunityHandler(), this);
      getLogger().info(Config.IMMUNITY_HANDLER + INITIALIZED);
      modulesLoaded++;
    }

    if (conf.getBoolean(Config.CUSTOM_ANVIL_HANDLER)) {
      getLogger().info(INITIALIZING + Config.CUSTOM_ANVIL_HANDLER);
      getServer().getPluginManager().registerEvents(new CustomAnvilHandler(), this);
      getLogger().info(Config.CUSTOM_ANVIL_HANDLER + INITIALIZED);
      modulesLoaded++;
    }

    getLogger().info("[" + modulesLoaded + "] module(s) loaded.");
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