package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Variant;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, CarpetTrapdoors.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Lets other mods (and vanilla mechanics) recognise them as trapdoors.
        for (Variant variant : CarpetTrapdoors.VARIANTS) {
            tag(variant.type() == CarpetTrapdoors.Type.WOODEN ? BlockTags.WOODEN_TRAPDOORS : BlockTags.TRAPDOORS)
                    .add(CarpetTrapdoors.block(variant));
        }
    }
}
