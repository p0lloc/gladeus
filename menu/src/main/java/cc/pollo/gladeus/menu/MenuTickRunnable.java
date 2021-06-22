package cc.pollo.gladeus.menu;

import cc.pollo.gladeus.menu.model.Menu;

/**
 * Head runnable that invokes animated/ticking menus according to their interval
 */
public class MenuTickRunnable implements Runnable {

    private final MenuManager menuManager;

    public MenuTickRunnable(MenuManager menuManager){
        this.menuManager = menuManager;
    }

    @Override
    public void run() {
        for (Menu openedMenu : menuManager.getOpenedMenus()) {
            int tickInterval = openedMenu.getTickInterval();
            if(tickInterval < 1)
                continue; // This menu is not ticking

            int tick = openedMenu.getTick();

            if(tick == tickInterval){
                openedMenu.onTick();
                openedMenu.setTick(0);
            } else {
                openedMenu.setTick(tick + 1);
            }
        }
    }

}
