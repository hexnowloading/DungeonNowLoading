package dev.hexnowloading.dungeonnowloading.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class DNLMath {
    public static BlockPos rotateVector(BlockPos vec, Direction.Axis axis, double theta){
        double x, y, z;
        double u, v, w;
        vec.relative(Direction.NORTH);
        x=vec.getX();
        y=vec.getY();
        z=vec.getZ();
        switch (axis) {
            case X:
            default:
                u=0.0d;
                v=0.0d;
                w=0.0d;
                break;
            case Y:
                u=0.0d;
                v=0.0d;
                w=0.0d;
                break;
            case Z:
                u=0.0d;
                v=0.0d;
                w=0.0d;
        }

        double a = u*x + v*y + w*z;

        double xPrime = u* a *(1d - Math.cos(theta))
                + x*Math.cos(theta)
                + (-w*y + v*z)*Math.sin(theta);
        double yPrime = v* a *(1d - Math.cos(theta))
                + y*Math.cos(theta)
                + (w*x - u*z)*Math.sin(theta);
        double zPrime = w* a *(1d - Math.cos(theta))
                + z*Math.cos(theta)
                + (-v*x + u*y)*Math.sin(theta);
        return new BlockPos((int) Math.round(xPrime), (int) Math.round(yPrime), (int) Math.round(zPrime));
    }
}
