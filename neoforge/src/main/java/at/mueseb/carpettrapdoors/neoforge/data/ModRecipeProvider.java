package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Variant;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

/**
 * <pre>
 *  #      # = carpet
 * #O#     O = trapdoor of the matching type
 *  #
 * </pre>
 */
public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        super(recipes, advancements);
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
}
