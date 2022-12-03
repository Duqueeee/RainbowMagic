package duquee.rainbowmagic.blocks.mortar;

import duquee.rainbowmagic.blocks.RMScreenHandlers;
import duquee.rainbowmagic.items.PestleItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class MortarScreenHandler extends ScreenHandler {

    private static final int INVENTORY_SIZE = 3;
    private final Inventory inventory;

    PropertyDelegate propertyDelegate;

    public MortarScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(INVENTORY_SIZE), new ArrayPropertyDelegate(1));
    }

    public MortarScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(RMScreenHandlers.MORTAR, syncId);

        checkSize(inventory, INVENTORY_SIZE);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        inventory.onOpen(playerInventory.player);

        this.addProperties(propertyDelegate);

        addMortarSlots();
        addPlayerSlots(playerInventory);

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {

        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {

            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot < INVENTORY_SIZE) {

                if (!this.insertItem(originalStack, INVENTORY_SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }

            } else if (!this.insertItem(originalStack, 0, INVENTORY_SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    private void addMortarSlots() {

        this.addSlot(new Slot(inventory, 0, 80, 49) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof PestleItem;
            }
        });

        this.addSlot(new Slot(inventory, 1, 53, 22));
        this.addSlot(new Slot(inventory, 2, 107, 22) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

    }

    private void addPlayerSlots(PlayerInventory playerInventory) {

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 76 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 134));
        }

    }

    public int getScaledProgress() {
        final int progress = propertyDelegate.get(0);
        return MortarBlockEntity.MAX_PROGRESS != 0 &&  progress != 0 ? progress * 26 / MortarBlockEntity.MAX_PROGRESS : 0;
    }

}