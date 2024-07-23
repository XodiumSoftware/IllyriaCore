package eu.illyrion.illyriacore.handlers;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class CustomAnvilHandler implements Listener {

    /**
     * Handles the PrepareAnvilEvent.
     * This method is called when an anvil is being prepared for use.
     *
     * @param e The PrepareAnvilEvent object containing information about the event.
     */
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {
        AnvilInventory anvilInventory = e.getInventory();
        ItemStack firstItem = anvilInventory.getItem(0);
        ItemStack secondItem = anvilInventory.getItem(1);
        if (firstItem != null && secondItem != null && firstItem.getType() == Material.ENCHANTED_BOOK
                && secondItem.getType() == Material.ENCHANTED_BOOK) {
            Iterator<Enchantment> enchantments = Registry.ENCHANTMENT.iterator();
            while (enchantments.hasNext()) {
                Enchantment enchantment = enchantments.next();
                if (enchantment.getMaxLevel() > 1) {
                    int minLVL = firstItem.getEnchantmentLevel(enchantment);
                    int maxLVL = secondItem.getEnchantmentLevel(enchantment);
                    int vanillaMaxLevel = enchantment.getMaxLevel();
                    int customMaxLevel = vanillaMaxLevel + 5;
                    if (minLVL == maxLVL && minLVL >= 1 && minLVL < customMaxLevel) {
                        ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
                        result.addUnsafeEnchantment(enchantment, minLVL + 1);
                        e.setResult(result);
                        break;
                    }
                }
            }
        }
    }
}