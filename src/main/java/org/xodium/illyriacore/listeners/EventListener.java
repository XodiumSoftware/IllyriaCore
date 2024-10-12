package org.xodium.illyriacore.listeners;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeEqualityPredicate;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.util.Tristate;

import java.util.Map;
import java.util.Optional;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EventListener implements Listener {

    private final LuckPerms lp;
    private final Map<EntityType, String> entityPermMap;

    public EventListener(Map<EntityType, String> entityPermMap, LuckPerms lp) {
        this.lp = lp;
        this.entityPermMap = entityPermMap;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Optional.ofNullable(entityPermMap.get(e.getEntity().getType()))
                .ifPresent(permNodeStr -> handleEntityDeath(e, permNodeStr));
    }

    private void handleEntityDeath(EntityDeathEvent e, String permNodeStr) {
        if (e.getEntity().getKiller() instanceof Player) {
            Optional.ofNullable(lp.getUserManager().getUser(e.getEntity().getKiller().getUniqueId()))
                    .ifPresent(usr -> addPermissionIfAbsent(usr, permNodeStr));
        }
    }

    private void addPermissionIfAbsent(User usr, String permNodeStr) {
        PermissionNode permNode = PermissionNode.builder(permNodeStr).build();
        if (usr.data().contains(permNode, NodeEqualityPredicate.EXACT) == Tristate.TRUE) {
            usr.data().add(permNode);
            lp.getUserManager().saveUser(usr).join();
        }
    }
}
