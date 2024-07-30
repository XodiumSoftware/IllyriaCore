package org.xodium.illyriacore.configs;

import org.junit.jupiter.api.Test;

import org.xodium.illyriacore.interfaces.PermissionsInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermissionsTest implements PermissionsInterface {

    @Test
    public void testPermissions() {
        assertEquals("illyriautils.", PERM_PREFIX);
        assertEquals("illyriautils.reload", RELOAD);
    }
}
