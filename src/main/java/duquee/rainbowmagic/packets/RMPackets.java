package duquee.rainbowmagic.packets;

import duquee.rainbowmagic.RainbowMagic;
import duquee.rainbowmagic.packets.s2c.ItemStackSyncS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class RMPackets {

    public static final Identifier ITEM_SYNC = new Identifier(RainbowMagic.MOD_ID, "item_sync");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }

}
