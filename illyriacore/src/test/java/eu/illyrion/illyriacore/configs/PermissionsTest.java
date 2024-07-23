package eu.illyrion.illyriacore.configs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermissionsTest {

    @Test
    public void testPermissions() {
        assertEquals("illyriautils.", Permissions.PERM_PREFIX);
        assertEquals("illyriautils.reload", Permissions.RELOAD);
    }
}
