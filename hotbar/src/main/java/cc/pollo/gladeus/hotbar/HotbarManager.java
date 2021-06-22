package cc.pollo.gladeus.hotbar;

import cc.pollo.gladeus.hotbar.listener.HotbarListener;
import cc.pollo.gladeus.hotbar.model.Hotbar;
import cc.pollo.gladeus.misc.Manager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Keeps track of which hotbar the player currently has, the implementing <br>
 * plugin still needs to clear when appropriate.
 */
public class HotbarManager implements Manager {

    private final Map<UUID, Hotbar> activeBars = new HashMap<>();
    private final Plugin plugin;

    public HotbarManager(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void enable(){
        plugin.getServer().getPluginManager()
                .registerEvents(new HotbarListener(this), plugin);
    }

    @Override
    public void disable() { }

    /**
     * Sets the current hotbar for the player
     * @param player player to set for
     * @param hotbar hotbar to assign
     */
    public void setHotbar(Player player, Hotbar hotbar){
        activeBars.put(player.getUniqueId(), hotbar);
    }

    /**
     * Gets the current hotbar for the player
     * @param player hotbar to get for
     * @return hotbar or null if not assigned
     */
    @Nullable
    public Hotbar getHotbar(Player player){
        return activeBars.get(player.getUniqueId());
    }

    /**
     * Clears the current hotbar for the player
     * @param player player to clear for
     */
    public void clearHotbar(Player player){
        activeBars.remove(player.getUniqueId());
    }

    /**
     * Gets all active hotbars
     * @return collection of active hotbars
     */
    public Collection<Hotbar> getActiveHotbars(){
        return activeBars.values();
    }

}