package org.xodium.illyriacore.handlers;

import java.time.Duration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.configs.Config;
import org.xodium.illyriacore.interfaces.ConfigInferface;
import io.papermc.paper.util.Tick;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class ImmunityHandler implements Listener {

    private final PlayerInvulnerabilityManager invulnerabilityManager;
    private final BossBarManager bossBarManager;
    private final FileConfiguration conf;

    public ImmunityHandler() {
        this.conf = Config.init();
        this.invulnerabilityManager = new PlayerInvulnerabilityManager(conf);
        this.bossBarManager = new BossBarManager(conf);
    }

    /**
     * Handles the player join event.
     * Makes the player invulnerable and shows a countdown boss bar.
     *
     * @param e The PlayerJoinEvent object representing the player join event.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.isOnline()) {
            invulnerabilityManager.makePlayerInvulnerable(p);
            bossBarManager.showCountdownBossBar(p);
        }
    }
}

class PlayerInvulnerabilityManager implements ConfigInferface {

    private final FileConfiguration conf;

    public PlayerInvulnerabilityManager(FileConfiguration conf) {
        this.conf = conf;
    }

    /**
     * Makes the specified player invulnerable for a certain duration.
     * After the duration expires, the player's invulnerability is removed.
     *
     * @param p The player to make invulnerable.
     */
    public void makePlayerInvulnerable(Player p) {
        p.setInvulnerable(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.isOnline()) {
                    p.setInvulnerable(false);
                }
            }
        }.runTaskLater(IllyriaCore.getInstance(),
                Tick.tick().fromDuration(Duration.ofSeconds(conf.getInt(IMMUNITY_TIMER_DURATION))));
    }
}

class BossBarManager implements ConfigInferface {

    private static final int TICKS_PER_SECOND = 20;
    private final FileConfiguration conf;

    public BossBarManager(FileConfiguration conf) {
        this.conf = conf;
    }

    /**
     * Shows a countdown boss bar to the specified player.
     *
     * @param p the player to show the boss bar to
     */
    public void showCountdownBossBar(Player p) {
        final BossBar bossBar = createBossBar();
        p.showBossBar(bossBar);
        startBossBarCountdown(p, bossBar);
    }

    /**
     * Creates a boss bar with the specified name and initial progress.
     *
     * @return the created boss bar
     */
    private BossBar createBossBar() {
        final Component name = Component.text(conf.getString(IMMUNITY_TIMER));
        return BossBar.bossBar(name, 1.0f, BossBar.Color.WHITE, BossBar.Overlay.PROGRESS);
    }

    /**
     * Starts a countdown timer for the boss bar.
     *
     * @param p       The player for whom the boss bar is displayed.
     * @param bossBar The boss bar to be displayed.
     */
    private void startBossBarCountdown(Player p, BossBar bossBar) {
        long initialDelay = conf.getInt(IMMUNITY_TIMER_DURATION);
        long delay = conf.getInt(IMMUNITY_TIMER_DURATION) / TICKS_PER_SECOND;

        new BukkitRunnable() {
            int timeLeft = conf.getInt(IMMUNITY_TIMER_DURATION);

            @Override
            public void run() {
                if (timeLeft <= 0 || !p.isOnline()) {
                    p.hideBossBar(bossBar);
                    this.cancel();
                } else {
                    bossBar.progress((float) timeLeft / conf.getInt(IMMUNITY_TIMER_DURATION));
                    timeLeft--;
                }
            }
        }.runTaskTimer(IllyriaCore.getInstance(), initialDelay, delay);
    }
}
