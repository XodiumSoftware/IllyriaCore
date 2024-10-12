package org.xodium.illyriacore;

import org.bukkit.plugin.java.JavaPlugin;
import org.xodium.illyriacore.interfaces.CONST;
import org.xodium.illyriacore.interfaces.MSG;
import org.xodium.illyriacore.listeners.EventListener;

public class IllyriaCore extends JavaPlugin {

  @Override
  public void onEnable() {
    if (!IllyriaUtils.isCompatibleEnv(this)) {
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    saveResource(CONST.CONFIG_FILE, false);
    saveDefaultConfig();

    getLogger().info(MSG.ILLYRIA_CORE_ENABLED);

    getServer().getPluginManager().registerEvents(new EventListener(IllyriaUtils.loadFromConfig(this)), this);
  }

  @Override
  public void onDisable() {
    getLogger().info(MSG.ILLYRIA_CORE_DISABLED);
  }

}