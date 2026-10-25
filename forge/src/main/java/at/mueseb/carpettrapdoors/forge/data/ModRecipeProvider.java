package at.mueseb.carpettrapdoors.forge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Variant;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

/**
 * <pre>
 *  #      # = carpet
 * #O#     O = trapdoor of the matching type
 *  #
 * </pre>
 */
public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> output) {
        for (Variant variant : CarpetTrapdoors.VARIANTS) {
            ShapedRecipeBuilder.shaped(CarpetTrapdoors.block(variant))
                    .define('#', variant.carpet().carpet)
                    .define('O', trapdoor(variant.type()))
                    .pattern(" # ")
                    .pattern("#O#")
                    .pattern(" # ")
                    .group(variant.type().suffix)
                    .unlockedBy("has_carpet", has(variant.carpet().carpet))
                    .save(output);
        }
    }

    private static Ingredient trapdoor(CarpetTrapdoors.Type type) {
        return switch (type) {
            case WOODEN -> Ingredient.of(ItemTags.WOODEN_TRAPDOORS);
            case IRON -> Ingredient.of(Items.IRON_TRAPDOOR);
        };
    }
}
