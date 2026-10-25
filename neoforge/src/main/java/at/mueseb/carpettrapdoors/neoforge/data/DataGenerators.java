package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

/**
 * Generates the resources shared by all loaders. Run with {@code ./gradlew :neoforge:runData}.
 */
@EventBusSubscriber(modid = CarpetTrapdoors.MOD_ID)
public final class DataGenerators {

    private DataGenerators() {
    }

    // The client data run generates both assets and data.
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
        ModLanguageProvider.TRANSLATIONS.keySet().forEach(locale -> event.createProvider(output -> new ModLanguageProvider(output, locale)));

        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider((output, lookup) -> new LootTableProvider(output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK)), lookup));
        event.createProvider(ModBlockTagsProvider::new);
        event.createProvider(ModItemTagsProvider::new);
    }
}
