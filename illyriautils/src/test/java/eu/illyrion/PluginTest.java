package eu.illyrion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

public class PluginTest {

    private Plugin plugin;
    private Server mockServer;
    private PluginManager mockPluginManager;

    @Before
    public void setUp() {
        plugin = new Plugin();
        mockServer = Mockito.mock(Server.class);
        mockPluginManager = Mockito.mock(PluginManager.class);
        Mockito.when(mockServer.getPluginManager()).thenReturn(mockPluginManager);
        Bukkit.setServer(mockServer);
    }

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void testIsCompatible() {
        assertTrue(plugin.isCompatible("1.20.6"));
        assertTrue(plugin.isCompatible("1.21.0"));
        assertFalse(plugin.isCompatible("1.20.5"));
        assertFalse(plugin.isCompatible("1.19.4"));
    }

    @Test
    public void testOnEnableWithCompatibleVersion() {
        Mockito.when(Bukkit.getBukkitVersion()).thenReturn("1.20.6");
        plugin.onEnable();
        Mockito.verify(mockPluginManager, Mockito.never()).disablePlugin(plugin);
    }

    @Test
    public void testOnEnableWithIncompatibleVersion() {
        Mockito.when(Bukkit.getBukkitVersion()).thenReturn("1.20.5");
        plugin.onEnable();
        Mockito.verify(mockPluginManager).disablePlugin(plugin);
    }
}