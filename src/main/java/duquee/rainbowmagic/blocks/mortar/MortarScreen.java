package duquee.rainbowmagic.blocks.mortar;

import com.mojang.blaze3d.systems.RenderSystem;
import duquee.rainbowmagic.RainbowMagic;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MortarScreen extends HandledScreen<MortarScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(RainbowMagic.MOD_ID, "textures/gui/mortar.png");

    public MortarScreen(MortarScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        playerInventoryTitleY = 65;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow(matrices, x, y);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    private void renderProgressArrow(MatrixStack matrices, int x, int y) {
        drawTexture(matrices, x + 76, y + 33, 176, 0, handler.getScaledProgress(), 8);
    }

}

