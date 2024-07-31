package org.xodium.illyriacore.handlers;

import java.time.Duration;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.configs.Config;
import org.xodium.illyriacore.interfaces.ConfigInferface;
import org.xodium.illyriacore.utils.IllyriaUtils;

import io.papermc.paper.util.Tick;
import net.kyori.adventure.bossbar.BossBar;

public class ImmunityHandler implements Listener {

    private final PlayerInvulnerabilityManager invulnerabilityManager;
    private final BossBarManager bossBarManager;

    public ImmunityHandler() {
        this.invulnerabilityManager = new PlayerInvulnerabilityManager();
        this.bossBarManager = new BossBarManager();
    }

    /**
     * Handles the player join event.
     * Makes the player invulnerable and shows a countdown boss bar.
     *
     * @param e The PlayerJoinEvent object representing the player join event.
     * @throws ConfigurateException
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws ConfigurateException {
        Player p = e.getPlayer();
        if (p.isOnline()) {
            invulnerabilityManager.makePlayerInvulnerable(p);
            bossBarManager.showCountdownBossBar(p);
        }
    }
}

class PlayerInvulnerabilityManager implements ConfigInferface {

    /**
     * Makes the specified player invulnerable for a certain duration.
     * After the duration expires, the player's invulnerability is removed.
     *
     * @param p The player to make invulnerable.
     * @throws ConfigurateException
     */
    public void makePlayerInvulnerable(Player p) throws ConfigurateException {
        CommentedConfigurationNode conf = Config.init();
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
                        Duration.ofSeconds(conf.node(LOCALIZATION_PREFIX, IMMUNITY_TIMER_DURATION).getInt())));
    }
}

class BossBarManager implements ConfigInferface {

    private static final int TICKS_PER_SECOND = 20;

    /**
     * Shows a countdown boss bar to the specified player.
     *
     * @param p the player to show the boss bar to
     * @throws ConfigurateException
     */
    public void showCountdownBossBar(Player p) throws ConfigurateException {
        CommentedConfigurationNode conf = Config.init();
        final BossBar bossBar = IllyriaUtils.createBossBar(
                conf.node(LOCALIZATION_PREFIX, IMMUNITY_TIMER_TITLE).getString(), 1.0f,
                BossBar.Color.WHITE,
                BossBar.Overlay.PROGRESS);
        p.showBossBar(bossBar);
        startBossBarCountdown(p, bossBar, conf);
    }

    /**
     * Starts a countdown timer for the boss bar.
     *
     * @param p       The player for whom the boss bar is displayed.
     * @param bossBar The boss bar to be displayed.
     * @throws ConfigurateException
     */
    private void startBossBarCountdown(Player p, BossBar bossBar, CommentedConfigurationNode conf)
            throws ConfigurateException {
        long initialDelay = conf.node(LOCALIZATION_PREFIX, IMMUNITY_TIMER_DURATION).getInt();
        long delay = conf.node(LOCALIZATION_PREFIX, IMMUNITY_TIMER_DURATION).getInt() / TICKS_PER_SECOND;

        new BukkitRunnable() {
            int timeLeft = conf.node(LOCALIZATION_PREFIX, IMMUNITY_TIMER_DURATION).getInt();

            @Override
            public void run() {
                if (timeLeft <= 0 || !p.isOnline()) {
                    p.hideBossBar(bossBar);
                    this.cancel();
                } else {
                    bossBar.progress(
                            (float) timeLeft / conf.node(LOCALIZATION_PREFIX, IMMUNITY_TIMER_DURATION).getInt());
                    timeLeft--;
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(IllyriaCore.class), initialDelay, delay);
    }
}
