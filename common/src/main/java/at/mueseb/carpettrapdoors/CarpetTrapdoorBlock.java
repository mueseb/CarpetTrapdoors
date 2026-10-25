package at.mueseb.carpettrapdoors;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
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

    private static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 1, 16);
    private static final VoxelShape TOP = Block.box(0, 15, 0, 16, 16, 16);
    private static final VoxelShape NORTH_OPEN = Block.box(0, 0, 15, 16, 16, 16);
    private static final VoxelShape SOUTH_OPEN = Block.box(0, 0, 0, 16, 16, 1);
    private static final VoxelShape WEST_OPEN = Block.box(15, 0, 0, 16, 16, 16);
    private static final VoxelShape EAST_OPEN = Block.box(0, 0, 0, 1, 16, 16);

    private final Component name;
    private final SoundType sound;

    public CarpetTrapdoorBlock(Properties properties, BlockSetType type) {
        this(properties, type, null, null);
    }

    /**
     * @param name  the display name, or {@code null} for the default name from the translation key
     * @param carpetSound the carpet's sound for walking on the trapdoor, or {@code null} to only use the trapdoor sound
     */
    public CarpetTrapdoorBlock(Properties properties, BlockSetType type, Component name, SoundType carpetSound) {
        super(properties, type);
        this.name = name;
        // Building sounds like the trapdoor type (wood, iron), walking on it sounds like the carpet it hides under.
        SoundType trapdoor = type.soundType();
        this.sound = carpetSound == null ? null : new SoundType(carpetSound.getVolume(), carpetSound.getPitch(),
                trapdoor.getBreakSound(), carpetSound.getStepSound(), trapdoor.getPlaceSound(), trapdoor.getHitSound(), carpetSound.getFallSound());
    }

    @Override
    public MutableComponent getName() {
        return name != null ? name.copy() : super.getName();
    }

    // TrapDoorBlock replaces the sound in the properties with the one of its block set type.
    @Override
    public SoundType getSoundType(BlockState state) {
        return sound != null ? sound : super.getSoundType(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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
