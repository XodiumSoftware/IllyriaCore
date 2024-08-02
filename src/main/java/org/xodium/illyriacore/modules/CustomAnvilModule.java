package org.xodium.illyriacore.modules;

import java.util.Iterator;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;

/**
 * The CustomAnvilModule implements Listener.
 */
public class CustomAnvilModule implements Listener {

    // TODO: fix max enchantment going back to vanilla.
    // TODO: enchantment level upgrade skipping when starting from lower than V.
    // TODO: adjust xp to upgrade enchantment level based on the level dynamically.

    /**
     * Handles the PrepareAnvilEvent, which is triggered when a player prepares an
     * anvil.
     * This method checks if the first and second items in the anvil inventory are
     * enchanted books,
     * and if they have only one enchantment each. If so, it increases the level of
     * the enchantment
     * by 1 (up to a maximum of the vanilla maximum level + 5) and sets the result
     * of the anvil operation
     * to the newly enchanted book.
     *
     * @param e The PrepareAnvilEvent object representing the event being handled.
     */
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {
        AnvilInventory anvilInventory = e.getInventory();
        Optional<ItemStack> firstItem = Optional.ofNullable(anvilInventory.getItem(0));
        Optional<ItemStack> secondItem = Optional.ofNullable(anvilInventory.getItem(1));

        if (!firstItem.isPresent() || !secondItem.isPresent() ||
                firstItem.get().getType() != Material.ENCHANTED_BOOK ||
                secondItem.get().getType() != Material.ENCHANTED_BOOK) {
            return;
        }

        EnchantmentStorageMeta firstMeta = (EnchantmentStorageMeta) firstItem.get().getItemMeta();
        EnchantmentStorageMeta secondMeta = (EnchantmentStorageMeta) secondItem.get().getItemMeta();

        if (firstMeta.getStoredEnchants().size() != 1 || secondMeta.getStoredEnchants().size() != 1) {
            return;
        }

        RegistryAccess registryAccess = RegistryAccess.registryAccess();
        Iterator<Enchantment> enchantments = registryAccess.getRegistry(RegistryKey.ENCHANTMENT).iterator();
        while (enchantments.hasNext()) {
            Enchantment enchantment = enchantments.next();

            if (enchantment.getMaxLevel() <= 1) {
                continue;
            }

            int minLVL = firstMeta.getStoredEnchantLevel(enchantment);
            int maxLVL = secondMeta.getStoredEnchantLevel(enchantment);

            int vanillaMaxLevel = enchantment.getMaxLevel();
            int customMaxLevel = vanillaMaxLevel + 5;

            if (minLVL != maxLVL || minLVL < 1 || minLVL >= customMaxLevel) {
                continue;
            }

            ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta resultMeta = (EnchantmentStorageMeta) result.getItemMeta();
            int newLevel = Math.min(minLVL + 1, customMaxLevel);
            resultMeta.addStoredEnchant(enchantment, newLevel + 1, true);
            result.setItemMeta(resultMeta);
            e.setResult(result);
            break;
        }
    }
}
