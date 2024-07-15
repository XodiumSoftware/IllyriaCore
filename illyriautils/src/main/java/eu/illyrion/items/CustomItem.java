package eu.illyrion.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.illyrion.utils.Utils;

public class CustomItem {
    public static String namespace = "IllyrionUtils";

    /**
     * Initializes the custom items by creating the necessary recipes.
     */
    public static void init() {
        createCustomItem(Material.POTATO, "{#FFA500}Condensed Potato",
                Arrays.asList("{#FFFFFF}This is a condensed potato made from 9 potatoes",
                        "{#FFFFFF}Another line of lore"));
    }

    /**
     * Creates a custom item.
     *
     * @param material The material of the item.
     * @param name     The display name of the item.
     * @param lores    The list of lore strings for the item.
     * @return The ItemStack representing the custom item.
     */
    private static ItemStack createCustomItem(Material material, String name, List<String> lores) {
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
            item.setItemMeta(meta);
        }

        return item;
    }

}