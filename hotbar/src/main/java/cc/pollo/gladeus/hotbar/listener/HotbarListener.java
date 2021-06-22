package cc.pollo.gladeus.hotbar.listener;

import cc.pollo.gladeus.hotbar.HotbarManager;
import cc.pollo.gladeus.hotbar.model.Hotbar;
import cc.pollo.gladeus.hotbar.model.HotbarItem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Listens to events regarding the hotbar, does some cancelling <br>
 * to make things look better to the player.
 */
public class HotbarListener implements Listener {

    private final HotbarManager hotbarManager;

    public HotbarListener(HotbarManager hotbarManager){
        this.hotbarManager = hotbarManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){

        if(event.getHand() != EquipmentSlot.HAND)
            return;

        Action action = event.getAction();
        if(action == Action.PHYSICAL)
            return;

        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        Hotbar hotbar = hotbarManager.getHotbar(player);
        if(hotbar == null)
            return;

        HotbarItem item = hotbar.getActiveItemInSlot(inventory.getHeldItemSlot(), event.getItem());
        if(item != null)
            item.onClick(event);

    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event){

        Player player = event.getPlayer();
        Hotbar hotbar = hotbarManager.getHotbar(player);

        if(hotbar == null)
            return;

        PlayerInventory inventory = player.getInventory();

        HotbarItem item = hotbar.getActiveItemInSlot(inventory.getHeldItemSlot(), inventory.getItemInMainHand());

        if(item != null)
            event.setCancelled(true);

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

        ItemStack currentItem = event.getCurrentItem();
        HumanEntity whoClicked = event.getWhoClicked();

        if(!(whoClicked instanceof Player) || currentItem == null)
            return;

        Player player = (Player) whoClicked;
        HotbarItem item = getItem(player, event.getSlot(), currentItem, event.getClickedInventory());

        if(item != null)
            event.setCancelled(true);

    }

    /**
     * Gets a item in the players hotbar by also compares things like inventory which might vary.
     * @param player player to check for
     * @param slot slot where item should be
     * @param stack stack to compare with
     * @param inventory inventory to compare
     * @return found item or null if not found
     */
    private HotbarItem getItem(Player player, int slot, ItemStack stack, Inventory inventory){

        if (inventory != null && inventory.getType() != InventoryType.PLAYER)
            return null;

        Hotbar hotbar = hotbarManager.getHotbar(player);
        if(hotbar == null)
            return null;

        if(slot < 0 || slot > 8) // Hotbar has 9 slots, this item is invalid
            return null;

        return hotbar.getActiveItemInSlot(slot, stack);

    }

}