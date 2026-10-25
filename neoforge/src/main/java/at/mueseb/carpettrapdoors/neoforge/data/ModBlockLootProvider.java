package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootProvider extends BlockLootSubProvider {

    public ModBlockLootProvider(LootTableSubProvider.Context context) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), context);
    }

    @Override
    protected void generate() {
    }

    // The default run() demands a loot table for every block in the game, so only write our own here.
    @Override
    public void run() {
        for (CarpetTrapdoors.Variant variant : CarpetTrapdoors.VARIANTS) {
            Block block = CarpetTrapdoors.block(variant);
            output.accept(block.getLootTable().orElseThrow(), createSingleItemTable(block));
        }
    }
}
