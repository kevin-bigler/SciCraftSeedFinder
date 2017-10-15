package com.scicraft.seedfinder.structure;

import com.scicraft.seedfinder.BiomeGenerator;
import com.scicraft.seedfinder.model.XzPair;

public abstract class Structure {
    /**
     * generate the xpart of the equation
     *
     * @param x
     * @return
     */
    public long xPart(final int x){
        return (long) x * 341873128712L;
    }

    /**
     * generate the zpart of the equation
     *
     * @param z
     * @return
     */
    public long zPart(final int z){
        return (long) z * 132897987541L;
    }

    public abstract XzPair structurePosInRegion(long x, long z, long seed);
    public abstract XzPair structurePosInRegionFast(long xPart, long zPart, long seed, int lowerThen, int higherThen);
    public abstract boolean structureWillSpawn(int xRegion, int zRegion, int xRandom, int zRandom, BiomeGenerator generator);
}
