package xfacthd.framedblocks.common.data.conpreds.slab;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.predicate.contex.NonDetailedConnectionPredicate;
import xfacthd.framedblocks.api.util.Utils;

public final class DoubleSlabConnectionPredicate extends NonDetailedConnectionPredicate
{
    public static final DoubleSlabConnectionPredicate INSTANCE = new DoubleSlabConnectionPredicate();

    private DoubleSlabConnectionPredicate() { }

    @Override
    public boolean canConnectFullEdge(BlockState state, Direction side, @Nullable Direction edge)
    {
        return Utils.isY(side) || (edge != null && Utils.isY(edge));
    }
}
