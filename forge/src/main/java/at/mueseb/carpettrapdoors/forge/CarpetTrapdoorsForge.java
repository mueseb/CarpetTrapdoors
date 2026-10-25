package at.mueseb.carpettrapdoors.forge;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

    public CarpetTrapdoorsForge() {
        CarpetTrapdoors.tab = new CreativeModeTab(CarpetTrapdoors.TAB_ID) {
            @Override
            public ItemStack makeIcon() {
                return CarpetTrapdoors.tabIcon();
            }
        };

        CarpetTrapdoors.registerBlocks((id, supplier) -> {
            RegistryObject<Block> block = BLOCKS.register(id, supplier);
            ITEMS.register(id, () -> CarpetTrapdoors.createBlockItem(id, block.get()));
            return block;
        });

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
    }
}
