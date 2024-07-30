package org.xodium.illyriacore.handlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomAnvilHandlerTest {

    @Mock
    private PrepareAnvilEvent prepareAnvilEvent;

    @Mock
    private AnvilInventory anvilInventory;

    @InjectMocks
    private CustomAnvilHandler customAnvilHandler;

    @BeforeEach
    public void setUp() {
        when(prepareAnvilEvent.getInventory()).thenReturn(anvilInventory);
    }

    @Test
    public void testOnPrepareAnvil_BothItemsPresentAndEnchantedBooks() {
        ItemStack firstItem = mock(ItemStack.class);
        ItemStack secondItem = mock(ItemStack.class);
        Enchantment enchantment = mock(Enchantment.class);

        when(firstItem.getType()).thenReturn(Material.ENCHANTED_BOOK);
        when(secondItem.getType()).thenReturn(Material.ENCHANTED_BOOK);

        when(firstItem.getEnchantmentLevel(enchantment)).thenReturn(1);
        when(secondItem.getEnchantmentLevel(enchantment)).thenReturn(1);

        @SuppressWarnings("unchecked")
        Iterator<Enchantment> iterator = mock(Iterator.class);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(enchantment);
        when(Registry.ENCHANTMENT.iterator()).thenReturn(iterator);
        when(anvilInventory.getItem(0)).thenReturn(firstItem);
        when(anvilInventory.getItem(1)).thenReturn(secondItem);

        customAnvilHandler.onPrepareAnvil(prepareAnvilEvent);

        ItemStack result = prepareAnvilEvent.getResult();
        assertNotNull(result);
        assertEquals(Material.ENCHANTED_BOOK, result.getType());
        assertEquals(2, result.getEnchantmentLevel(enchantment));
    }

    @Test
    public void testOnPrepareAnvil_ItemsNotPresent() {
        when(anvilInventory.getItem(0)).thenReturn(null);
        when(anvilInventory.getItem(1)).thenReturn(null);

        customAnvilHandler.onPrepareAnvil(prepareAnvilEvent);

        assertNull(prepareAnvilEvent.getResult());
    }

    @Test
    public void testOnPrepareAnvil_ItemsNotEnchantedBooks() {
        ItemStack firstItem = mock(ItemStack.class);
        ItemStack secondItem = mock(ItemStack.class);

        when(firstItem.getType()).thenReturn(Material.DIAMOND_SWORD);
        when(secondItem.getType()).thenReturn(Material.DIAMOND_SWORD);
        when(anvilInventory.getItem(0)).thenReturn(firstItem);
        when(anvilInventory.getItem(1)).thenReturn(secondItem);

        customAnvilHandler.onPrepareAnvil(prepareAnvilEvent);
        assertNull(prepareAnvilEvent.getResult());
    }
}