package duquee.rainbowmagic.blocks.mortar;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class MortarBlockEntityRenderer implements BlockEntityRenderer<MortarBlockEntity> {

    public MortarBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(MortarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if (!entity.hasPestle()) return;

        matrices.push();
        matrices.scale(0.9F, 0.9F, 0.9F);

        double x, y, z; float a;
        switch (entity.getCachedState().get(MortarBlock.FACING)) {
            case NORTH:
                x = 0.4; y = 0.1; z = 0.55; a = 180;
                break;
            case SOUTH:
                x = 0.7; y = 0.1; z = 0.55; a = 0;
                break;
            case EAST:
                x = 0.55; y = 0.1; z = 0.7; a = 90;
                break;
            default:
                x = 0.55; y = 0.1; z = 0.4; a = 90;
        }

        matrices.translate(x, y, z);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(a));

        MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getItems().get(0), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

        matrices.pop();

    }

}
