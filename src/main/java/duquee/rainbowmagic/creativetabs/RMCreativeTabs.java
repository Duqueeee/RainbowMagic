package duquee.rainbowmagic.creativetabs;

import duquee.rainbowmagic.RainbowMagic;
import duquee.rainbowmagic.blocks.RMBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class RMCreativeTabs {

    public static final ItemGroup MISC = FabricItemGroupBuilder.build(new Identifier(RainbowMagic.MOD_ID, "misc"),
            () -> new ItemStack(RMBlocks.CRYSTAL_CLUSTER));

}
