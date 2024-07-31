package org.xodium.illyriacore;

import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.xodium.illyriacore.commands.ReloadCommand;
import org.xodium.illyriacore.handlers.ConfigHandler;
import org.xodium.illyriacore.handlers.ModuleHandler;
import org.xodium.illyriacore.interfaces.MessagesInterface;
import org.xodium.illyriacore.utils.IllyriaUtils;

public class IllyriaCore extends JavaPlugin implements MessagesInterface {

  @Override
  public void onEnable() {
    getLogger().info(SERVER_VERSION + BUKKIT_VERSION);
    if (!IllyriaUtils.isCompatible(BUKKIT_VERSION)) {
      getLogger().severe(COMP_VERSION);
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    try {
      CommentedConfigurationNode conf = ConfigHandler.init(this);
      ModuleHandler.init(this, conf);
      ReloadCommand.init(getLifecycleManager(), conf);
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