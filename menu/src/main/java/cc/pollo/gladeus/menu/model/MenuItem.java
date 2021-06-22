package cc.pollo.gladeus.menu.model;

import cc.pollo.gladeus.item.StackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MenuItem {

    private ItemStack stack;
    private StackBuilder stackBuilder;

    private boolean inserted;

    private final boolean modifiable;
    private final Consumer<MenuItemClick> clickConsumer;

    public MenuItem(@NotNull StackBuilder stackBuilder) {
        this(stackBuilder, click -> { });
    }

    public MenuItem(@NotNull StackBuilder stackBuilder, @NotNull Consumer<MenuItemClick> clickConsumer) {
        this(stackBuilder, false, clickConsumer);
    }

    /**
     * Constructs a menu item that uses a StackBuilder which be rendered
     * @param stackBuilder builder to render later
     * @param modifiable whether this item should be able to be moved out of its slot
     * @param clickConsumer consumer called when a player clicks this item
     */
    public MenuItem(@NotNull StackBuilder stackBuilder,
                    boolean modifiable, @NotNull Consumer<MenuItemClick> clickConsumer) {

        this.stackBuilder = stackBuilder;
        this.modifiable = modifiable;
        this.clickConsumer = clickConsumer;
    }

    public MenuItem(@NotNull ItemStack stack) {
        this(stack, false, click -> { });
    }

    public MenuItem(@NotNull ItemStack stack, @NotNull Consumer<MenuItemClick> clickConsumer) {
        this(stack, false, clickConsumer);
    }

    /**
     * Constructs a menu item that has a pre-defined stack
     * @param stack stack to display
     * @param modifiable whether this item should be able to be moved out of its slot
     * @param clickConsumer consumer called when a player clicks this item
     */
    public MenuItem(@NotNull ItemStack stack, boolean modifiable,
                    @NotNull Consumer<MenuItemClick> clickConsumer) {

        this.stack = stack;
        this.modifiable = modifiable;
        this.clickConsumer = clickConsumer;
    }

    public ItemStack getStack(Player player) {
        if (stackBuilder == null) {
            return stack;
        } else {
            return stackBuilder.toStack(player);
        }
    }

    public void setInserted(boolean inserted) {
        this.inserted = inserted;
    }

    public boolean isInserted() {
        return inserted;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public Consumer<MenuItemClick> getClickConsumer() {
        return clickConsumer;
    }

    public static MenuItem empty() {
        return new MenuItem(new ItemStack(Material.AIR));
    }

}