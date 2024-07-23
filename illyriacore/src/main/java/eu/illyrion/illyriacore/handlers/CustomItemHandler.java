package eu.illyrion.illyriacore.handlers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import eu.illyrion.illyriacore.builders.CustomItemBuilder;
import eu.illyrion.illyriacore.interfaces.ItemsInterface;
import eu.illyrion.illyriacore.utils.Utils;

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
