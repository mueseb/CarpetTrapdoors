package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Variant;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
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

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        for (Variant variant : CarpetTrapdoors.VARIANTS) {
            shaped(RecipeCategory.REDSTONE, CarpetTrapdoors.block(variant))
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

    private Ingredient trapdoor(CarpetTrapdoors.Type type) {
        return switch (type) {
            case WOODEN -> tag(ItemTags.WOODEN_TRAPDOORS);
            case IRON -> Ingredient.of(Items.IRON_TRAPDOOR);
            case COPPER -> tag(ModItemTagsProvider.COPPER_TRAPDOORS);
        };
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Carpet Trapdoors recipes";
        }
    }
}
