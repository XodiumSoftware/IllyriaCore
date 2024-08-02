package org.xodium.illyriacore.commands;

import com.mojang.brigadier.Command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.interfaces.CI;
import org.xodium.illyriacore.interfaces.PI;
import org.xodium.illyriacore.utils.IllyriaUtils;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

/**
 * The ReloadCommand class is responsible for initializing the reload command
 * for the IllyriaCore plugin.
 * This command allows users to reload the plugin.
 */
public class ReloadCommand {

    /**
     * Initializes the ReloadCommand by registering it with the provided
     * LifecycleEventManager and
     * configuring it with the given CommentedConfigurationNode.
     *
     * @param manager The LifecycleEventManager to register the ReloadCommand with.
     * @param conf    The CommentedConfigurationNode used for configuration.
     * @throws ConfigurateException if there is an error with the configuration.
     */
    public static void init(LifecycleEventManager<org.bukkit.plugin.Plugin> manager,
            CommentedConfigurationNode conf) throws ConfigurateException {
        IllyriaCore plugin = JavaPlugin.getPlugin(IllyriaCore.class);
        manager.registerEventHandler(LifecycleEvents.COMMANDS, e -> {
            final Commands cmds = e.registrar();
            cmds.register(
                    Commands.literal("icore")
                            .requires(source -> {
                                CommandSender sender = source.getSender();
                                return sender.hasPermission(PI.RELOAD);
                            })
                            .then(Commands.literal("reload")
                                    .executes(ctx -> {
                                        CommandSender sender = ctx.getSource()
                                                .getSender();
                                        sender.sendMessage(
                                                IllyriaUtils.formatter(
                                                        conf.node(CI.GENERAL_PREFIX,
                                                                CI.CHAT_PREFIX)
                                                                .getString()
                                                                + "<yellow>Reloading the plugin...<reset>"));
                                        plugin.reload();
                                        sender.sendMessage(
                                                IllyriaUtils.formatter(
                                                        conf.node(CI.GENERAL_PREFIX,
                                                                CI.CHAT_PREFIX)
                                                                .getString()
                                                                + "<green>Reload complete.<reset>"));
                                        return Command.SINGLE_SUCCESS;
                                    }))
                            .build(),
                    "Reloads the plugin");
        });
    }
}
