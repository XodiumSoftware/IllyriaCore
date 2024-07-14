package eu.illyrion;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * illyriautils java plugin
 */
public class Plugin extends JavaPlugin {
  private static final Logger LOGGER = Logger.getLogger("illyriautils");

  public void onEnable() {
    String version = Bukkit.getBukkitVersion();
    if (!isCompatible(version)) {
      getLogger().severe("This plugin is only compatible with Minecraft version 1.20.6 and up.");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    LOGGER.info("illyriautils enabled");
  }

  public void onDisable() {
    LOGGER.info("illyriautils disabled");
  }

  private boolean isCompatible(String version) {
    String[] parts = version.split("\\.");
    int major = Integer.parseInt(parts[0]);
    int minor = Integer.parseInt(parts[1]);
    int patch = Integer.parseInt(parts[2].split("-")[0]);

    return (major > 1) || (major == 1 && minor > 20) || (major == 1 && minor == 20 && patch >= 6);
  }
}
