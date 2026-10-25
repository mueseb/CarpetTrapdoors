package at.mueseb.carpettrapdoors;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Loader-independent definition of every carpet trapdoor. Each loader only has to provide a {@link Registrar}.
 */
public final class CarpetTrapdoors {

    public static final String MOD_ID = "carpettrapdoors";

    public enum Carpet {
        MOSS(Blocks.MOSS_CARPET, Blocks.MOSS_BLOCK),
        WHITE(Blocks.WHITE_CARPET, Blocks.WHITE_WOOL),
        ORANGE(Blocks.ORANGE_CARPET, Blocks.ORANGE_WOOL),
        MAGENTA(Blocks.MAGENTA_CARPET, Blocks.MAGENTA_WOOL),
        LIGHT_BLUE(Blocks.LIGHT_BLUE_CARPET, Blocks.LIGHT_BLUE_WOOL),
        YELLOW(Blocks.YELLOW_CARPET, Blocks.YELLOW_WOOL),
        LIME(Blocks.LIME_CARPET, Blocks.LIME_WOOL),
        PINK(Blocks.PINK_CARPET, Blocks.PINK_WOOL),
        GRAY(Blocks.GRAY_CARPET, Blocks.GRAY_WOOL),
        LIGHT_GRAY(Blocks.LIGHT_GRAY_CARPET, Blocks.LIGHT_GRAY_WOOL),
        CYAN(Blocks.CYAN_CARPET, Blocks.CYAN_WOOL),
        PURPLE(Blocks.PURPLE_CARPET, Blocks.PURPLE_WOOL),
        BLUE(Blocks.BLUE_CARPET, Blocks.BLUE_WOOL),
        BROWN(Blocks.BROWN_CARPET, Blocks.BROWN_WOOL),
        GREEN(Blocks.GREEN_CARPET, Blocks.GREEN_WOOL),
        RED(Blocks.RED_CARPET, Blocks.RED_WOOL),
        BLACK(Blocks.BLACK_CARPET, Blocks.BLACK_WOOL);

        /** The vanilla carpet used in the recipe and as item model. */
        public final Block carpet;
        /** The block whose texture the carpet uses. */
        public final Block textureBlock;

        Carpet(Block carpet, Block textureBlock) {
            this.carpet = carpet;
            this.textureBlock = textureBlock;
        }

        public String id() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    public enum Type {
        // Before 1.19.4 the material decides how a trapdoor behaves: metal ones only open with redstone.
        // The sound is the one of the matching vanilla trapdoor, used for building it.
        WOODEN("carpet_trapdoor", Material.WOOL, SoundType.WOOD),
        IRON("carpet_iron_trapdoor", Material.METAL, SoundType.METAL);

        public final String suffix;
        public final Material material;
        public final SoundType sound;

        Type(String suffix, Material material, SoundType sound) {
            this.suffix = suffix;
            this.material = material;
            this.sound = sound;
        }

        /** Translation key of the name pattern, e.g. "%s Iron Trapdoor" where %s is the vanilla carpet name. */
        public String translationKey() {
            return "block." + MOD_ID + "." + suffix;
        }
    }

    public record Variant(Carpet carpet, Type type) {
        public String id() {
            return carpet.id() + "_" + type.suffix;
        }

        /** "Red Carpet" + "%s Iron Trapdoor" = "Red Carpet Iron Trapdoor", in every language vanilla supports. */
        public Component name() {
            return Component.translatable(type.translationKey(), carpet.carpet.getName());
        }

        public CarpetTrapdoorBlock createBlock() {
            // Building sounds like the trapdoor type (wood, iron), walking on it sounds like the carpet it hides under.
            SoundType carpetSound = carpet.carpet.defaultBlockState().getSoundType();
            SoundType sound = new SoundType(carpetSound.getVolume(), carpetSound.getPitch(), type.sound.getBreakSound(),
                    carpetSound.getStepSound(), type.sound.getPlaceSound(), type.sound.getHitSound(), carpetSound.getFallSound());
            BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(type.material, carpet.carpet.defaultMaterialColor())
                    .sound(sound)
                    .strength(0.1F)
                    .noOcclusion();
            return new CarpetTrapdoorBlock(properties, name());
        }
    }

    @FunctionalInterface
    public interface Registrar {
        /** Registers the block and its block item under {@code id} and returns a supplier of the registered block. */
        Supplier<? extends Block> register(String id, Supplier<? extends Block> block);
    }

    public static final List<Variant> VARIANTS;

    static {
        List<Variant> variants = new ArrayList<>();
        for (Type type : Type.values()) {
            for (Carpet carpet : Carpet.values()) {
                variants.add(new Variant(carpet, type));
            }
        }
        VARIANTS = Collections.unmodifiableList(variants);
    }

    private static final Map<Variant, Supplier<? extends Block>> BLOCKS = new LinkedHashMap<>();

    private CarpetTrapdoors() {
    }

    public static void registerBlocks(Registrar registrar) {
        for (Variant variant : VARIANTS) {
            BLOCKS.put(variant, registrar.register(variant.id(), variant::createBlock));
        }
    }

    public static Block block(Variant variant) {
        return BLOCKS.get(variant).get();
    }

    /** Creates the item for a carpet trapdoor block that was registered under {@code id}. */
    public static BlockItem createBlockItem(String id, Block block) {
        return new BlockItem(block, new Item.Properties().tab(tab)) {
            @Override
            public Component getName(ItemStack stack) {
                return block.getName();
            }
        };
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    // Creative tab

    public static final String TAB_ID = MOD_ID;

    /** Created by the loader before any item, because items name their tab in their properties before 1.19.3. */
    public static CreativeModeTab tab;

    public static ItemStack tabIcon() {
        return new ItemStack(block(new Variant(Carpet.RED, Type.WOODEN)));
    }
}
