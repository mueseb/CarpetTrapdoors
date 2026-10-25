package at.mueseb.carpettrapdoors.fabric;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public class CarpetTrapdoorsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CarpetTrapdoors.registerBlocks((id, supplier) -> {
            Block block = Registry.register(BuiltInRegistries.BLOCK, CarpetTrapdoors.id(id), supplier.get());
            Registry.register(BuiltInRegistries.ITEM, CarpetTrapdoors.id(id), CarpetTrapdoors.createBlockItem(id, block));
            return () -> block;
        });

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CarpetTrapdoors.id(CarpetTrapdoors.TAB_ID),
                CarpetTrapdoors.configureTab(FabricItemGroup.builder()).build());
    }
}
