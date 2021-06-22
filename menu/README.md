# menu

Library for creating menus / GUIs with the help of Minecraft inventories. 

### Menu types
Menu - full control over everything rendered in the inventory

Paginated Menu - menu that is paginated and will use space for page indicator and next/prev buttons

Paginated Object Menu - same as above but is bound to a specific object type

Examples of menu usage can be seen in the **examples** directory

### Examples
#### Simple menu with animation
```java
public class ExampleMenu extends Menu {

    public ExampleMenu(String title, Player player) {
        super(title, 2, player, 20, false);
    }

    @Override
    public void buildMenu() {
        set(1, new MenuItem(new ItemStack(Material.DIAMOND),
                click -> click.getPlayer().sendMessage("ok")));
    }

    @Override
    public void onTick() {
        update(ThreadLocalRandom.current().nextInt(getItems().length), new MenuItem(new ItemStack(Material.EMERALD),
                click -> click.getPlayer().sendMessage(String.valueOf(ThreadLocalRandom.current().nextInt(100)))));
    }

}
```

#### Paginated Menu
Note that menu items need to be passed to the constructor. 
```java
public class ExamplePaginatedMenu extends PaginatedMenu {

    public ExamplePaginatedMenu(Player player, MenuItem[] items) {
        super("Paginated Example", 5, player, 0, false, items, 9, 4*9);
    }

    @Override
    public void buildUi() {
        filledRows(Material.GRAY_STAINED_GLASS_PANE, 1, 5);
    }

}
```

#### Paginated Object Menu

```java
public class ExamplePaginatedObjectMenu extends PaginatedObjectMenu<ExamplePaginatedObjectMenu.ExampleObject> {

    public ExamplePaginatedObjectMenu(Player player, List<ExampleObject> list) {
        super("Paginated Object Menu", 4, player, 0, false, list, 0, 4 * 9);
    }

    @Override
    public void buildUi() { }

    @Override
    public MenuItem buildItem(ExampleObject exampleObject, int index) {
        return new MenuItem(StackBuilder.stack(exampleObject.getMaterial())
                .displayName("&a" + exampleObject.getExample()).build(),
                menuItemClick -> exampleObject.example(menuItemClick.getPlayer()));
    }
    
    public static class ExampleObject {

        private final Material material;
        private final String example;

        public ExampleObject(Material material, String example) {
            this.material = material;
            this.example = example;
        }

        public void example(Player player) {
            player.sendMessage("cool " + example + "!");
        }

        public String getExample() {
            return example;
        }

        public Material getMaterial() {
            return material;
        }

    }

}
```

### To Do
- Test animated menus properly
- Test performance