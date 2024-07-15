package eu.illyrion.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import eu.illyrion.utils.Utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionMeta;

public class CustomItem {

    /**
     * Initializes the custom item.
     */
    public static void init() {
        createCustomItem(Material.POTATO, "{#FFA500}Condensed Potato",
                Arrays.asList("{#FFFFFF}This is a condensed potato made from 9 potatoes",
                        "{#FFFFFF}Another line of lore"),
                null, false, 0, null, 0, null);
    }

    /**
     * Creates a custom ItemStack with the specified properties.
     *
     * @param material           The material of the item.
     * @param name               The display name of the item.
     * @param lores              The list of lore lines for the item.
     * @param enchantments       The map of enchantments and their levels for the
     *                           item.
     * @param unbreakable        Whether the item should be unbreakable.
     * @param customModelData    The custom model data for the item.
     * @param itemFlags          The list of item flags for the item.
     * @param durability         The durability of the item.
     * @param attributeModifiers The map of attribute modifiers for the item.
     * @return The created custom ItemStack.
     */
    private static ItemStack createCustomItem(Material material, String name, List<String> lores,
            Map<Enchantment, Integer> enchantments, boolean unbreakable,
            int customModelData, List<ItemFlag> itemFlags, int durability,
            Map<Attribute, AttributeModifier> attributeModifiers) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(Utils.parseColor(name));
            if (lores != null) {
                List<String> parsedLores = new ArrayList<>();
                for (String lore : lores) {
                    parsedLores.add(Utils.parseColor(lore));
                }
                meta.setLore(parsedLores);
            }
            if (enchantments != null) {
                for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
                    meta.addEnchant(enchantment.getKey(), enchantment.getValue(), true);
                }
            }
            meta.setUnbreakable(unbreakable);
            if (customModelData != 0) {
                meta.setCustomModelData(customModelData);
            }
            if (itemFlags != null) {
                for (ItemFlag flag : itemFlags) {
                    meta.addItemFlags(flag);
                }
            }
            if (durability > 0) {
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta instanceof Damageable) {
                    Damageable damageable = (Damageable) itemMeta;
                    damageable.setDamage(durability);
                    item.setItemMeta(itemMeta);
                }
            }
            if (attributeModifiers != null) {
                for (Map.Entry<Attribute, AttributeModifier> entry : attributeModifiers.entrySet()) {
                    meta.addAttributeModifier(entry.getKey(), entry.getValue());
                }
            }
            item.setItemMeta(meta);
        }

        return item;
    }
}