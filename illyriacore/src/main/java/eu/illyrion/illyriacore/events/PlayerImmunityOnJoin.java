package eu.illyrion.illyriacore.events;

import java.time.Duration;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import eu.illyrion.illyriacore.IllyriaCore;
import eu.illyrion.illyriacore.utils.Utils;
import io.papermc.paper.util.Tick;

public class PlayerImmunityOnJoin implements Listener {

    private static final String INVULNERABILITY_DISABLED_MSG = "<red>You are no longer invulnerable!";
    private static final String INVULNERABILITY_ENABLED_MSG = "<green>You are now invulnerable!";

    private static final int DURATION_SECONDS = 10;

    /**
     * Handles the player join event.
     * This method is called when a player joins the server.
     *
     * @param e The PlayerJoinEvent object containing information about the event.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        invulnerableOnJoin(p);
    }

    /**
     * Makes the player invulnerable upon joining the server for a specific
     * duration.
     * The player will be set as invulnerable and a title message will be displayed.
     * After the specified duration, the player's invulnerability will be disabled
     * and
     * another title message will be displayed.
     *
     * @param p The player to make invulnerable on join.
     */
    private void invulnerableOnJoin(Player p) {
        IllyriaCore plugin = IllyriaCore.getInstance();
        int tick = Tick.tick().fromDuration(Duration.ofSeconds(DURATION_SECONDS));
        p.setInvulnerable(true);
        Utils.showTitle(p, INVULNERABILITY_ENABLED_MSG, null);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.setInvulnerable(false);
                Utils.showTitle(p, INVULNERABILITY_DISABLED_MSG, null);
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