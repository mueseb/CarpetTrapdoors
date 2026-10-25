package at.mueseb.carpettrapdoors.forge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootProvider extends BlockLootSubProvider {

    public ModBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        getKnownBlocks().forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CarpetTrapdoors.VARIANTS.stream().map(CarpetTrapdoors::block).toList();
    }
}
