package org.xodium.illyriacore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.xodium.illyriacore.commands.ReloadCommand;
import org.xodium.illyriacore.handlers.ConfigHandler;
import org.xodium.illyriacore.handlers.ModuleHandler;
import org.xodium.illyriacore.interfaces.ACI;
import org.xodium.illyriacore.interfaces.MI;

/**
 * The main class of the IllyriaCore plugin.
 * This class extends the JavaPlugin class provided by Bukkit.
 * It handles the enable, disable, and reload events of the plugin.
 */
public class IllyriaCore extends JavaPlugin {

  public static final String PAPERMC_V = "1.21-R0.1-SNAPSHOT";
  public static final String BUKKIT_V = Bukkit.getBukkitVersion();
  public static final String COMP_V = ACI.YELLOW + "Is only compatible with " + PAPERMC_V + ACI.RESET;

  /**
   * Called when the plugin is enabled.
   * This method initializes the plugin by checking the server version, loading
   * the configuration,
   * initializing modules, and saving the default configuration.
   */
  @Override
  public void onEnable() {
    getLogger().info(MI.SERVER_VERSION + BUKKIT_V);
    if (!BUKKIT_V.contains(PAPERMC_V)) {
      getLogger().severe(COMP_V);
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    try {
      ConfigHandler configHandler = new ConfigHandler();
      CommentedConfigurationNode conf = configHandler.init(this);

      ReloadCommand.init(getLifecycleManager(), conf);

      ModuleHandler moduleHandler = new ModuleHandler();
      moduleHandler.init(this, conf);

      saveDefaultConfig();
      getLogger().info(MI.PLUGIN_ENABLED);
    } catch (ConfigurateException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reloads the configuration of the plugin.
   * This method saves the default configuration, reloads the configuration file,
   * and logs a message indicating that the plugin has been reloaded.
   */
  public void reload() {
    saveDefaultConfig();
    reloadConfig();
    getLogger().info(MI.PLUGIN_RELOADED);
  }

  /**
   * Called when the plugin is being disabled.
   * This method is automatically called by the server when the plugin is being
   * unloaded.
   * It is used to perform any necessary cleanup or finalization tasks.
   */
  @Override
  public void onDisable() {
    getLogger().info(MI.PLUGIN_DISABLED);
  }
}