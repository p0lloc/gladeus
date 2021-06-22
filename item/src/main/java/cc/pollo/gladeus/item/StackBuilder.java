package cc.pollo.gladeus.item;

import cc.pollo.texty.Texty;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public interface StackBuilder {

    /**
     * Constructs a new stack builder from an {@link ItemStack}
     * @param stack stack to create from
     * @return new this builder
     */
    static StackBuilder stack(ItemStack stack){
        return new DefaultStackBuilder(stack);
    }

    static StackBuilder stack(Material material){
        return stack(material, 1);
    }

    static StackBuilder stack(Material material, int amount){
        return new DefaultStackBuilder(material, amount);
    }

    static StackBuilder localized(Texty texty, ItemStack stack){
        return new LocalizedStackBuilder(texty, stack);
    }

    /* Localized */

    static StackBuilder localized(Texty texty, Material material){
        return new LocalizedStackBuilder(texty, material, 1);
    }

    static StackBuilder localized(Texty texty, Material material, int amount){
        return new LocalizedStackBuilder(texty, material, amount);
    }

    /**
     * Sets the display name of this stack
     * @param displayName display name
     * @return this builder
     */
    StackBuilder displayName(Component displayName);

    /**
     * Sets the display name of this stack
     * @param displayName display name
     * @param keepItalic whether to keep the default italicized name
     * @return this builder
     */
    StackBuilder displayName(Component displayName, boolean keepItalic);

    StackBuilder displayName(Supplier<Component> dynamicDisplayName);

    default StackBuilder lore(Component... lore){
        return lore(Arrays.asList(lore));
    }

    default StackBuilder lore(List<Component> lore){
        return lore(lore, false);
    }

    StackBuilder lore(List<Component> lore, boolean keepItalic);

    StackBuilder lore(Supplier<List<Component>> dynamicLore);

    StackBuilder unbreakable(boolean unbreakable);

    StackBuilder enchant(Enchantment enchantment, int level);

    StackBuilder enchantUnsafe(Enchantment enchantment, int level);

    @Deprecated
    StackBuilder skullOwner(String skullOwner);

    StackBuilder skullOwner(UUID ownerId);

    StackBuilder skullOwner(OfflinePlayer player);

    ItemStack toStack();

    ItemStack toStack(Player player);

    void applyDynamicText(MetaType type, Component[] component);

    enum MetaType {
        DISPLAY_NAME,
        LORE
    }

}