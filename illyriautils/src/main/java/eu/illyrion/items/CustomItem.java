package eu.illyrion.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

/**
 * Represents a custom item in the game.
 * This class provides methods to initialize and create a condensed potato item.
 */
public class CustomItem {

    public static String namespace = "IllyrionUtils";
    private static final String ORANGE_HEX = "#FFA500";
    private static final String WHITE_HEX = "#FFFFFF";

    /**
     * Initializes the custom items by giving each online player a condensed potato.
     */
    public static void init() {
        ItemStack condensedPotato = createCondensedPotato();
        for (Player player : Bukkit.getOnlinePlayers()) {
            givePlayerCondensedPotato(player, condensedPotato);
        }
    }

    /**
     * Creates a condensed potato item.
     *
     * @return The ItemStack representing the condensed potato.
     */
    private static ItemStack createCondensedPotato() {
        ItemStack item = new ItemStack(Material.POTATO);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(
                    Component.text("Condensed Potato").color(TextColor.fromHexString(ORANGE_HEX)).toString());
            List<String> lore = Arrays.asList(
                    Component.text("This is a condensed potato made from 9 potatoes")
                            .color(TextColor.fromHexString(WHITE_HEX)))
                    .stream()
                    .map(Component::toString)
                    .collect(Collectors.toList());
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Gives the player a condensed potato item and adds the necessary recipes to
     * convert between condensed potatoes and regular potatoes.
     *
     * @param player The player to give the condensed potato item to.
     * @param item   The condensed potato item to give to the player.
     */
    private static void givePlayerCondensedPotato(Player player, ItemStack item) {
        ShapedRecipe toCondensed = new ShapedRecipe(new NamespacedKey(namespace, "condensed_potato"),
                item);
        toCondensed.shape("PPP", "PPP", "PPP");
        toCondensed.setIngredient('P', Material.POTATO);

        ShapelessRecipe toPotatoes = new ShapelessRecipe(new NamespacedKey(namespace, "potatoes"),
                new ItemStack(Material.POTATO, 9));
        toPotatoes.addIngredient(item.getData());

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(toCondensed);
        recipes.add(toPotatoes);

        for (Recipe recipe : recipes) {
            Bukkit.addRecipe(recipe);
        }

        player.getInventory().addItem(item);
    }
}
