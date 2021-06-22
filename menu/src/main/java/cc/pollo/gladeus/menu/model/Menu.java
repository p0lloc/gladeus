package cc.pollo.gladeus.menu.model;

import cc.pollo.gladeus.menu.MenuManager;
import cc.pollo.texty.Texty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class Menu extends MenuItemCollection {

    private final UUID player;
    protected int rows;
    private Component title;
    private Inventory inventory;
    private final int tickInterval;
    private boolean modifiable;
    private int tick;

    public Menu(Component title, int rows, Player player) {
        this(title, rows, player, false);
    }

    public Menu(Component title, int rows, Player player, boolean modifiable) {
        this(title, rows, player, 0, modifiable);
    }

    public Menu(Component title, int rows, Player player, int tickInterval, boolean modifiable) {
        super(rows * 9);
        this.title = title;
        this.rows = rows;
        this.player = player.getUniqueId();
        this.modifiable = modifiable;
        this.tickInterval = tickInterval;
    }

    public void open(MenuManager menuManager) {
        this.open(menuManager, null);
    }

    public void open(MenuManager menuManager, Texty texty) {
        Player player = this.getPlayer();
        if (player != null) {
            this.inventory = Bukkit.createInventory(null, this.items.length,
                    texty != null ? texty.renderComponent(this.title, player) : this.title);

            player.closeInventory();
            menuManager.set(player, this);

            buildMenu();
            fillInventory();
            player.openInventory(this.inventory);
        }
    }

    private void fillInventory() {
        for(int i = 0; i < this.items.length; i++) {
            MenuItem item = this.items[i];
            insertItem(i, item);
        }
    }

    /**
     * Updates all changed slots
     */
    public void updateChanged() {
        for (int i = 0; i < items.length; i++) {
            MenuItem item = items[i];
            if(!item.isInserted())
                insertItem(i, item);
        }
    }

    private void insertItem(int slot, MenuItem item) {
        ItemStack stack;
        if(item != null){
            stack = item.getStack(getPlayer());
            item.setInserted(true);
        } else {
            stack = new ItemStack(Material.AIR);
        }

        inventory.setItem(slot, stack);
    }

    protected void update(int slot, MenuItem item) {
        set(slot, item);
        insertItem(slot, item);
    }

    public Component getTitle() {
        return this.title;
    }

    public void setTitle(Component title) {
        this.title = title;
    }

    protected Player getPlayer() {
        return Bukkit.getPlayer(this.player);
    }

    public boolean isModifiable() {
        return this.modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }

    public int getTickInterval() { return this.tickInterval; }

    public int getTick() {
        return this.tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public abstract void buildMenu();

    public void onTick() { }

    /**
     * Method called when the menu is opened. <br>
     * This should be overriden if you wish to use it.
     *
     * @param event Bukkit open event
     */
    public void onOpen(InventoryOpenEvent event) { }

    /**
     * Method called when the menu is closed. <br>
     * This should be overriden if you wish to use it.
     *
     * @param event Bukkit close event
     */
    public void onClose(InventoryCloseEvent event) { }

}