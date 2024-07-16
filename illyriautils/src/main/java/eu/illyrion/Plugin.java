package eu.illyrion;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import eu.illyrion.items.CustomItem;

public class Plugin extends JavaPlugin {
  private static final String INVALID_VERSION_FORMAT_MSG = "Invalid version format: ";
  private static final String SERVER_VERSION_MSG = "Server version: ";
  private static final String REGEX_0 = "\\.";
  private static final String REGEX_1 = "-";
  private static final String CONFIG_FILE = "config.yml";
  private static final String DISABLED_MSG = "illyriautils disabled";
  private static final String ENABLED_MSG = "illyriautils enabled";
  private static final String COMP_VERSION_MSG = "This plugin is only compatible with Minecraft version 1.20.6 and up.";

  private static final int COMP_MAJOR = 1;
  private static final int COMP_MINOR = 20;
  private static final int COMP_PATCH = 6;

  /**
   * Called when the plugin is enabled.
   * Checks if the server version is compatible and initializes custom items.
   * If the server version is not compatible, the plugin is disabled.
   */
  @Override
  public void onEnable() {
    String version = Bukkit.getBukkitVersion();
    this.getLogger().info(SERVER_VERSION_MSG + version);
    if (!isCompatible(version)) {
      getLogger().severe(COMP_VERSION_MSG);
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    saveResource(CONFIG_FILE, isEnabled());
    saveDefaultConfig();

    CustomItem.init();

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

  /**
   * Checks if the given version is compatible with the plugin.
   *
   * @param version the version to check
   * @return true if the version is compatible, false otherwise
   */
  boolean isCompatible(String version) {
    try {
      String[] parts = version.split(REGEX_0);
      int major = Integer.parseInt(parts[0]);
      int minor = Integer.parseInt(parts[1]);
      int patch = Integer.parseInt(parts[2].split(REGEX_1)[0]);

      return (major > COMP_MAJOR) || (major == COMP_MAJOR && minor > COMP_MINOR)
          || (major == COMP_MAJOR && minor == COMP_MINOR && patch >= COMP_PATCH);
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException(INVALID_VERSION_FORMAT_MSG + version, e);
    }
  }
}