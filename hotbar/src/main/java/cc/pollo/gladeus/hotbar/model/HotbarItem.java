package cc.pollo.gladeus.hotbar.model;

import cc.pollo.gladeus.item.StackBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Holds information about an item of the hotbar
 */
public class HotbarItem {

    /**
     * Whether or not this hotbar item is in carousel mode, i.e <br>
     * we should move to the next state in the list if not found
     */
    private final boolean carousel;
    private final HotbarItemState[] states;

    private int slot;

    /**
     * Index of the current state
     */
    private int stateIndex;

    public HotbarItem(ItemStack stack, Consumer<PlayerInteractEvent> clickConsumer){
        this(false, new HotbarItemState(stack, clickConsumer));
    }

    public HotbarItem(StackBuilder builder, Consumer<PlayerInteractEvent> clickConsumer){
        this(false, new HotbarItemState(builder, clickConsumer));
    }

    /**
     * Constructs a new HotbarItem.
     *
     * @param carousel if item is in carousel mode, i.e we should move to the
     *                 next state in the list when we click it
     *
     * @param states states that this item will have
     */
    public HotbarItem(boolean carousel, HotbarItemState... states){
        this.carousel = carousel;
        this.states   = states;
    }

    /**
     * Called when the player clicks with this item
     * @param event interact event for this click
     */
    public void onClick(PlayerInteractEvent event){
        Consumer<PlayerInteractEvent> clickConsumer = getCurrentState().getClickConsumer();
        if(clickConsumer != null)
            clickConsumer.accept(event);

        if(carousel){
            if(stateIndex == states.length - 1){
                setState(0); // Start over if reached end
            } else {
                setState(stateIndex + 1);
            }

            apply(event.getPlayer());
        }
    }

    /**
     * Sets the current state index
     * @param index index to set
     */
    public void setState(int index){
        this.stateIndex = index;
    }

    /**
     * Sets the slot of the item, this is only used internally.
     * @param slot slot index to hold this item
     */
    void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Adds the item to the player's inventory
     * @param player player to add for
     */
    public void apply(Player player){
        player.getInventory().setItem(slot, getCurrentState().getStack(player));
    }

    /**
     * Gets the current item state
     * @return current item state
     */
    public HotbarItemState getCurrentState(){
        return states[stateIndex];
    }

}