package duquee.rainbowmagic.blocks;

import duquee.rainbowmagic.RainbowMagic;
import duquee.rainbowmagic.blocks.mortar.MortarScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class RMScreenHandlers {

    public static ScreenHandlerType<MortarScreenHandler> MORTAR = ScreenHandlerRegistry.registerSimple(new Identifier(RainbowMagic.MOD_ID, "mortar"), MortarScreenHandler::new);

    public static void register() {

    }

}
