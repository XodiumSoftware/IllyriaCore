package org.xodium.illyriacore.commands;

import com.mojang.brigadier.Command;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

public class UpdateCustomItemsCmd {

    /**
     * Initializes the command registration for custom items.
     *
     * @param manager The lifecycle event manager for the plugin.
     */
    public static void init(LifecycleEventManager<org.bukkit.plugin.Plugin> manager) {
        manager.registerEventHandler(LifecycleEvents.COMMANDS, e -> {
            final Commands cmds = e.registrar();
            cmds.register(
                    Commands.literal("customitems")
                            .executes(ctx -> {
                                // TODO: Implement the command logic here
                                ctx.getSource().getSender().sendPlainMessage("Updating custom items...");
                                // Add code to update custom items here
                                return Command.SINGLE_SUCCESS;
                            })
                            .build(),
                    "Updates the custom items on this server");
        });
    }

    // TODO: Create the logic here to update the customitems.
}
