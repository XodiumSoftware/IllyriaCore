package eu.illyrion;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import eu.illyrion.items.CustomItem;

/*
 * illyriautils java plugin
 */
public class Plugin extends JavaPlugin {
  private static final Logger LOGGER = Logger.getLogger("illyriautils");

  /**
   * Called when the plugin is enabled.
   * Checks if the server version is compatible and initializes custom items.
   * If the server version is not compatible, the plugin is disabled.
   */
  @Override
  public void onEnable() {
    String version = Bukkit.getBukkitVersion();
    if (!isCompatible(version)) {
      getLogger().severe("This plugin is only compatible with Minecraft version 1.20.6 and up.");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    CustomItem.init();

    LOGGER.info("illyriautils enabled");
  }

  /**
   * Called when the plugin is being disabled.
   * This method is responsible for performing any necessary cleanup or
   * finalization tasks.
   */
  @Override
  public void onDisable() {
    LOGGER.info("illyriautils disabled");
  }

  /**
   * Checks if the given version is compatible with the plugin.
   *
   * @param version the version to check
   * @return true if the version is compatible, false otherwise
   */
  boolean isCompatible(String version) {
    String[] parts = version.split("\\.");
    int major = Integer.parseInt(parts[0]);
    int minor = Integer.parseInt(parts[1]);
    int patch = Integer.parseInt(parts[2].split("-")[0]);

    return (major > 1) || (major == 1 && minor > 20) || (major == 1 && minor == 20 && patch >= 6);
  }
}