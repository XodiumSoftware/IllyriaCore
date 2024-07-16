package eu.illyrion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import eu.illyrion.Plugin;
import eu.illyrion.config.Permissions;

public class ReloadCommand implements CommandExecutor {

    private static final String HAS_RELOADED_MSG = "IllyriaUtils configuration has been reloaded.";

    private final Plugin plugin;

    public ReloadCommand() {
        plugin = Plugin.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias,
            @NotNull String[] args) {
        if (!sender.hasPermission(Permissions.RELOAD)) {
            sender.sendMessage(cmd.getPermission());
            return true;
        }
        plugin.reload();
        if (Plugin.getInstance().isDebug()) {
            for (String key : plugin.getConfig().getKeys(true)) {
                Plugin.getInstance().getLogger().info(key + " -> " + plugin.getConfig().getString(key));
            }
        }
        sender.sendMessage(HAS_RELOADED_MSG);
        return true;
    }

}