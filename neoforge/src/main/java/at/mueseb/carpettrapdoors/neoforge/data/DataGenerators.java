package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

/**
 * Generates the resources shared by all loaders. Run with {@code ./gradlew :neoforge:runData}.
 */
@EventBusSubscriber(modid = CarpetTrapdoors.MOD_ID)
public final class DataGenerators {

    private DataGenerators() {
    }

    // Recipes, their recipe-book advancements and loot tables are data pack registry entries since 26.1.
    @SubscribeEvent
    public static void gatherRegistryEntries(GatherDataRegistryEntriesEvent event) {
        event.gatherFor(CarpetTrapdoors.MOD_ID)
                .recipe(ModRecipeProvider::new)
                .lootTable(new LootTableProvider.SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK));
    }

    // The client data run generates both assets and data.
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
        ModLanguageProvider.TRANSLATIONS.keySet().forEach(locale -> event.createProvider(output -> new ModLanguageProvider(output, locale)));
        event.createProvider(ModBlockTagsProvider::new);
        event.createProvider(ModItemTagsProvider::new);
    }
}
