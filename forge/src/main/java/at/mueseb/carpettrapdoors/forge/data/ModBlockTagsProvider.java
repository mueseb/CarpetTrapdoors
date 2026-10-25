package at.mueseb.carpettrapdoors.forge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Variant;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper files) {
        super(output, lookup, CarpetTrapdoors.MOD_ID, files);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Lets other mods (and vanilla mechanics) recognise them as trapdoors.
        for (Variant variant : CarpetTrapdoors.VARIANTS) {
            tag(variant.type() == CarpetTrapdoors.Type.WOODEN ? BlockTags.WOODEN_TRAPDOORS : BlockTags.TRAPDOORS)
                    .add(CarpetTrapdoors.block(variant));
            // Step sounds come from the block below the player, which is the floor under a one pixel thin trapdoor.
            // Like vanilla carpets, this plays the carpet step sound over a muffled one of the floor.
            tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(CarpetTrapdoors.block(variant));
        }
    }
}
