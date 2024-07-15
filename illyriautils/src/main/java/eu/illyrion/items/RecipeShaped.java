package eu.illyrion.items;

import java.util.List;

import org.bukkit.inventory.ItemStack;

class RecipeShaped {
    public ItemStack inputItem;
    public ItemStack outputItem;
    public int outputQuantity;
    public List<String> shape;

    /**
     * Constructs a new RecipeShaped object with the specified parameters.
     *
     * @param inputItem      the input item required for the recipe
     * @param outputItem     the output item produced by the recipe
     * @param outputQuantity the quantity of the output item produced
     * @param shape          the shape of the recipe
     */
    public RecipeShaped(ItemStack inputItem, ItemStack outputItem, int outputQuantity, List<String> shape) {
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        this.outputQuantity = outputQuantity;
        this.shape = shape;
    }
}