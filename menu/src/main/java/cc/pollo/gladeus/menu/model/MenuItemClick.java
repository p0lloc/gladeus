package cc.pollo.gladeus.menu.model;

import cc.pollo.gladeus.menu.MenuManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Stores additional information from the click event of a menu item
 */
public class MenuItemClick {

    private final Player player;
    private final MenuManager menuManager;
    private final InventoryClickEvent event;

    public MenuItemClick(MenuManager menuManager, InventoryClickEvent clickEvent){
        this.event       = clickEvent;
        this.menuManager = menuManager;

        HumanEntity whoClicked = clickEvent.getWhoClicked();
        if(whoClicked instanceof Player){
            player = (Player) whoClicked;
        } else {
            player = null;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public InventoryClickEvent getEvent() {
        return event;
    }

}