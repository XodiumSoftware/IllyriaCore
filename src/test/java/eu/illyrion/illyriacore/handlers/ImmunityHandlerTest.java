package eu.illyrion.illyriacore.handlers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.scheduler.BukkitSchedulerMock;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.illyrion.illyriacore.IllyriaCore;
import eu.illyrion.illyriacore.configs.Config;
import net.kyori.adventure.text.Component;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmunityHandlerTest {

    private IllyriaCore plugin;
    private ImmunityHandler immunityHandler;
    private BukkitSchedulerMock scheduler;
    private FileConfiguration config;

    @BeforeEach
    public void setUp() {
        MockBukkit.mock();
        plugin = MockBukkit.load(IllyriaCore.class);
        scheduler = MockBukkit.getMock().getScheduler();
        immunityHandler = new ImmunityHandler();
        PluginManager pluginManager = MockBukkit.getMock().getPluginManager();
        pluginManager.registerEvents(immunityHandler, plugin);
        config = Config.init();
        config.set("ImmunityTimerDuration", 10);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testOnPlayerJoin() {
        PlayerMock player = MockBukkit.getMock().addPlayer();
        PlayerJoinEvent joinEvent = new PlayerJoinEvent(player, Component.text("TEST"));
        MockBukkit.getMock().getPluginManager().callEvent(joinEvent);
        assertTrue(player.isInvulnerable());
        scheduler.performTicks(config.getInt("ImmunityTimerDuration") * 20 + 1);
        assertFalse(player.isInvulnerable());
    }
}
