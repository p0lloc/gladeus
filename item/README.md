# item
Includes a simple item builder to help you create items with a chained approach.

### Example
```java

ItemStack stack = new DefaultItemBuilder(Material.DIAMOND)
        .displayName("Super Diamond")
        .enchantUnsafe(Enchantment.SHARPNESS, 1)
        .build();

```