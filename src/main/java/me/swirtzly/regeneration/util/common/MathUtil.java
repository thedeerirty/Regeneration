package me.swirtzly.regeneration.util.common;

import net.minecraft.util.math.BlockPos;

public class MathUtil {

    public static BlockPos getDistance(BlockPos from, BlockPos to) {
        return to.subtract(from);
    }
}
