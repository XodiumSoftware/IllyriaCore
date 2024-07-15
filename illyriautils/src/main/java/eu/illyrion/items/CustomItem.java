package eu.illyrion.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {
    public static String namespace = "IllyrionUtils";

    /**
     * Initializes the custom items by creating the necessary recipes.
     */
    public static void init() {
        ItemStack condensedPotato = createCustomItem(Material.POTATO, "{#FFA500}Condensed Potato",
                Arrays.asList("{#FFFFFF}This is a condensed potato made from 9 potatoes",
                        "{#FFFFFF}Another line of lore"));

        createRecipe(
                new RecipeShaped(new ItemStack(Material.POTATO), condensedPotato, 1,
                        Arrays.asList("PPP", "PPP", "PPP")),
                new RecipeShapeless(condensedPotato, new ItemStack(Material.POTATO), 9));
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
            meta.setDisplayName(ColorUtil.parseColor(name));
            if (lores != null) {
                List<String> parsedLores = new ArrayList<>();
                for (String lore : lores) {
                    parsedLores.add(ColorUtil.parseColor(lore));
                }
                meta.setLore(parsedLores);
            }
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Creates a recipe for a given item.
     *
     * @param shaped    The shaped recipe object.
     * @param shapeless The shapeless recipe object.
     */
    private static void createRecipe(RecipeShaped shaped, RecipeShapeless shapeless) {
        if (shaped != null) {
            ItemStack inputItem = shaped.inputItem;
            ItemStack outputItem = new ItemStack(shaped.outputItem.getType(), shaped.outputQuantity);
            List<String> shape = shaped.shape;
            ShapedRecipe shapedRecipe = new ShapedRecipe(
                    new NamespacedKey(namespace, outputItem.getType().toString().toLowerCase()), outputItem);
            shapedRecipe.shape(shape.toArray(new String[0]));
            shapedRecipe.setIngredient('P', inputItem.getType());
            Bukkit.addRecipe(shapedRecipe);
        }

        if (shapeless != null) {
            ItemStack inputItem = shapeless.inputItem;
            ItemStack outputItem = new ItemStack(shapeless.outputItem.getType(), shapeless.outputQuantity);
            ShapelessRecipe shapelessRecipe = new ShapelessRecipe(
                    new NamespacedKey(namespace, outputItem.getType().toString().toLowerCase()), outputItem);
            shapelessRecipe.addIngredient(inputItem.getType());
            Bukkit.addRecipe(shapelessRecipe);
        }
    }
}