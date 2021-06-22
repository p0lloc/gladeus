package cc.pollo.gladeus.menu.model.types;

import cc.pollo.gladeus.menu.model.MenuItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class PaginatedObjectMenu<T> extends PaginatedMenu {

    private final List<T> list;

    public PaginatedObjectMenu(Component title, int rows, Player player,
                               List<T> list, int start, int stop){

        this(title, rows, player, false, list, start, stop);
    }

    public PaginatedObjectMenu(Component title, int rows, Player player,
                         boolean modifiable, List<T> list, int start, int stop){

        this(title, rows, player, 0, modifiable, list, start, stop);
    }

    public PaginatedObjectMenu(Component title, int rows, Player player, int tickInterval,
                               boolean modifiable, List<T> list, int start, int stop) {

        super(title, rows, player, tickInterval, modifiable, new MenuItem[list.size()], start, stop);
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public void buildMenu() {
        for (int i = 0; i < list.size(); i++) {
            items[i] = buildItem(list.get(i), i);
        }

        super.buildMenu();
    }

    public abstract MenuItem buildItem(T t, int index);

}