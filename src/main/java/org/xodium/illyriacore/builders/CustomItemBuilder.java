package org.xodium.illyriacore.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import org.xodium.illyriacore.interfaces.MessagesInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class CustomItemBuilder implements MessagesInterface {

    private static final String KEY = "custom_model_data";
    private String name;

    private List<String> lores = Collections.emptyList();
    private List<ItemFlag> itemFlags = Collections.emptyList();

    private Map<Enchantment, Integer> enchantments = Collections.emptyMap();
    private Map<Attribute, AttributeModifier> attributeModifiers = Collections.emptyMap();

    private boolean unbreakable;

    private int customModelData;
    private int durability;

    private final Material material;

    /**
     * Constructs a new CustomItemBuilder with the specified material.
     *
     * @param material the material of the custom item
     */
    public CustomItemBuilder(Material material) {
        this.material = material;
    }

    /**
     * Sets the name of the custom item.
     *
     * @param name the name of the custom item
     * @return the CustomItemBuilder instance
     */
    public CustomItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the list of lores for the custom item.
     *
     * @param lores the list of lores to set
     * @return the CustomItemBuilder instance
     */
    public CustomItemBuilder lores(List<String> lores) {
        this.lores = lores;
        return this;
    }

    /**
     * Sets the enchantments for the custom item.
     *
     * @param enchantments a map containing the enchantments and their corresponding
     *                     levels
     * @return the CustomItemBuilder instance
     */
    public CustomItemBuilder enchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    /**
     * Sets the unbreakable status of the custom item.
     *
     * @param unbreakable true if the item should be unbreakable, false otherwise
     * @return the CustomItemBuilder instance
     */
    public CustomItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    /**
     * Sets the custom model data for the custom item.
     *
     * @param customModelData the custom model data to set
     * @return the CustomItemBuilder instance
     */
    public CustomItemBuilder customModelData(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    /**
     * Sets the item flags for this custom item.
     *
     * @param itemFlags the list of item flags to set
     * @return the CustomItemBuilder instance
     */
    public CustomItemBuilder itemFlags(List<ItemFlag> itemFlags) {
        this.itemFlags = itemFlags;
        return this;
    }

    /**
     * Sets the durability of the custom item.
     *
     * @param durability the durability value to set
     * @return the CustomItemBuilder instance
     */
    public CustomItemBuilder durability(int durability) {
        this.durability = durability;
        return this;
    }

    /**
     * Sets the attribute modifiers for the custom item.
     *
     * @param attributeModifiers a map of attribute modifiers to be applied to the
     *                           custom item
     * @return the CustomItemBuilder instance
     */
    public CustomItemBuilder attributeModifiers(Map<Attribute, AttributeModifier> attributeModifiers) {
        this.attributeModifiers = attributeModifiers;
        return this;
    }

    /**
     * Builds and returns an ItemStack with the specified properties.
     *
     * @return The built ItemStack.
     */
    public ItemStack build() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.displayName(MiniMessage.miniMessage().deserialize(name));
            List<Component> parsedLores = new ArrayList<>();
            for (String lore : lores) {
                parsedLores.add(MiniMessage.miniMessage().deserialize(lore));
            }
            meta.lore(parsedLores);
            for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
                meta.addEnchant(enchantment.getKey(), enchantment.getValue(), true);
            }
            meta.setUnbreakable(unbreakable);
            if (customModelData != 0) {
                meta.setCustomModelData(customModelData);
            }
            for (ItemFlag flag : itemFlags) {
                meta.addItemFlags(flag);
            }
            if (durability > 0) {
                ((Damageable) meta).setDamage(durability);
            }
            for (Map.Entry<Attribute, AttributeModifier> entry : attributeModifiers.entrySet()) {
                meta.addAttributeModifier(entry.getKey(), entry.getValue());
            }
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            NamespacedKey nkey = new NamespacedKey(NAMESPACE, KEY);
            dataContainer.set(nkey, PersistentDataType.INTEGER, customModelData);
            item.setItemMeta(meta);
        }
        return item;
    }
}