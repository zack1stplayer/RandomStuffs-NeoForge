package net.zack1stplayer.randomstuffs.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.zack1stplayer.randomstuffs.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ModLogBlock extends RotatedPillarBlock {
    private static final Map<Holder<Block>, DeferredBlock<Block>> STRIPPING_MAP =
            Map.of(
                    ModBlocks.BLOODWOOD_LOG, ModBlocks.STRIPPED_BLOODWOOD_LOG,
                    ModBlocks.BLOODWOOD_WOOD, ModBlocks.STRIPPED_BLOODWOOD_WOOD
            );

    public ModLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem axeItem) {
            if(STRIPPING_MAP.containsKey(state.getBlockHolder())) {
                return STRIPPING_MAP.get(state.getBlockHolder()).get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
