# item
Includes a simple item builder to help you create items with a chained approach.

### Example
```java

ItemStack stack = StackBuilder.stack(Material.DIAMOND)
        .displayName("Super Diamond")
        .enchantUnsafe(Enchantment.SHARPNESS, 1)
        .toStack();

```