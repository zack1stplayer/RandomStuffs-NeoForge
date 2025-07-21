package net.zack1stplayer.randomstuffs.block.custom.burnables;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BurnableStairBlock extends StairBlock {
    private final int flammability;
    private final int fireSpreadSpeed;


    public BurnableStairBlock(BlockState baseState, Properties properties) {
        super(baseState, properties);
        this.flammability = 5;
        this.fireSpreadSpeed = 5;
    }
    public BurnableStairBlock(BlockState baseState, Properties properties, int flammability, int fireSpreadSpeed) {
        super(baseState, properties);
        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return this.flammability;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return this.fireSpreadSpeed;
    }
}
