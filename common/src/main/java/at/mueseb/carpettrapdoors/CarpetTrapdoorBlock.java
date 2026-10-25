package at.mueseb.carpettrapdoors;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A trapdoor that is only one pixel thick, so it looks and feels like a carpet.
 */
public class CarpetTrapdoorBlock extends TrapDoorBlock {

    public static final MapCodec<CarpetTrapdoorBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockSetType.CODEC.fieldOf("block_set_type").forGetter(CarpetTrapdoorBlock::getType),
            propertiesCodec()
    ).apply(instance, CarpetTrapdoorBlock::new));

    private static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 1, 16);
    private static final VoxelShape TOP = Block.box(0, 15, 0, 16, 16, 16);
    private static final VoxelShape NORTH_OPEN = Block.box(0, 0, 15, 16, 16, 16);
    private static final VoxelShape SOUTH_OPEN = Block.box(0, 0, 0, 16, 16, 1);
    private static final VoxelShape WEST_OPEN = Block.box(15, 0, 0, 16, 16, 16);
    private static final VoxelShape EAST_OPEN = Block.box(0, 0, 0, 1, 16, 16);

    private final Component name;

    public CarpetTrapdoorBlock(BlockSetType type, Properties properties) {
        this(type, properties, null);
    }

    /** @param name the display name, or {@code null} for the default name from the translation key */
    public CarpetTrapdoorBlock(BlockSetType type, Properties properties, Component name) {
        super(type, properties);
        this.name = name;
    }

    @Override
    public MutableComponent getName() {
        return name != null ? name.copy() : super.getName();
    }

    @Override
    public MapCodec<? extends TrapDoorBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!state.getValue(OPEN)) {
            return state.getValue(HALF) == Half.TOP ? TOP : BOTTOM;
        }
        return switch (state.getValue(FACING)) {
            case SOUTH -> SOUTH_OPEN;
            case WEST -> WEST_OPEN;
            case EAST -> EAST_OPEN;
            default -> NORTH_OPEN;
        };
    }
}
