package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Carpet;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Variant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

/**
 * Block states, block models and item models. Every colour shares the hand-written templates in
 * {@code common/src/main/resources/assets/carpettrapdoors/models/block}; wooden, iron and copper variants
 * of one colour use the same models.
 */
public class ModModelProvider extends ModelProvider {

    private static final TextureSlot TEXTURE = TextureSlot.create("0");
    private static final ModelTemplate BOTTOM = template("carpet_trapdoor_bottom_template");
    private static final ModelTemplate TOP = template("carpet_trapdoor_top_template");
    private static final ModelTemplate OPEN = template("carpet_trapdoor_open_template");

    public ModModelProvider(PackOutput output) {
        super(output, CarpetTrapdoors.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (Carpet carpet : Carpet.values()) {
            String base = "block/" + carpet.id() + "_carpet_trapdoor";
            Material texture = TextureMapping.getBlockTexture(carpet.textureBlock);
            TextureMapping textures = new TextureMapping().put(TEXTURE, texture).put(TextureSlot.PARTICLE, texture);

            MultiVariant bottom = BlockModelGenerators.plainVariant(BOTTOM.create(CarpetTrapdoors.id(base + "_bottom"), textures, blockModels.modelOutput));
            MultiVariant top = BlockModelGenerators.plainVariant(TOP.create(CarpetTrapdoors.id(base + "_top"), textures, blockModels.modelOutput));
            MultiVariant open = BlockModelGenerators.plainVariant(OPEN.create(CarpetTrapdoors.id(base + "_open"), textures, blockModels.modelOutput));

            for (CarpetTrapdoors.Type type : CarpetTrapdoors.Type.values()) {
                Block block = CarpetTrapdoors.block(new Variant(carpet, type));
                // Not orientable: closed trapdoors keep the same texture rotation as wool, whichever way they were placed.
                blockModels.blockStateOutput.accept(BlockModelGenerators.createTrapdoor(block, top, bottom, open));
                blockModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(carpet.carpet)));
            }
        }
    }

    private static ModelTemplate template(String name) {
        return new ModelTemplate(Optional.of(CarpetTrapdoors.id("block/" + name)), Optional.empty(), TEXTURE, TextureSlot.PARTICLE);
    }
}
