package org.xodium.illyriacore.handlers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.xodium.illyriacore.IllyriaCore;
import org.xodium.illyriacore.utils.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

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

import eu.illyrion.illyriacore.interfaces.MessagesInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class CustomItemBuilder implements MessagesInterface {
    // TODO: make the items.yml be generated without having it in the resources
    // folder

    private static final String ITEM_DURABILITY = "durability";
    private static final String ITEM_FLAGS = "itemFlags";
    private static final String ITEM_CUSTOM_MODEL_DATA = "customModelData";
    private static final String ITEM_UNBREAKABLE = "unbreakable";
    private static final String ITEM_ENCHANTMENT = "enchantments";
    private static final String ITEM_LORE = "lores";
    private static final String ITEM_NAME = "name";
    private static final String ITEM_MATERIAL = "material";
    private static final String ITEMS = "items";
    private static final String ITEMS_YML = "items.yml";

    /**
     * Initializes the custom items.
     */
    public static void init() {
        File file = new File(ITEMS_YML);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<?> items = config.getList(ITEMS);
        if (items != null) {
            for (Object itemObj : items) {
                if (itemObj instanceof Map) {
                    Map<String, Object> itemData = Utils.castMap(itemObj, String.class, Object.class);

                    Material material = Material.valueOf((String) itemData.get(ITEM_MATERIAL));
                    String name = (String) itemData.get(ITEM_NAME);
                    List<String> lores = Utils.castList(itemData.get(ITEM_LORE), String.class);
                    Map<Enchantment, Integer> enchantments = Utils.castMap(itemData.get(ITEM_ENCHANTMENT),
                            Enchantment.class,
                            Integer.class);
                    boolean unbreakable = (boolean) itemData.get(ITEM_UNBREAKABLE);
                    int customModelData = (int) itemData.get(ITEM_CUSTOM_MODEL_DATA);
                    List<ItemFlag> itemFlags = Utils.castList(itemData.get(ITEM_FLAGS), ItemFlag.class);
                    int durability = (int) itemData.get(ITEM_DURABILITY);

                    new CustomItemBuilder(material)
                            .name(name)
                            .lores(lores)
                            .enchantments(enchantments)
                            .unbreakable(unbreakable)
                            .customModelData(customModelData)
                            .itemFlags(itemFlags)
                            .durability(durability)
                            .build();
                }
            }
        }
    }
}
