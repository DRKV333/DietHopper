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
        makeAABB(6, 0, 6, 10, 4, 10),
        makeAABB(0, 0, 0, 0, 0, 0),
        makeAABB(6, 4, 0, 10, 8, 4),
        makeAABB(6, 4, 12, 10, 8, 16),
        makeAABB(0, 4, 6, 4, 8, 10),
        makeAABB(12, 4, 6, 16, 8, 10)
    };

    private static AxisAlignedBB makeAABB(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
        return AxisAlignedBB.getBoundingBox(-toX / 16F, -toY / 16F, -toZ / 16F, -fromX / 16F, -fromY / 16F, -fromZ / 16F);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end) {
        int metadata = world.getBlockMetadata(x, y, z);
        int facing = getDirectionFromMetadata(metadata);
        
        if (facing >= 0 && facing < bottom.length) {
            Vec3 pos = Vec3.createVectorHelper(x, y, z);
            Vec3 startRel = start.subtract(pos);
            Vec3 endRel = end.subtract(pos);
            
            if (top.calculateIntercept(startRel, endRel) != null ||
                middle.calculateIntercept(startRel, endRel) != null ||
                bottom[facing].calculateIntercept(startRel, endRel) != null)
            {
                return super.collisionRayTrace(world, x, y, z, start, end);
            }
        }

        return null;
    }
}
