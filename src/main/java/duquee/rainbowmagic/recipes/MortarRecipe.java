package duquee.rainbowmagic.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class MortarRecipe implements Recipe<SimpleInventory> {

    private final Identifier id;
    private final Ingredient ingredient;
    private final ItemStack output;

    public MortarRecipe(Identifier id, Ingredient ingredient, ItemStack output) {
        this.id = id;
        this.output = output;
        this.ingredient = ingredient;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient()) return false;
        return ingredient.test(inventory.getStack(1));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public static class Type implements RecipeType<MortarRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "mortar";
    }

    public static class Serializer implements RecipeSerializer<MortarRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "mortar";

        @Override
        public MortarRecipe read(Identifier id, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"));
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
            return new MortarRecipe(id, ingredient, output);
        }

        @Override
        public MortarRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient ingredient = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            return new MortarRecipe(id, ingredient, output);
        }

        @Override
        public void write(PacketByteBuf buf, MortarRecipe recipe) {
            recipe.getIngredient().write(buf);
            buf.writeItemStack(recipe.getOutput());
        }
    }

}
