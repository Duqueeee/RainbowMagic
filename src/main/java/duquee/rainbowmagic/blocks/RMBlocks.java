package duquee.rainbowmagic.blocks;

import duquee.rainbowmagic.RainbowMagic;
import duquee.rainbowmagic.blocks.mortar.MortarBlock;
import duquee.rainbowmagic.creativetabs.RMCreativeTabs;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RMBlocks {

    public static final Block CRYSTAL_CLUSTER = new AmethystClusterBlock(5, 4, FabricBlockSettings.of(Material.AMETHYST)
            .strength(0.5F).luminance(5).sounds(BlockSoundGroup.AMETHYST_CLUSTER));

    public static final Block QUARTZ_CLUSTER = new AmethystClusterBlock(5, 4, FabricBlockSettings.of(Material.AMETHYST)
            .strength(0.5F).sounds(BlockSoundGroup.AMETHYST_CLUSTER));

    public static final Block EXTRACTOR = new Block(FabricBlockSettings.of(Material.METAL)
            .strength(3F).nonOpaque());

    public static final Block MORTAR = new MortarBlock(FabricBlockSettings.of(Material.STONE)
            .strength(1F).nonOpaque());

    public static void register() {
        registerBlock("crystal_cluster", CRYSTAL_CLUSTER);
        registerBlock("quartz_cluster", QUARTZ_CLUSTER);
        registerBlock("extractor", EXTRACTOR);
        registerBlock("mortar", MORTAR);
    }

    public static void setRenderTypes() {
        BlockRenderLayerMap.INSTANCE.putBlock(CRYSTAL_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(QUARTZ_CLUSTER, RenderLayer.getCutout());
    }

    private static void registerBlock(String id, Block block) {
        registerBlock(id, block, RMCreativeTabs.MISC);
    }

    private static void registerBlock(String id, Block block, ItemGroup tab) {
        Registry.register(Registry.BLOCK, new Identifier(RainbowMagic.MOD_ID, id), block);
        Registry.register(Registry.ITEM, new Identifier(RainbowMagic.MOD_ID, id),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }

}
