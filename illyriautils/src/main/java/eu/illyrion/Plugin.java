package eu.illyrion;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import eu.illyrion.handlers.CustomItemHandler;
import eu.illyrion.utils.Utils;

public class Plugin extends JavaPlugin {

  private static final String CUSTOM_ITEM_HANDLER_ENABLED = "CustomItemHandler.enabled";
  private static final String MODULE_YML = "module.yml";
  private static final String SERVER_VERSION_MSG = "Server version: ";
  private static final String CONFIG_FILE = "config.yml";
  private static final String DISABLED_MSG = "illyriautils disabled";
  private static final String ENABLED_MSG = "illyriautils enabled";
  private static final String COMP_VERSION_MSG = "This plugin is only compatible with Minecraft version 1.20.6";
  private static final String VERSION = Bukkit.getBukkitVersion();

  /**
   * Called when the plugin is enabled.
   * Checks if the server version is compatible and initializes custom items.
   * If the server version is not compatible, the plugin is disabled.
   */
  @Override
  public void onEnable() {
    this.getLogger().info(SERVER_VERSION_MSG + VERSION);
    if (!Utils.isCompatible(VERSION)) {
      this.getLogger().severe(COMP_VERSION_MSG);
      this.getServer().getPluginManager().disablePlugin(this);
      return;
    }

    FileConfiguration moduleConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), MODULE_YML));
    boolean customItemsEnabled = moduleConfig.getBoolean(CUSTOM_ITEM_HANDLER_ENABLED, true);
    if (customItemsEnabled) {
      CustomItemHandler.init();
    }

    this.saveResource(CONFIG_FILE, isEnabled());
    this.saveDefaultConfig();

    this.getLogger().info(ENABLED_MSG);
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