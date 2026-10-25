package at.mueseb.carpettrapdoors.forge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Variant;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generator, ExistingFileHelper files) {
        super(generator, CarpetTrapdoors.MOD_ID, files);
    }

    @Override
    protected void addTags() {
        // Lets other mods (and vanilla mechanics) recognise them as trapdoors.
        for (Variant variant : CarpetTrapdoors.VARIANTS) {
            tag(variant.type() == CarpetTrapdoors.Type.WOODEN ? BlockTags.WOODEN_TRAPDOORS : BlockTags.TRAPDOORS)
                    .add(CarpetTrapdoors.block(variant));
        }
    }
}
