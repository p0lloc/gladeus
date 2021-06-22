package cc.pollo.gladeus.hotbar.model;

import cc.pollo.gladeus.hotbar.HotbarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents the whole hotbar which will be applied to the player (slots 1-9)
 */
public abstract class Hotbar {

    private HotbarItem[] items;
    private final UUID playerId;

    public Hotbar(@NotNull Player player){
        this.playerId = player.getUniqueId();
    }

    /**
     * Adds the items to the player and notifies the hotbar manager that the player now has this hotbar applied
     * @param hotbarManager manager which should manage this hotbar
     */
    public void apply(HotbarManager hotbarManager){
        Player player = getPlayer();
        if(player == null)
            return;

        makeReady();
        for (HotbarItem item : items) {
            if (item != null) {
                item.apply(player);
            }
        }

        hotbarManager.setHotbar(player, this);
    }

    /**
     * Gets the active hotbar item in slot. <br>
     * This makes sure that the stack is actually the one that was set earlier.
     *
     * @param slot slot to check at
     * @param check stack to compare with
     * @return hotbar item or null it not found
     */
    public HotbarItem getActiveItemInSlot(int slot, ItemStack check){
        Player player = getPlayer();
        if(player == null)
            return null;

        HotbarItem item = getItemAtSlot(slot);
        if(item != null){
            ItemStack stack = item.getCurrentState().getStack(player);

            if(stack != null && stack.isSimilar(check)){
                return item;
            }
        }

        return null;
    }

    /**
     * Gets the item specified for the certain slot or null if not found
     * @param slot slot to get (index)
     * @return specified HotbarItem or null
     */
    public HotbarItem getItemAtSlot(int slot){
        return getItems()[slot];
    }

    /**
     * Makes the hotbar ready for application by assigning slot according to array index.<br>
     * It would be unnecessary for implementors to specify slot twice.
     */
    private void makeReady(){
        if(items == null) { // If we aren't ready
            items = build().getItems();
            for (int i = 0; i < items.length; i++) {
                HotbarItem item = items[i];
                if(item != null)
                    item.setSlot(i);
            }
        }
    }

    /**
     * Gets all the items of this hotbar
     * @return array of items
     */
    public HotbarItem[] getItems() {
        makeReady();
        return items;
    }

    @Nullable
    private Player getPlayer(){
        return Bukkit.getPlayer(playerId);
    }

    /**
     * Abstract method which should build the hotbar
     * @return repository with all hotbar items that this hotbar should contain
     */
    public abstract HotbarItemCollection build();

}
