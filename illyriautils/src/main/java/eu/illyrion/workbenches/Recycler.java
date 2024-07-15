package eu.illyrion.workbenches;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import eu.illyrion.constants.RecyclerConstants;

public class Recycler extends JavaPlugin implements Listener {
    private final Map<UUID, ItemStack> confirmationMap = new HashMap<>();

    /**
     * Handles the player interact event.
     *
     * @param event The PlayerInteractEvent to handle.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (isInvalidInteractEvent(event)) {
            return;
        }

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (!player.hasPermission(RecyclerConstants.PERMISSION_RECYCLE)) {
            player.sendMessage(RecyclerConstants.MSG_NO_PERMISSION);
            return;
        }

        if (confirmationMap.containsKey(playerId)) {
            player.sendMessage(RecyclerConstants.MSG_PENDING_CONFIRMATION);
            return;
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        player.sendMessage(RecyclerConstants.MSG_CONFIRM_RECYCLE);
        confirmationMap.put(playerId, itemInHand.clone());
        event.setCancelled(true);
    }

    /**
     * Checks if the given PlayerInteractEvent is an invalid interact event.
     * An interact event is considered invalid if any of the following conditions
     * are met:
     * - The action is not a right click on a block
     * - The clicked block is not an iron block
     * - The player is not sneaking
     *
     * @param event The PlayerInteractEvent to check
     * @return true if the event is an invalid interact event, false otherwise
     */
    private boolean isInvalidInteractEvent(PlayerInteractEvent event) {
        return event.getAction() != Action.RIGHT_CLICK_BLOCK ||
                event.getClickedBlock().getType() != Material.IRON_BLOCK ||
                !event.getPlayer().isSneaking();
    }

    /**
     * Handles the player chat event.
     * This method is called when a player sends a chat message.
     * It checks if the player is in the confirmation map and cancels the event if
     * so.
     * It then calls the handleChatResponse method to handle the chat response.
     *
     * @param event The AsyncPlayerChatEvent object representing the chat event.
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        String message = event.getMessage().toLowerCase();

        if (confirmationMap.containsKey(playerId)) {
            event.setCancelled(true);
            handleChatResponse(player, playerId, message);
        }
    }

    /**
     * Handles the chat response from a player.
     *
     * @param player   The player who sent the chat response.
     * @param playerId The UUID of the player who sent the chat response.
     * @param message  The chat message sent by the player.
     */
    private void handleChatResponse(Player player, UUID playerId, String message) {
        if (message.equals(RecyclerConstants.RESPONSE_YES)) {
            recycleItem(player, playerId);
        } else if (message.equals(RecyclerConstants.RESPONSE_NO)) {
            confirmationMap.remove(playerId);
            player.sendMessage(RecyclerConstants.MSG_RECYCLING_CANCELLED);
        } else {
            player.sendMessage(RecyclerConstants.MSG_INVALID_RESPONSE);
        }
    }

    /**
     * Recycles the item held by the player.
     *
     * @param player   The player who is recycling the item.
     * @param playerId The UUID of the player.
     */
    private void recycleItem(Player player, UUID playerId) {
        ItemStack itemInHand = confirmationMap.remove(playerId);

        if (itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasEnchants()) {
            ItemMeta meta = itemInHand.getItemMeta();
            for (Map.Entry<Enchantment, Integer> enchantment : meta.getEnchants().entrySet()) {
                ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
                bookMeta.addStoredEnchant(enchantment.getKey(), enchantment.getValue(), true);
                enchantedBook.setItemMeta(bookMeta);
                addItemToInventoryOrDrop(player, enchantedBook);
            }
            itemInHand.setItemMeta(null);
        }

        for (Recipe recipe : getServer().getRecipesFor(itemInHand)) {
            if (recipe instanceof ShapedRecipe) {
                ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;

                for (ItemStack ingredient : shapedRecipe.getIngredientMap().values()) {
                    if (ingredient != null && ingredient.getType() != Material.AIR) {
                        addItemToInventoryOrDrop(player, ingredient);
                    }
                }

                itemInHand.setAmount(itemInHand.getAmount() - 1);
                player.getInventory().setItemInMainHand(itemInHand.getAmount() > 0 ? itemInHand : null);
                player.sendMessage(RecyclerConstants.MSG_ITEM_RECYCLED_SUCCESSFULLY);
                break;
            }
        }
    }

    /**
     * Adds an item to the player's inventory or drops it at the player's location
     * if the inventory is full.
     *
     * @param player The player to receive the item.
     * @param item   The item to be added or dropped.
     */
    private void addItemToInventoryOrDrop(Player player, ItemStack item) {
        Map<Integer, ItemStack> remainingItems = player.getInventory().addItem(item);
        if (!remainingItems.isEmpty()) {
            for (ItemStack remainingItem : remainingItems.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), remainingItem);
            }
        }
    }
}