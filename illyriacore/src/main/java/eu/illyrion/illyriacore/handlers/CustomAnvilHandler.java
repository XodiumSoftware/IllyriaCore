package eu.illyrion.illyriacore.handlers;

import java.util.Iterator;
import java.util.Optional;

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
     * Handles the PrepareAnvilEvent, which is triggered when a player prepares an
     * anvil.
     * This method checks if the first and second items in the anvil inventory are
     * enchanted books.
     * If they are, it iterates through all enchantments and checks if the minimum
     * level of the enchantment
     * in the first item is equal to the maximum level of the enchantment in the
     * second item.
     * If the conditions are met, it creates a new ItemStack with an increased level
     * of the enchantment
     * and sets it as the result of the event.
     *
     * @param e The PrepareAnvilEvent object representing the event.
     */
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {
        AnvilInventory anvilInventory = e.getInventory();
        Optional<ItemStack> firstItem = Optional.ofNullable(anvilInventory.getItem(0));
        Optional<ItemStack> secondItem = Optional.ofNullable(anvilInventory.getItem(1));
        if (!firstItem.isPresent() || !secondItem.isPresent()) {
            return;
        }
        if (firstItem.get().getType() != Material.ENCHANTED_BOOK
                || secondItem.get().getType() != Material.ENCHANTED_BOOK) {
            return;
        }
        Iterator<Enchantment> enchantments = Registry.ENCHANTMENT.iterator();
        while (enchantments.hasNext()) {
            Enchantment enchantment = enchantments.next();
            if (enchantment.getMaxLevel() <= 1) {
                continue;
            }

            int minLVL = firstItem.get().getEnchantmentLevel(enchantment);
            int maxLVL = secondItem.get().getEnchantmentLevel(enchantment);
            int vanillaMaxLevel = enchantment.getMaxLevel();
            int customMaxLevel = vanillaMaxLevel + 5;

            if (minLVL != maxLVL || minLVL < 1 || minLVL >= customMaxLevel) {
                continue;
            }

            ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
            result.addUnsafeEnchantment(enchantment, minLVL + 1);
            e.setResult(result);
            break;
        }
    }
}
