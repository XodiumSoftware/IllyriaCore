package eu.illyrion.illyriautils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import eu.illyrion.illyriautils.config.Permissions;
import eu.illyrion.illyriautils.IllyriaUtils;

public class ReloadCmd implements CommandExecutor {

    private static final String HAS_RELOADED_MSG = "IllyriaUtils configuration has been reloaded.";

    private final IllyriaUtils plugin;

    public ReloadCmd() {
        plugin = IllyriaUtils.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String alias,
            @NotNull String[] args) {
        if (!cs.hasPermission(Permissions.RELOAD)) {
            cs.sendMessage(cmd.getPermission());
            return true;
        }
        plugin.reload();
        if (IllyriaUtils.getInstance().isDebug()) {
            for (String key : plugin.getConfig().getKeys(true)) {
                IllyriaUtils.getInstance().getLogger().info(key + " -> " + plugin.getConfig().getString(key));
            }
        }
        cs.sendMessage(HAS_RELOADED_MSG);
        return true;
    }

}