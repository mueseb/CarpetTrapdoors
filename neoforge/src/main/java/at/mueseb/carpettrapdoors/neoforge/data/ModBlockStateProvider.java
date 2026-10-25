package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoorBlock;
import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Carpet;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Variant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Block states, block models and item models. Every colour shares the hand-written templates in
 * {@code common/src/main/resources/assets/carpettrapdoors/models/block}; wooden, iron and copper variants
 * of one colour use the same models.
 */
public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper files) {
        super(output, CarpetTrapdoors.MOD_ID, files);
    }

    @Override
    protected void registerStatesAndModels() {
        for (Variant variant : CarpetTrapdoors.VARIANTS) {
            Carpet carpet = variant.carpet();
            String base = carpet.id() + "_carpet_trapdoor";
            ModelFile bottom = coloredModel(base + "_bottom", "carpet_trapdoor_bottom_template", carpet);
            ModelFile top = coloredModel(base + "_top", "carpet_trapdoor_top_template", carpet);
            ModelFile open = coloredModel(base + "_open", "carpet_trapdoor_open_template", carpet);

            // Not orientable: closed trapdoors keep the same texture rotation as wool, whichever way they were placed.
            trapdoorBlock((CarpetTrapdoorBlock) CarpetTrapdoors.block(variant), bottom, top, open, false);
            itemModels().withExistingParent(variant.id(), BuiltInRegistries.BLOCK.getKey(carpet.carpet).withPrefix("block/"));
        }
    }

    private ModelFile coloredModel(String name, String template, Carpet carpet) {
        ResourceLocation texture = BuiltInRegistries.BLOCK.getKey(carpet.textureBlock).withPrefix("block/");
        return models().withExistingParent(name, modLoc("block/" + template))
                .texture("0", texture)
                .texture("particle", texture);
    }
}
