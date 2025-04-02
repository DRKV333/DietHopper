package com.rwtema.diethopper;

import net.minecraft.block.BlockHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockDietHopper extends BlockHopper {
    private static final AxisAlignedBB top = makeAABB(0, 10, 0, 16, 16, 16);
    private static final AxisAlignedBB middle = makeAABB(4, 4, 4, 12, 10, 12);

    private static final AxisAlignedBB[] bottom = new AxisAlignedBB[] {
        makeAABB(6, 0, 6, 10, 4, 10), // DOWN
        makeAABB(0, 0, 0, 0, 0, 0), // UP
        makeAABB(6, 4, 0, 10, 8, 4), // NORTH
        makeAABB(6, 4, 12, 10, 8, 16), // SOUTH
        makeAABB(0, 4, 6, 4, 8, 10), // WEST
        makeAABB(12, 4, 6, 16, 8, 10) // EAST
    };

    private static AxisAlignedBB makeAABB(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
        return AxisAlignedBB.getBoundingBox(fromX / 16F, fromY / 16F, fromZ / 16F, toX / 16F, toY / 16F, toZ / 16F);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end) {
        int metadata = world.getBlockMetadata(x, y, z);
        int facing = getDirectionFromMetadata(metadata);

        if (facing < 0 || facing >= bottom.length)
            return null;

        Vec3 pos = Vec3.createVectorHelper(x, y, z);
        
        // Vec3.subtract works backwards...
        Vec3 startRel = pos.subtract(start);
        Vec3 endRel = pos.subtract(end);

        MovingObjectPosition relIntercept = collisionRel(facing, startRel, endRel);
        if (relIntercept != null) {
            return new MovingObjectPosition(x, y, z, relIntercept.sideHit, relIntercept.hitVec.addVector(x, y, z));   
        }

        return null;
    }

    private static MovingObjectPosition collisionRel(int facing, Vec3 startRel, Vec3 endRel) {
        MovingObjectPosition topIntercept = top.calculateIntercept(startRel, endRel);
        if (topIntercept != null)
            return topIntercept;

        MovingObjectPosition middleIntercept = middle.calculateIntercept(startRel, endRel);
        if (middleIntercept != null)
            return middleIntercept;

        MovingObjectPosition bottomIntercept = bottom[facing].calculateIntercept(startRel, endRel);
        if (bottomIntercept != null)
            return bottomIntercept;

        return null;
    }
}
