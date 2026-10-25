package at.mueseb.carpettrapdoors.neoforge;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CarpetTrapdoors.MOD_ID)
public class CarpetTrapdoorsNeoForge {

    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CarpetTrapdoors.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CarpetTrapdoors.MOD_ID);
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CarpetTrapdoors.MOD_ID);

    public CarpetTrapdoorsNeoForge(IEventBus modBus) {
        CarpetTrapdoors.registerBlocks((id, supplier) -> {
            DeferredBlock<?> block = BLOCKS.register(id, supplier);
            ITEMS.register(id, () -> CarpetTrapdoors.createBlockItem(id, block.get()));
            return block;
        });
        TABS.register(CarpetTrapdoors.TAB_ID, () -> CarpetTrapdoors.configureTab(CreativeModeTab.builder()).build());

        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TABS.register(modBus);
    }
}
