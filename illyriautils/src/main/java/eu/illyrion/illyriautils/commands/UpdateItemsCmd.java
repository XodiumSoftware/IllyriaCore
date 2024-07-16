package eu.illyrion.illyriautils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import eu.illyrion.illyriautils.handlers.CustomItemHandler;

public class UpdateItemsCmd implements CommandExecutor {

    private static final String CUSTOM_ITEMS_UPDATED_MSG = "Custom items have been updated.";
    private static final String UPDATECUSTOMITEMS = "updatecustomitems";

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String alias,
            @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase(UPDATECUSTOMITEMS)) {
            CustomItemHandler.init();
            cs.sendMessage(CUSTOM_ITEMS_UPDATED_MSG);
            return true;
        }
        return false;
    }
}