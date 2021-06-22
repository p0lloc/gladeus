package cc.pollo.gladeus.menu.model.types;
import cc.pollo.gladeus.menu.model.Menu;
import cc.pollo.gladeus.menu.model.MenuItem;
import cc.pollo.gladeus.item.StackBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class PaginatedMenu extends Menu {

    protected final MenuItem[] items;
    private final int start;
    private final int stop;
    private final int space;

    private PaginatedMenuComponent pageIndicator;
    private PaginatedMenuComponent nextBtn;
    private PaginatedMenuComponent prevBtn;

    private int page = 0;

    public PaginatedMenu(Component title, int rows, Player player,
                         MenuItem[] items, int startSlot, int stopSlot){

        this(title, rows, player, false, items, startSlot, stopSlot);
    }

    public PaginatedMenu(Component title, int rows, Player player,
                         boolean modifiable, MenuItem[] items, int startSlot, int stopSlot){

        this(title, rows, player, 0, modifiable, items, startSlot, stopSlot);
    }

    public PaginatedMenu(Component title, int rows, Player player, int tickInterval,
                         boolean modifiable, MenuItem[] items, int startSlot, int stopSlot) {

        super(title, rows, player, tickInterval, modifiable);
        this.items      = items;
        this.start      = startSlot;
        this.stop       = stopSlot;
        this.space      = stopSlot - startSlot;
    }

    @Override
    public void buildMenu() {
        buildUi();

        pageIndicator = buildPageIndicator();
        prevBtn       = buildPreviousBtn();
        nextBtn       = buildNextBtn();

        updateUi(true);
    }

    /**
     * This method updates the pagination UI, such as next/prev button
     */
    public void updateUi(boolean initial){

        for (int i = start; i < stop; i++) { // Fill all the items
            MenuItem item;

            int collectionIdx = (i - start) + getCursor();
            if(collectionIdx >= items.length){
                item = MenuItem.empty();
            } else {
                item = items[collectionIdx];
                item.setInserted(false);
            }

            set(i, item);
        }

        updateComponent(pageIndicator);

        // If we aren't on the first page, there should be a prev button
        updateComponent(prevBtn, page > 0);

        // If the space is not enough, we need another page
        updateComponent(nextBtn, space < (items.length - getCursor()));

        if(!initial)
            updateChanged();

    }

    private void updateComponent(PaginatedMenuComponent component){
        updateComponent(component, true);
    }

    private void updateComponent(PaginatedMenuComponent component, boolean condition){
        MenuItem item;
        if(condition){
            item = component.getItem();
            item.setInserted(false);
        } else {
            item = new MenuItem(new ItemStack(Material.AIR));
        }

        set(component.getSlot(), item);
    }

    private int getCursor(){
        return space * page;
    }

    public int getPage() {
        return page;
    }

    public void changePage(int page) {
        this.page = page;
        updateUi(false);
    }

    public abstract void buildUi();

    // These UI methods should be overwritten if you wish to change them

    protected PaginatedMenuComponent buildNextBtn(){
        return new PaginatedMenuComponent((rows-1) * 9 + 5, new MenuItemPageNext(this));
    }

    protected PaginatedMenuComponent buildPreviousBtn(){
        return new PaginatedMenuComponent((rows-1) * 9 + 3, new MenuItemPagePrevious(this));
    }

    protected PaginatedMenuComponent buildPageIndicator(){
        return new PaginatedMenuComponent((rows-1) * 9 + 4, new MenuItemPageIndicator(this));
    }

    private static class MenuItemPageIndicator extends MenuItem {
        public MenuItemPageIndicator(PaginatedMenu menu) {
            // We are keeping this as a StackBuilder so it can be rendered with the correct page during runtime
            super(StackBuilder.stack(Material.DIAMOND)
                    .displayName(() -> Component.text("#" + (menu.getPage() + 1), NamedTextColor.AQUA)), click -> {});
        }
    }

    private static class MenuItemPageNext extends MenuItem {
        public MenuItemPageNext(PaginatedMenu menu) {
            super(StackBuilder.stack(Material.PLAYER_HEAD)
                    .displayName(Component.text("->", NamedTextColor.AQUA))
                    .skullOwner(UUID.fromString("50c8510b-5ea0-4d60-be9a-7d542d6cd156")).toStack(),
                    click -> menu.changePage(menu.getPage() + 1));
        }
    }

    private static class MenuItemPagePrevious extends MenuItem {
        public MenuItemPagePrevious(PaginatedMenu menu) {
            super(StackBuilder.stack(Material.PLAYER_HEAD)
                    .displayName(Component.text("<-", NamedTextColor.AQUA))
                    .skullOwner(UUID.fromString("a68f0b64-8d14-4000-a95f-4b9ba14f8df9")).toStack(),
                    click -> menu.changePage(menu.getPage() - 1));
        }
    }

    public static class PaginatedMenuComponent {
        private final int slot;
        private final MenuItem item;

        public PaginatedMenuComponent(int slot, MenuItem item){
            this.slot = slot;
            this.item = item;
        }

        public int getSlot() {
            return slot;
        }

        public MenuItem getItem() {
            return item;
        }
    }

}