package eu.illyrion.items;

import org.bukkit.inventory.ItemStack;

class RecipeShapeless {
    public ItemStack inputItem;
    public ItemStack outputItem;
    public int outputQuantity;

    /**
     * Constructs a new shapeless recipe.
     *
     * @param inputItem      the input item required for the recipe
     * @param outputItem     the output item produced by the recipe
     * @param outputQuantity the quantity of the output item produced
     */
    public RecipeShapeless(ItemStack inputItem, ItemStack outputItem, int outputQuantity) {
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        this.outputQuantity = outputQuantity;
    }
}