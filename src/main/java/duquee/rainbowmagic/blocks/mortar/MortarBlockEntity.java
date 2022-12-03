package duquee.rainbowmagic.blocks.mortar;

import duquee.rainbowmagic.RainbowMagic;
import duquee.rainbowmagic.blocks.RMBlockEntities;
import duquee.rainbowmagic.items.PestleItem;
import duquee.rainbowmagic.packets.RMPackets;
import duquee.rainbowmagic.recipes.MortarRecipe;
import duquee.rainbowmagic.utils.BlockEntityInventory;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MortarBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, BlockEntityInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private int progress = 0;
    public static final int MAX_PROGRESS = 10;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return progress;
        }
        @Override
        public void set(int index, int value) {
            progress = value;
        }
        @Override
        public int size() {
            return 1;
        }
    };

    public MortarBlockEntity(BlockPos pos, BlockState state) {
        super(RMBlockEntities.MORTAR, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MortarScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        progress = nbt.getInt("progress");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt("progress", progress);
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }

    @Override
    public void markDirty() {

        if(!world.isClient()) {

            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());

            for(int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, RMPackets.ITEM_SYNC, data);
            }
        }

        super.markDirty();
    }

    public void grind() {

        Optional<MortarRecipe> recipe = getRecipe();
        if (!canGrind(recipe)) return;

        getWorld().playSound(null, getPos(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 0.3F, 0.5F);

        ItemStack pestleStack = getStack(0);
        PestleItem pestle = (PestleItem) pestleStack.getItem();

        progress += pestle.getMaterial().getMiningSpeedMultiplier();

        if (progress > MAX_PROGRESS) {
            craft(recipe.get(), progress/MAX_PROGRESS);
            progress %= MAX_PROGRESS;
        }

        boolean shouldBreak = pestle.use(pestleStack);
        if (shouldBreak) getWorld().playSound(null, getPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 0.5F, 1F);

        markDirty();

    }

    private void craft(MortarRecipe recipe, int amount) {
        if (amount > inventory.get(1).getCount()) amount = inventory.get(1).getCount();
        getStack(1).decrement(amount);
        setStack(2, new ItemStack(recipe.getOutput().getItem(),
                inventory.get(2).getCount() + amount*recipe.getOutput().getCount()));
    }

    public Optional<MortarRecipe> getRecipe() {

        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }

        return this.getWorld().getRecipeManager().getFirstMatch(MortarRecipe.Type.INSTANCE, inv, this.getWorld());
    }

    public boolean hasRecipe() {
        return getRecipe().isPresent();
    }

    public boolean hasRecipe(Optional<MortarRecipe> recipe) {
        return recipe.isPresent();
    }

    public boolean hasPestle() {
        return getStack(0).getItem() instanceof PestleItem;
    }

    public boolean canOutput(ItemStack output) {
        return (getStack(2).getItem() == output.getItem()
                && (getStack(2).getMaxCount() > getStack(2).getCount() + output.getCount()))
                || getStack(2).isEmpty();
    }

    public boolean canGrind(Optional<MortarRecipe> recipe) {
        return hasPestle() && hasRecipe(recipe) && canOutput(recipe.get().getOutput());
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (stack.getItem() != getStack(slot).getItem() && progress != 0) {
            progress = 0;
            markDirty();
        }
        BlockEntityInventory.super.setStack(slot, stack);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

}

