package eu.illyrion.illyriacore.events;

import java.time.Duration;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import eu.illyrion.illyriacore.IllyriaCore;
import io.papermc.paper.util.Tick;

public class PlayerImmunityOnJoin implements Listener {

    private static final int DURATION_SECONDS = 10;

    /**
     * Handles the player join event.
     * Applies a potion effect to the player, making them invincible for a certain
     * duration.
     *
     * @param e The PlayerJoinEvent object representing the player join event.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        IllyriaCore plugin = IllyriaCore.getInstance();
        // MiniMessage mm = MiniMessage.miniMessage();
        int tick = Tick.tick().fromDuration(Duration.ofSeconds(DURATION_SECONDS));
        p.setInvulnerable(true);
        // TODO: implement this correctly
        // p.showTitle(mm.deserialize(""));
        new BukkitRunnable() {
            @Override
            public void run() {
                p.setInvulnerable(false);
                // TODO: implement this correctly
                // mm.deserialize("");
            }
        }.runTaskLater(plugin, tick);
    };

    /**
     * Prevents the player from dealing damage.
     *
     * @param e The EntityDamageByEntityEvent object representing the damage event.
     */
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (p.isInvulnerable()) {
                e.setCancelled(true);
            }
        }
    }
}