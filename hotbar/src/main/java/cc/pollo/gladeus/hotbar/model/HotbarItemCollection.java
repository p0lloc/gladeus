package cc.pollo.gladeus.hotbar.model;

/**
 * Responsible for constructing the hotbar with HotbarItems
 */
public class HotbarItemCollection {

    private final HotbarItem[] items;

    private HotbarItemCollection(){
        items = new HotbarItem[9];
    }

    public HotbarItemCollection set(int slot, HotbarItem item){
        items[slot] = item;
        return this;
    }

    public static HotbarItemCollection create(){
        return new HotbarItemCollection();
    }

    public HotbarItem[] getItems() {
        return items;
    }

}