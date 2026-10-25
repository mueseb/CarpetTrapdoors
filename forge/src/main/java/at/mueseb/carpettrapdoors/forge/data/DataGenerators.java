package at.mueseb.carpettrapdoors.forge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Generates the resources shared by all loaders. Run with {@code ./gradlew :forge:runData}.
 */
@Mod.EventBusSubscriber(modid = CarpetTrapdoors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {

    private DataGenerators() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        ExistingFileHelper files = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, files));
        ModLanguageProvider.TRANSLATIONS.keySet().forEach(locale ->
                generator.addProvider(event.includeClient(), new ModLanguageProvider(output, locale)));

        generator.addProvider(event.includeServer(), new ModRecipeProvider(output));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK))));
        generator.addProvider(event.includeServer(), new ModBlockTagsProvider(output, lookup, files));
    }
}
