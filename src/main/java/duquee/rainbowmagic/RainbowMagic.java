package duquee.rainbowmagic;

import duquee.rainbowmagic.blocks.RMBlockEntities;
import duquee.rainbowmagic.blocks.RMBlocks;
import duquee.rainbowmagic.blocks.RMScreenHandlers;
import duquee.rainbowmagic.items.RMItems;
import duquee.rainbowmagic.recipes.RMRecipes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RainbowMagic implements ModInitializer {

	public static final String MOD_ID = "rainbowmagic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		RMItems.register();
		RMBlocks.register();
		RMBlockEntities.register();
		RMScreenHandlers.register();
		RMRecipes.register();
	}
}
