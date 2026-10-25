package at.mueseb.carpettrapdoors.forge;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(CarpetTrapdoors.MOD_ID)
public class CarpetTrapdoorsForge {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CarpetTrapdoors.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CarpetTrapdoors.MOD_ID);
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CarpetTrapdoors.MOD_ID);

    public CarpetTrapdoorsForge() {
        CarpetTrapdoors.registerBlocks((id, supplier) -> {
            RegistryObject<Block> block = BLOCKS.register(id, supplier);
            ITEMS.register(id, () -> CarpetTrapdoors.createBlockItem(id, block.get()));
            return block;
        });
        TABS.register(CarpetTrapdoors.TAB_ID, () -> CarpetTrapdoors.configureTab(CreativeModeTab.builder()).build());

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TABS.register(modBus);
    }
}
