package org.xodium.illyriacore.listeners;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeEqualityPredicate;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.util.Tristate;

import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EventListener implements Listener {

    private final LuckPerms lp;
    private final Map<EntityType, String> entityPermMap;

    public EventListener(LuckPerms lp, Map<EntityType, String> entityPermMap) {
        this.lp = lp;
        this.entityPermMap = entityPermMap;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        EntityType entityType = e.getEntity().getType();
        if (!entityPermMap.containsKey(entityType)) {
            return;
        }

        if (!(e.getEntity().getKiller() instanceof Player)) {
            return;
        }

        Player p = e.getEntity().getKiller();
        User usr = lp.getUserManager().getUser(p.getUniqueId());
        if (usr == null) {
            return;
        }

        String permNodeStr = entityPermMap.get(entityType);
        addPermissionIfAbsent(usr, permNodeStr);
    }

    private void addPermissionIfAbsent(User usr, String permNodeStr) {
        PermissionNode permNode = PermissionNode.builder(permNodeStr).build();
        if (usr.data().contains(permNode, NodeEqualityPredicate.EXACT) == Tristate.FALSE) {
            usr.data().add(permNode);
            lp.getUserManager().saveUser(usr);
        }
    }
}
