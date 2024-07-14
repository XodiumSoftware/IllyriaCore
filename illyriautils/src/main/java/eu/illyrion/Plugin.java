package eu.illyrion;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * illyriautils java plugin
 */
public class Plugin extends JavaPlugin {
  private static final Logger LOGGER = Logger.getLogger("illyriautils");

  public void onEnable() {
    LOGGER.info("illyriautils enabled");
  }

  public void onDisable() {
    LOGGER.info("illyriautils disabled");
  }
}
