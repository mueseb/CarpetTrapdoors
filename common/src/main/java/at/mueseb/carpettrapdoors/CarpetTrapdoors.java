package at.mueseb.carpettrapdoors;

import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

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
        WHITE(Blocks.CARPET.pick(DyeColor.WHITE), Blocks.WOOL.pick(DyeColor.WHITE)),
        ORANGE(Blocks.CARPET.pick(DyeColor.ORANGE), Blocks.WOOL.pick(DyeColor.ORANGE)),
        MAGENTA(Blocks.CARPET.pick(DyeColor.MAGENTA), Blocks.WOOL.pick(DyeColor.MAGENTA)),
        LIGHT_BLUE(Blocks.CARPET.pick(DyeColor.LIGHT_BLUE), Blocks.WOOL.pick(DyeColor.LIGHT_BLUE)),
        YELLOW(Blocks.CARPET.pick(DyeColor.YELLOW), Blocks.WOOL.pick(DyeColor.YELLOW)),
        LIME(Blocks.CARPET.pick(DyeColor.LIME), Blocks.WOOL.pick(DyeColor.LIME)),
        PINK(Blocks.CARPET.pick(DyeColor.PINK), Blocks.WOOL.pick(DyeColor.PINK)),
        GRAY(Blocks.CARPET.pick(DyeColor.GRAY), Blocks.WOOL.pick(DyeColor.GRAY)),
        LIGHT_GRAY(Blocks.CARPET.pick(DyeColor.LIGHT_GRAY), Blocks.WOOL.pick(DyeColor.LIGHT_GRAY)),
        CYAN(Blocks.CARPET.pick(DyeColor.CYAN), Blocks.WOOL.pick(DyeColor.CYAN)),
        PURPLE(Blocks.CARPET.pick(DyeColor.PURPLE), Blocks.WOOL.pick(DyeColor.PURPLE)),
        BLUE(Blocks.CARPET.pick(DyeColor.BLUE), Blocks.WOOL.pick(DyeColor.BLUE)),
        BROWN(Blocks.CARPET.pick(DyeColor.BROWN), Blocks.WOOL.pick(DyeColor.BROWN)),
        GREEN(Blocks.CARPET.pick(DyeColor.GREEN), Blocks.WOOL.pick(DyeColor.GREEN)),
        RED(Blocks.CARPET.pick(DyeColor.RED), Blocks.WOOL.pick(DyeColor.RED)),
        BLACK(Blocks.CARPET.pick(DyeColor.BLACK), Blocks.WOOL.pick(DyeColor.BLACK));

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
        WOODEN("carpet_trapdoor", BlockSetType.OAK),
        IRON("carpet_iron_trapdoor", BlockSetType.IRON),
        COPPER("carpet_copper_trapdoor", BlockSetType.COPPER);

        public final String suffix;
        public final BlockSetType blockSetType;

        Type(String suffix, BlockSetType blockSetType) {
            this.suffix = suffix;
            this.blockSetType = blockSetType;
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
            BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, CarpetTrapdoors.id(id())))
                    .mapColor(carpet.carpet.defaultMapColor())
                    .sound(carpet.carpet.defaultBlockState().getSoundType())
                    .strength(0.1F)
                    .noOcclusion();
            return new CarpetTrapdoorBlock(type.blockSetType, properties, name());
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
        return new BlockItem(block, new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, id(id)))
                .useBlockDescriptionPrefix()) {
            @Override
            public Component getName(ItemStack stack) {
                return block.getName();
            }
        };
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    // Creative tab

    public static final String TAB_ID = MOD_ID;
    public static final Component TAB_TITLE = Component.translatable("itemGroup." + MOD_ID);

    /** Sets title and icon. The loader adds {@link #tabContents()} itself, since vanilla's tab output type isn't public. */
    public static CreativeModeTab.Builder configureTab(CreativeModeTab.Builder builder) {
        return builder
                .title(TAB_TITLE)
                .icon(() -> new ItemStack(block(new Variant(Carpet.RED, Type.WOODEN))));
    }

    public static List<Block> tabContents() {
        return BLOCKS.values().stream().<Block>map(Supplier::get).toList();
    }
}
