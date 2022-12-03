package duquee.rainbowmagic.items;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.util.math.random.Random;

public class PestleItem extends ToolItem implements Vanishable {

    public PestleItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public boolean use(ItemStack stack) {
        boolean shouldBreak = stack.damage(1, Random.create(), null);
        if (shouldBreak) stack.decrement(1);
        return shouldBreak;
    }

}
