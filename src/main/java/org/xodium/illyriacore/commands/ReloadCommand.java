package org.xodium.illyriacore.commands;

import com.mojang.brigadier.Command;
import org.xodium.illyriacore.interfaces.MessagesInterface;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

public class ReloadCommand implements MessagesInterface {

    /**
     * Initializes the ReloadCmd by registering it as a command handler in the
     * provided LifecycleEventManager.
     *
     * @param manager The lifecycle event manager for the plugin.
     */
    public static void init(LifecycleEventManager<org.bukkit.plugin.Plugin> manager) {
        manager.registerEventHandler(LifecycleEvents.COMMANDS, e -> {
            final Commands cmds = e.registrar();
            cmds.register(
                    Commands.literal("reload")
                            .executes(ctx -> {
                                ctx.getSource().getSender().sendMessage(RELOADING);
                                // TODO: Implement the command logic here
                                return Command.SINGLE_SUCCESS;
                            })
                            .build(),
                    "Reloads the plugin");
        });
    }

    // TODO: Create the logic here to reload the plugin.
}
