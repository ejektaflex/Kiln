package ejektaflex.kilnx.ext

import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos

fun BlockPos.AABB(): AxisAlignedBB {
    return AxisAlignedBB(x.toDouble(), y.toDouble(), z.toDouble(),
            x.toDouble() + 1.0, y.toDouble() + 1.0, z.toDouble() + 1.0)
}