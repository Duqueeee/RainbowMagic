package duquee.rainbowmagic.blocks;

import duquee.rainbowmagic.RainbowMagic;
import duquee.rainbowmagic.blocks.mortar.MortarBlockEntity;
import duquee.rainbowmagic.blocks.mortar.MortarBlockEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RMBlockEntities {

    public static final BlockEntityType<MortarBlockEntity> MORTAR = FabricBlockEntityTypeBuilder.create(MortarBlockEntity::new, RMBlocks.MORTAR).build();

    public static void register() {
        registerBlockEntityType("mortar_entity", MORTAR);
    }

    private static void registerBlockEntityType(String id, BlockEntityType<?> blockEntityType) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RainbowMagic.MOD_ID, id), blockEntityType);
    }


    public class Renderers {
        public static void register() {
            BlockEntityRendererRegistry.register(MORTAR, MortarBlockEntityRenderer::new);
        }
    }

}
