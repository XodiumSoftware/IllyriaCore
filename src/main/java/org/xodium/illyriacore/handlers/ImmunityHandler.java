package org.xodium.illyriacore.handlers;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.interfaces.ConfigInferface;
import org.xodium.illyriacore.utils.IllyriaUtils;

import io.papermc.paper.util.Tick;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

public class ImmunityHandler implements Listener {

    private final PlayerInvulnerabilityManager invulnerabilityManager;
    private final BossBarManager bossBarManager;
    private final DamagePreventionManager damagePreventionManager;

    public ImmunityHandler() {
        this.invulnerabilityManager = new PlayerInvulnerabilityManager();
        this.bossBarManager = new BossBarManager();
        this.damagePreventionManager = new DamagePreventionManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws ConfigurateException {
        Player p = e.getPlayer();
        if (p.isOnline()) {
            invulnerabilityManager.makePlayerInvulnerable(p);
            bossBarManager.showCountdownBossBar(p);
            damagePreventionManager.addInvulnerablePlayer(p);
        }
    }
}

class PlayerInvulnerabilityManager implements ConfigInferface {

    public void makePlayerInvulnerable(Player p) throws ConfigurateException {
        IllyriaCore plugin = JavaPlugin.getPlugin(IllyriaCore.class);
        ConfigHandler configHandler = new ConfigHandler();
        CommentedConfigurationNode conf = configHandler.init(plugin);
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
                        Duration.ofSeconds(conf.node(LOC_PREFIX, IMMUNITY_TIMER_DURATION).getInt())));
    }
}

class BossBarManager implements ConfigInferface {

    private static final @NotNull Sound immunityBarSound = Sound.sound(
            Key.key("minecraft", "block.note_block.pling"),
            Sound.Source.MASTER, 1f, 1f);

    public void showCountdownBossBar(Player p) throws ConfigurateException {
        IllyriaCore plugin = JavaPlugin.getPlugin(IllyriaCore.class);
        ConfigHandler configHandler = new ConfigHandler();
        CommentedConfigurationNode conf = configHandler.init(plugin);
        final BossBar bossBar = IllyriaUtils.createBossBar(
                conf.node(LOC_PREFIX, IMMUNITY_TIMER_TITLE).getString(), 1.0f,
                BossBar.Color.WHITE,
                BossBar.Overlay.PROGRESS);
        p.showBossBar(bossBar);
        startBossBarCountdown(p, bossBar, conf);
    }

    private void startBossBarCountdown(Player p, BossBar bossBar, CommentedConfigurationNode conf)
            throws ConfigurateException {
        long initialDelay = 0;
        long delay = conf.node(LOC_PREFIX, IMMUNITY_TIMER_DURATION).getInt() * 2;

        new BukkitRunnable() {
            int timeLeft = conf.node(LOC_PREFIX, IMMUNITY_TIMER_DURATION).getInt();
            boolean firstTick = true;

            @Override
            public void run() {
                if (timeLeft <= 0 || !p.isOnline()) {
                    p.hideBossBar(bossBar);
                    this.cancel();
                } else {
                    bossBar.progress(
                            (float) timeLeft / conf.node(LOC_PREFIX, IMMUNITY_TIMER_DURATION).getInt());
                    if (!firstTick) {
                        p.playSound(immunityBarSound);
                    }
                    firstTick = false;
                    timeLeft--;
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(IllyriaCore.class), initialDelay, delay);
    }
}

class DamagePreventionManager implements Listener {

    private final Set<Player> invulnerablePlayers = new HashSet<>();

    public void addInvulnerablePlayer(Player p) {
        invulnerablePlayers.add(p);
    }

    public void removeInvulnerablePlayer(Player p) {
        invulnerablePlayers.remove(p);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            if (invulnerablePlayers.contains(damager)) {
                e.setCancelled(true);
            }
        }
    }
}