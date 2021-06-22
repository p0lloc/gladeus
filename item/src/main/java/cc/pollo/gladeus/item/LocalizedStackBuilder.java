package cc.pollo.gladeus.item;
import cc.pollo.texty.Texty;
import cc.pollo.texty.TextyRenderer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class LocalizedStackBuilder extends DefaultStackBuilder {

    private final TextyRenderer renderHelper;

    public LocalizedStackBuilder(Texty texty, ItemStack stack) {
        super(stack);
        this.renderHelper = texty.getTextyRenderer();
    }

    public LocalizedStackBuilder(Texty texty, Material material, int amount){
        super(material, amount);
        this.renderHelper = texty.getTextyRenderer();
    }

    @Override
    public ItemStack toStack(Player player) {
        ItemStack stack = super.toStack();
        return renderHelper.render(stack, ItemStack.class, player);
    }

}