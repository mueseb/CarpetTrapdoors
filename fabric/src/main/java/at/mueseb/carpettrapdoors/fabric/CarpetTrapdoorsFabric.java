package at.mueseb.carpettrapdoors.fabric;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;

public class CarpetTrapdoorsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CarpetTrapdoors.tab = FabricItemGroupBuilder.build(CarpetTrapdoors.id(CarpetTrapdoors.TAB_ID), CarpetTrapdoors::tabIcon);

        CarpetTrapdoors.registerBlocks((id, supplier) -> {
            Block block = Registry.register(Registry.BLOCK, CarpetTrapdoors.id(id), supplier.get());
            Registry.register(Registry.ITEM, CarpetTrapdoors.id(id), CarpetTrapdoors.createBlockItem(id, block));
            return () -> block;
        });
    }
}
