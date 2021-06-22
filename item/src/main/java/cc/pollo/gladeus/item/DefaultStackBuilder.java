package cc.pollo.gladeus.item;

import cc.pollo.gladeus.item.util.ComponentUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class DefaultStackBuilder implements StackBuilder {

    private final ItemStack stack;
    private final ItemMeta meta;

    private final Map<MetaType, Supplier<Component[]>> dynamicText = new HashMap<>();

    public DefaultStackBuilder(ItemStack stack) {
        if(stack == null)
            throw new IllegalArgumentException("stack cannot be null!");

        if(!stack.getType().isItem())
            throw new IllegalArgumentException("stack must be an item!");

        this.stack = stack;
        this.meta  = stack.getItemMeta();
    }

    public DefaultStackBuilder(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    @Override
    public StackBuilder displayName(Component displayName) {
        return displayName(displayName, false);
    }

    @Override
    public StackBuilder displayName(Component displayName, boolean keepItalic) {
        meta.displayName(keepItalic ? displayName : ComponentUtils.removeItalic(displayName));
        return this;
    }

    @Override
    public StackBuilder displayName(Supplier<Component> dynamic) {
        dynamicText.put(MetaType.DISPLAY_NAME, () -> new Component[]{dynamic.get()});
        return this;
    }

    @Override
    public StackBuilder lore(List<Component> lore, boolean keepItalic) {
        if(!keepItalic) {
            lore = lore.stream()
                .map(ComponentUtils::removeItalic)
                .collect(Collectors.toList());
        }

        final List<Component> finalLore = lore;
        meta.lore(finalLore);
        return this;
    }

    @Override
    public StackBuilder lore(Supplier<List<Component>> dynamicLore) {
        dynamicText.put(MetaType.LORE, () -> dynamicLore.get().toArray(new Component[0]));
        return this;
    }

    @Override
    public StackBuilder unbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    @Override
    public StackBuilder enchant(Enchantment enchantment, int level) {
        stack.addEnchantment(enchantment, level);
        return this;
    }

    @Override
    public StackBuilder enchantUnsafe(Enchantment enchantment, int level) {
        stack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    @Override
    @Deprecated
    public StackBuilder skullOwner(String skullOwner) {
        modifySkull(skullMeta -> skullMeta.setOwner(skullOwner));
        return this;
    }

    @Override
    public StackBuilder skullOwner(UUID ownerId) {
        return skullOwner(Bukkit.getOfflinePlayer(ownerId));
    }

    @Override
    public StackBuilder skullOwner(OfflinePlayer player) {
        modifySkull(skullMeta -> skullMeta.setOwningPlayer(player));
        return this;
    }

    @Override
    public void applyDynamicText(MetaType type, Component[] component) {
        switch (type){
            case DISPLAY_NAME:{
                displayName(component[0]);
                break;
            }

            case LORE:{
                lore(component);
                break;
            }
        }
    }

    @Override
    public ItemStack toStack() {
        dynamicText.forEach((key, value) -> applyDynamicText(key, value.get()));
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public ItemStack toStack(Player player) {
        return toStack();
    }

    /**
     * Casting helper for skulls <br>
     * Feeds the consumer with the SkullMeta if the stack has one
     * @param consumer consumer for the meta
     */
    private void modifySkull(Consumer<SkullMeta> consumer){
        if(meta instanceof SkullMeta){
            consumer.accept((SkullMeta) meta);
        }
    }

}