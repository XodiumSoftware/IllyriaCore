package org.xodium.illyriacore.listeners;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

    private final LuckPerms LP;

    public EventListener(LuckPerms LP) {
        this.LP = LP;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        User usr = LP.getUserManager().getUser(e.getPlayer().getUniqueId());
        if (usr != null) {
            usr.data().add(Node.builder("your.permission.node").build());
            LP.getUserManager().saveUser(usr);
        }
    }
}
