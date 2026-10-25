package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    /** Every vanilla copper trapdoor, regardless of oxidation or wax, can be used in the copper recipe. */
    public static final TagKey<Item> COPPER_TRAPDOORS = TagKey.create(Registries.ITEM, CarpetTrapdoors.id("copper_trapdoors"));

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, CarpetTrapdoors.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(COPPER_TRAPDOORS).addAll(Items.COPPER_TRAPDOOR.asList().stream().map(item -> item.builtInRegistryHolder().key()));
    }
}
