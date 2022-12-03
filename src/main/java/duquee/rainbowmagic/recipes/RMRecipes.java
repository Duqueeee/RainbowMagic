package duquee.rainbowmagic.recipes;

import duquee.rainbowmagic.RainbowMagic;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RMRecipes {

    public static void register() {
        registerRecipe(MortarRecipe.Type.INSTANCE, MortarRecipe.Type.ID,
                MortarRecipe.Serializer.INSTANCE, MortarRecipe.Serializer.ID);
    }

    private static <T extends Recipe<?>> void registerRecipe(RecipeType<T> type, String typeId,
                                                             RecipeSerializer<T> serializer, String serializerId) {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(RainbowMagic.MOD_ID, serializerId), serializer);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(RainbowMagic.MOD_ID, typeId), type);
    }

}
