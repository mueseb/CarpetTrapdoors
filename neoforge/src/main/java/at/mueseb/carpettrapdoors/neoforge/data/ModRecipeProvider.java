package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Variant;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

/**
 * <pre>
 *  #      # = carpet
 * #O#     O = trapdoor of the matching type
 *  #
 * </pre>
 */
public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        for (Variant variant : CarpetTrapdoors.VARIANTS) {
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, CarpetTrapdoors.block(variant))
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
            case COPPER -> Ingredient.of(ModItemTagsProvider.COPPER_TRAPDOORS);
        };
    }
}
