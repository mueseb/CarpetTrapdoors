package at.mueseb.carpettrapdoors.forge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
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

        // The data run always generates everything; there is no client/server split before 1.19.
        generator.addProvider(new ModBlockStateProvider(generator, files));
        ModLanguageProvider.TRANSLATIONS.keySet().forEach(locale -> generator.addProvider(new ModLanguageProvider(generator, locale)));

        generator.addProvider(new ModRecipeProvider(generator));
        generator.addProvider(new ModBlockLootProvider(generator));
        generator.addProvider(new ModBlockTagsProvider(generator, files));
    }
}
