package duquee.rainbowmagic.items;

import duquee.rainbowmagic.RainbowMagic;
import duquee.rainbowmagic.creativetabs.RMCreativeTabs;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RMItems {

    public static final Item CRYSTAL_SHARD = new Item(new FabricItemSettings().group(RMCreativeTabs.MISC));
    public static final Item CRYSTAL_DUST = new Item(new FabricItemSettings().group(RMCreativeTabs.MISC));
    public static final Item WAND = new Item(new FabricItemSettings().group(RMCreativeTabs.MISC));
    public static final Item NETHERITE_PESTLE = new PestleItem(ToolMaterials.NETHERITE, new FabricItemSettings().group(RMCreativeTabs.MISC));
    public static final Item DIAMOND_PESTLE = new PestleItem(ToolMaterials.DIAMOND, new FabricItemSettings().group(RMCreativeTabs.MISC));
    public static final Item IRON_PESTLE = new PestleItem(ToolMaterials.IRON, new FabricItemSettings().group(RMCreativeTabs.MISC));
    public static final Item GOLDEN_PESTLE = new PestleItem(ToolMaterials.GOLD, new FabricItemSettings().group(RMCreativeTabs.MISC));
    public static final Item STONE_PESTLE = new PestleItem(ToolMaterials.STONE, new FabricItemSettings().group(RMCreativeTabs.MISC));
    public static final Item WOODEN_PESTLE = new PestleItem(ToolMaterials.WOOD, new FabricItemSettings().group(RMCreativeTabs.MISC));

    public static void register() {
        registerItem("crystal_shard", CRYSTAL_SHARD);
        registerItem("crystal_dust", CRYSTAL_DUST);
        registerItem("wand", WAND);
        registerItem("netherite_pestle", NETHERITE_PESTLE);
        registerItem("diamond_pestle", DIAMOND_PESTLE);
        registerItem("iron_pestle", IRON_PESTLE);
        registerItem("golden_pestle", GOLDEN_PESTLE);
        registerItem("stone_pestle", STONE_PESTLE);
        registerItem("wooden_pestle", WOODEN_PESTLE);
    }

    private static void registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(RainbowMagic.MOD_ID, id), item);
    }

}
