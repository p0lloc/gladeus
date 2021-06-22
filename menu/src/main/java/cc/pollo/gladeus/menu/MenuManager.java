package cc.pollo.gladeus.menu;

import cc.pollo.gladeus.menu.listener.MenuListener;
import cc.pollo.gladeus.menu.model.Menu;
import cc.pollo.gladeus.misc.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages assigning menus to a specific player
 */
public class MenuManager implements Manager {

    private final Map<UUID, Menu> menuMap = new ConcurrentHashMap<>();
    private final Plugin plugin;

    public MenuManager(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void enable() {
        Bukkit.getPluginManager().registerEvents(new MenuListener(this), plugin);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new MenuTickRunnable(this), 1, 1);
    }

    /**
     * Tracks the current menu for the player <br>
     * Does NOT open the menu, Menu#open does both.
     * @param player player to set for
     * @param menu menu to set
     */
    public void set(Player player, Menu menu){
        menuMap.put(player.getUniqueId(), menu);
    }

    /**
     * Gets the active menu for the player
     * @param player player to get menu for
     * @return active menu or null if none
     */
    public Menu get(Player player){
        return menuMap.get(player.getUniqueId());
    }

    /**
     * Clears the active menu for the player
     * @param player player to clear for
     */
    public void clear(Player player){
        menuMap.remove(player.getUniqueId());
    }

    /**
     * Gets all opened menus
     * @return collection of opened menus
     */
    public Collection<Menu> getOpenedMenus(){
        return menuMap.values();
    }

}