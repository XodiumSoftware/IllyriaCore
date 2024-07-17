package eu.illyrion.illyriacore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import eu.illyrion.illyriacore.IllyriaCore;
import eu.illyrion.illyriacore.config.Permissions;

public class ReloadCmd implements CommandExecutor {

    private static final String HAS_RELOADED_MSG = "IllyriaUtils configuration has been reloaded.";

    // TODO: Refactor to use the papermc CommandAPI.
    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String alias,
            @NotNull String[] args) {
        if (!cs.hasPermission(Permissions.RELOAD)) {
            cs.sendMessage(cmd.getPermission());
            return true;
        }
        IllyriaCore plugin = IllyriaCore.getInstance();
        plugin.reload();
        if (plugin.isDebug()) {
            for (String key : plugin.getConfig().getKeys(true)) {
                plugin.getLogger().info(key + " -> " + plugin.getConfig().getString(key));
            }
        }
        cs.sendMessage(HAS_RELOADED_MSG);
        return true;
    }

}