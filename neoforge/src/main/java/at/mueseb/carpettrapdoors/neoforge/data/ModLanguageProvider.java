package at.mueseb.carpettrapdoors.neoforge.data;

import at.mueseb.carpettrapdoors.CarpetTrapdoors;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Type;
import at.mueseb.carpettrapdoors.CarpetTrapdoors.Variant;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Block names are patterns around vanilla's translated carpet name ("%s Iron Trapdoor"), so each language only
 * needs one entry per trapdoor type and every colour is translated automatically.
 */
public class ModLanguageProvider extends LanguageProvider {

    /** Locale -> wooden, iron and copper name pattern, creative tab title. */
    public static final Map<String, List<String>> TRANSLATIONS = Map.of(
            "en_us", List.of("%s Trapdoor", "%s Iron Trapdoor", "%s Copper Trapdoor", "Carpet Trapdoors"),
            "de_de", List.of("%s Falltür", "%s Eisenfalltür", "%s Kupferfalltür", "Teppichfalltüren"),
            "fr_fr", List.of("%s (trappe)", "%s (trappe en fer)", "%s (trappe en cuivre)", "Trappes-tapis"),
            "es_es", List.of("%s (trampilla)", "%s (trampilla de hierro)", "%s (trampilla de cobre)", "Trampillas de alfombra"),
            "pt_br", List.of("%s (Alçapão)", "%s (Alçapão de Ferro)", "%s (Alçapão de Cobre)", "Alçapões de Tapete"),
            "ru_ru", List.of("%s (люк)", "%s (железный люк)", "%s (медный люк)", "Люки-ковры"),
            "pl_pl", List.of("%s (klapa)", "%s (żelazna klapa)", "%s (miedziana klapa)", "Klapy-dywany"),
            "zh_cn", List.of("%s活板门", "%s铁活板门", "%s铜活板门", "地毯活板门"),
            "ja_jp", List.of("%sのトラップドア", "%sの鉄のトラップドア", "%sの銅のトラップドア", "カーペットトラップドア"));

    private final String locale;

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, CarpetTrapdoors.MOD_ID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        List<String> names = TRANSLATIONS.get(locale);
        for (Type type : Type.values()) {
            add(type.translationKey(), names.get(type.ordinal()));
        }
        add("itemGroup." + CarpetTrapdoors.MOD_ID, names.get(3));

        if (locale.equals("en_us")) {
            // Full names under the blocks' own keys, for mods that read the translation key instead of the name.
            for (Variant variant : CarpetTrapdoors.VARIANTS) {
                // "light_blue_carpet_iron_trapdoor" -> "Light Blue Carpet Iron Trapdoor"
                String name = Arrays.stream(variant.id().split("_"))
                        .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                        .collect(Collectors.joining(" "));
                add(CarpetTrapdoors.block(variant), name);
            }
        }
    }
}
