package cc.pollo.gladeus.hotbar.model;

import cc.pollo.gladeus.item.StackBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Represents a state in the hotbar, i.e where clicking <br>
 * an item has a unique action
 */
public class HotbarItemState {

    private ItemStack stack;
    private StackBuilder stackBuilder;

    private final Consumer<PlayerInteractEvent> clickConsumer;

    public HotbarItemState(StackBuilder stackBuilder, Consumer<PlayerInteractEvent> clickConsumer){
        this.stackBuilder  = stackBuilder;
        this.clickConsumer = clickConsumer;
    }

    public HotbarItemState(ItemStack stack, Consumer<PlayerInteractEvent> clickConsumer){
        this.stack         = stack;
        this.clickConsumer = clickConsumer;
    }

    /**
     * Gets the itemstack that this state has
     * @param player (optional) specific player to get for, this <br>
     * is useful with localized ItemBuilders to get appropriate locale.
     * @return built stack
     */
    @Nullable
    public ItemStack getStack(Player player){
        return stackBuilder != null ? stackBuilder.toStack(player) : stack;
    }

    public Consumer<PlayerInteractEvent> getClickConsumer() {
        return clickConsumer;
    }

}
