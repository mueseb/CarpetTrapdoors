package at.mueseb.carpettrapdoors.forge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        ExistingFileHelper files = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(generator, files));
        ModLanguageProvider.TRANSLATIONS.keySet().forEach(locale ->
                generator.addProvider(event.includeClient(), new ModLanguageProvider(generator, locale)));

        generator.addProvider(event.includeServer(), new ModRecipeProvider(generator));
        generator.addProvider(event.includeServer(), new ModBlockLootProvider(generator));
        generator.addProvider(event.includeServer(), new ModBlockTagsProvider(generator, files));
    }
}
