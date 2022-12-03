package duquee.rainbowmagic;

import duquee.rainbowmagic.blocks.RMBlockEntities;
import duquee.rainbowmagic.blocks.RMBlocks;
import duquee.rainbowmagic.blocks.RMScreenHandlers;
import duquee.rainbowmagic.blocks.mortar.MortarScreen;
import duquee.rainbowmagic.packets.RMPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class RainbowMagicClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RMBlocks.setRenderTypes();
        HandledScreens.register(RMScreenHandlers.MORTAR, MortarScreen::new);
        RMBlockEntities.Renderers.register();
        RMPackets.registerS2CPackets();
    }

}
