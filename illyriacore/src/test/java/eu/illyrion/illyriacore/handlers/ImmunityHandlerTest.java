package eu.illyrion.illyriacore.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ImmunityHandlerTest {

    @Mock
    private PlayerJoinEvent event;

    @Mock
    private Player player;

    private ImmunityHandler handler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new ImmunityHandler();
        when(event.getPlayer()).thenReturn(player);
    }

    @Test
    public void testOnPlayerJoin() {
        when(player.isOnline()).thenReturn(true);

        handler.onPlayerJoin(event);

        verify(player, times(1)).setInvulnerable(true);
        verify(player, times(1)).showBossBar(any());
    }
}
