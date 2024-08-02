package org.xodium.illyriacore.modules;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.handlers.ConfigHandler;
import org.xodium.illyriacore.interfaces.CI;
import org.xodium.illyriacore.interfaces.SI;
import org.xodium.illyriacore.utils.IllyriaUtils;

import io.papermc.paper.util.Tick;
import net.kyori.adventure.bossbar.BossBar;

// TODO: refactor to be more modular and testable.

/**
 * The ImmunityModule class is responsible for managing player immunity
 * features.
 * It listens for player join events and applies various forms of immunity to
 * the player.
 */
public class ImmunityModule implements Listener {

    private final PlayerInvulnerabilityManager invulnerabilityManager;
    private final BossBarManager bossBarManager;
    private final DamagePreventionManager damagePreventionManager;

    public ImmunityModule() {
        this.invulnerabilityManager = new PlayerInvulnerabilityManager();
        this.bossBarManager = new BossBarManager();
        this.damagePreventionManager = new DamagePreventionManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws ConfigurateException {
        IllyriaCore plugin = JavaPlugin.getPlugin(IllyriaCore.class);
        Player p = e.getPlayer();
        if (p.isOnline()) {
            CommentedConfigurationNode conf = new ConfigHandler().init(plugin);
            invulnerabilityManager.makePlayerInvulnerable(p, plugin, conf);
            bossBarManager.showCountdownBossBar(p, plugin, conf);
            damagePreventionManager.addInvulnerablePlayer(p);
        }
    }
}

class PlayerInvulnerabilityManager {

    public void makePlayerInvulnerable(Player p, IllyriaCore plugin, CommentedConfigurationNode conf)
            throws ConfigurateException {
        p.setInvulnerable(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.isOnline()) {
                    p.setInvulnerable(false);
                }
            }
        }.runTaskLater(JavaPlugin.getPlugin(IllyriaCore.class),
                Tick.tick().fromDuration(
                        Duration.ofSeconds(conf.node(CI.MODULES_PREFIX, CI.IMMUNITY_MODULE, CI.IMMUNITY_TIMER_DURATION)
                                .getInt())));
    }
}

class BossBarManager {

    public void showCountdownBossBar(Player p, IllyriaCore plugin, CommentedConfigurationNode conf)
            throws ConfigurateException {
        final BossBar bossBar = IllyriaUtils.createBossBar(
                conf.node(CI.MODULES_PREFIX, CI.IMMUNITY_MODULE, CI.IMMUNITY_TIMER_TITLE).getString(), 1.0f,
                BossBar.Color.WHITE,
                BossBar.Overlay.PROGRESS);
        p.showBossBar(bossBar);
        startBossBarCountdown(p, plugin, bossBar, conf);
    }

    private void startBossBarCountdown(Player p, IllyriaCore plugin, BossBar bossBar, CommentedConfigurationNode conf)
            throws ConfigurateException {
        new BukkitRunnable() {
            int timeLeft = conf.node(CI.MODULES_PREFIX, CI.IMMUNITY_MODULE, CI.IMMUNITY_TIMER_DURATION).getInt();

            @Override
            public void run() {
                if (timeLeft <= 0 || !p.isOnline()) {
                    p.hideBossBar(bossBar);
                    this.cancel();
                } else {
                    bossBar.progress(
                            (float) timeLeft / conf
                                    .node(CI.MODULES_PREFIX, CI.IMMUNITY_MODULE, CI.IMMUNITY_TIMER_DURATION).getInt());
                    p.playSound(SI.IMMUNITY_BAR);
                }
                timeLeft--;
            }
        }.runTaskTimer(plugin, 0,
                conf.node(CI.MODULES_PREFIX, CI.IMMUNITY_MODULE, CI.IMMUNITY_TIMER_DURATION).getInt() * 2);
    }
}

class DamagePreventionManager implements Listener {

    private final Set<Player> invulnerableP = new CopyOnWriteArraySet<>();

    public void addInvulnerablePlayer(Player p) {
        invulnerableP.add(p);
    }

    public void removeInvulnerablePlayer(Player p) {
        invulnerableP.remove(p);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player p && invulnerableP.contains(p)) {
            e.setCancelled(true);
        }
    }
}