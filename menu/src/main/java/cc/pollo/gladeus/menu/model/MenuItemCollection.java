package cc.pollo.gladeus.menu.model;

import cc.pollo.gladeus.item.StackBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MenuItemCollection {

    protected final MenuItem[] items;

    private static final Component DEFAULT_EMPTY_NAME = Component.text("", NamedTextColor.GRAY);

    /**
     * Keeps track of index when ${@link #add(MenuItem)} is used
     */
    private int addIndex;

    public MenuItemCollection(int size){
        if(size % 9 != 0)
            throw new IllegalArgumentException("size must be a multiple of 9!");

        items   = new MenuItem[size];
    }

    /**
     * Sets item at specific slot
     * @param slot slot to set item at
     * @param item item to set
     */
    protected void set(int slot, MenuItem item){
        items[slot] = item;
    }

    /**
     * Appends a new menu item to this repository <br>
     *
     * Note: This keeps track of index on its own
     * @param item item to add
     */
    public void add(MenuItem item){
        set(addIndex, item);
        addIndex++;
    }

    /**
     * Copies all items from another repository
     * @param repository repository to get items from
     */
    public void setAll(MenuItemCollection repository){
        for (int i = 0; i < repository.getItems().length; i++) {
            MenuItem item = repository.getItems()[i];
            if(item != null){
                items[i] = item;
            }
        }
    }

    /**
     * Fills all rows with the specified material
     *
     * @param material material to fill with
     * @param rows rows to fill (1 is first)
     */
    public void filledRows(Material material, int... rows){
        filledRows(StackBuilder.stack(material)
                .displayName(DEFAULT_EMPTY_NAME)
                .toStack(), rows);
    }

    /**
     * Fills all rows with the specified stack
     * @param stack stack to fill with
     * @param rows rows to fill (1 is first)
     
     */
    public void filledRows(ItemStack stack, int... rows){
        for (int row : rows) {
            row = (row - 1) * 9; // Start index of the row
            for (int i = row; i < row + 9; i++) {
                set(i, new MenuItem(stack));
            }
        }
    }

    /**
     * Fills all rows with the specified stack
     * @param stack stack to will with
     * @param startRow row to start (1 is first)
     * @param stopRow row to stop
     
     */
    public void filledRows(ItemStack stack, int startRow, int stopRow){
        for (int i = (startRow-1) * 9; i < stopRow * 9; i++) {
            set(i, new MenuItem(stack));
        }
    }

    public int getSize(){
        return items.length;
    }

    /**
     * Gets all items from this repository
     * @return array of menu items
     */
    public MenuItem[] getItems() {
        return items;
    }

}
