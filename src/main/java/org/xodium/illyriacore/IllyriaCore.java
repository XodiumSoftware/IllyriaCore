package org.xodium.illyriacore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.xodium.illyriacore.commands.ReloadCommand;
import org.xodium.illyriacore.handlers.ConfigHandler;
import org.xodium.illyriacore.handlers.ModuleHandler;
import org.xodium.illyriacore.interfaces.MessagesInterface;

public class IllyriaCore extends JavaPlugin implements MessagesInterface {

  public static final String PAPERMC_V = "1.21-R0.1-SNAPSHOT";
  public static final String BUKKIT_V = Bukkit.getBukkitVersion();
  public static final String COMP_V = ANSI_YELLOW + "Is only compatible with " + PAPERMC_V + ANSI_RESET;

  @Override
  public void onEnable() {
    getLogger().info(SERVER_VERSION + BUKKIT_V);
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
      getLogger().info(PLUGIN_ENABLED);
    } catch (ConfigurateException e) {
      e.printStackTrace();
    }
  }

  public void reload() {
    saveDefaultConfig();
    reloadConfig();
    getLogger().info(PLUGIN_RELOADED);
  }

  @Override
  public void onDisable() {
    getLogger().info(PLUGIN_DISABLED);
  }
}