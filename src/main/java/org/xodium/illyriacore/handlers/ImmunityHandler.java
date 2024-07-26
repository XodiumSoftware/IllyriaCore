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

public class ImmunityHandler implements Listener, ConfigInferface {

    private static final float BOSS_BAR_INITIAL_PROGRESS = 1.0f;
    private static final float BOSS_BAR_PROGRESS = 10.0f;

    private static final long INITIAL_DELAY = 0L;
    private static final long DELAY = 20L;

    private final FileConfiguration conf = Config.init();

    /**
     * Handles the player join event.
     * Makes the player invulnerable and shows the countdown boss bar.
     *
     * @param e The PlayerJoinEvent object representing the player join event.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.isOnline()) {
            makePlayerInvulnerable(p);
            showCountdownBossBar(p);
        }
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
        return BossBar.bossBar(name, BOSS_BAR_INITIAL_PROGRESS, BossBar.Color.WHITE, BossBar.Overlay.PROGRESS);
    }

    /**
     * Starts a countdown timer for the boss bar.
     *
     * @param p       The player for whom the boss bar is displayed.
     * @param bossBar The boss bar to be displayed.
     */
    private void startBossBarCountdown(Player p, BossBar bossBar) {
        new BukkitRunnable() {
            private int timeLeft = conf.getInt(IMMUNITY_TIMER_DURATION);

            @Override
            public void run() {
                if (timeLeft <= 0 || !p.isOnline()) {
                    p.hideBossBar(bossBar);
                    this.cancel();
                } else {
                    bossBar.progress(timeLeft / BOSS_BAR_PROGRESS);
                    timeLeft--;
                }
            }
        }.runTaskTimer(IllyriaCore.getInstance(), INITIAL_DELAY, DELAY);
    }
}
