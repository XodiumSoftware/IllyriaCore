package org.xodium.illyriacore.handlers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import org.xodium.illyriacore.builders.CustomItemBuilder;
import org.xodium.illyriacore.interfaces.ItemsInterface;
import org.xodium.illyriacore.utils.IllyriaUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomItemHandler implements ItemsInterface {

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
                    Map<String, Object> itemData = IllyriaUtils.castMap(itemObj, String.class, Object.class);
                    Material material = Material.valueOf((String) itemData.get(ITEM_MATERIAL));
                    String name = (String) itemData.get(ITEM_NAME);
                    List<String> lores = IllyriaUtils.castList(itemData.get(ITEM_LORE), String.class);
                    Map<Enchantment, Integer> enchantments = IllyriaUtils.castMap(itemData.get(ITEM_ENCHANTMENT),
                            Enchantment.class,
                            Integer.class);
                    boolean unbreakable = (boolean) itemData.get(ITEM_UNBREAKABLE);
                    int customModelData = (int) itemData.get(ITEM_CUSTOM_MODEL_DATA);
                    List<ItemFlag> itemFlags = IllyriaUtils.castList(itemData.get(ITEM_FLAGS), ItemFlag.class);
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
