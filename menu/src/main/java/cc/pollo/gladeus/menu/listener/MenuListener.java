package cc.pollo.gladeus.menu.listener;

import cc.pollo.gladeus.menu.MenuManager;
import cc.pollo.gladeus.menu.model.Menu;
import cc.pollo.gladeus.menu.model.MenuItem;
import cc.pollo.gladeus.menu.model.MenuItemClick;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class MenuListener implements Listener {

    private final MenuManager menuManager;

    public MenuListener(MenuManager menuManager){
        this.menuManager = menuManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

        HumanEntity whoClicked = event.getWhoClicked();
        if(!(whoClicked instanceof Player))
            return;

        Player player = (Player) whoClicked;
        Menu menu = menuManager.get(player);

        if(menu == null)
            return;

        Inventory clickedInventory = event.getClickedInventory();
        if(clickedInventory == null)
            return;

        if(clickedInventory.getType() != InventoryType.CHEST)
            return;

        if(!menu.isModifiable())
            event.setCancelled(true);

        MenuItem item = menu.getItems()[event.getRawSlot()];
        if(item != null) {
            if(!item.isModifiable())
                event.setCancelled(true);

            item.getClickConsumer().accept(new MenuItemClick(menuManager, event));
        }

    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event){
        HumanEntity humanEntity = event.getPlayer();
        if(!(humanEntity instanceof Player))
            return;

        Player player = (Player) humanEntity;
        Menu menu = menuManager.get(player);

        if(menu == null)
            return;

        menu.onOpen(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        HumanEntity entity = event.getPlayer();
        if(!(entity instanceof Player))
            return;

        Player player = (Player) entity;
        Menu menu = menuManager.get(player);
        if(menu != null)
            menu.onClose(event);

        menuManager.clear(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        menuManager.clear(event.getPlayer()); // Just to make sure
    }

}