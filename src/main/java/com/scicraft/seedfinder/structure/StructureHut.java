package com.scicraft.seedfinder.structure;

import com.scicraft.seedfinder.BiomeGenerator;
import com.scicraft.seedfinder.model.XzPair;

import java.util.Random;

public class StructureHut extends Structure {
    private Random rnd = new Random();

    /**
     * return the chunk position in the region of the possible Structure
     *
     * @param x
     * @param z
     * @param seed
     * @return
     */
    @Override
    public XzPair structurePosInRegion(final long x, final long z, final long seed){
        rnd.setSeed((long) x * 341873128712L + (long)z * 132897987541L + seed + 14357617);
        return new XzPair(rnd.nextInt(24), rnd.nextInt(24));
    }

    /**
     * first check if the x pos is valid else return null
     *
     * @param xPart
     * @param zPart
     * @param seed
     * @param lowerThen
     * @param higherThen
     * @return
     */
    @Override
    public XzPair structurePosInRegionFast(final long xPart,
                                           final long zPart,
                                           final long seed,
                                           final int lowerThen,
                                           final int higherThen){
        rnd.setSeed(xPart + zPart + seed + 14357617);
        final int xRand = rnd.nextInt(24);
        if(xRand <= lowerThen || xRand >= higherThen) {
            return new XzPair(xRand, rnd.nextInt(24));
        } else {
            return null;
        }
    }

    /**
     * checks if it will spawn
     *
     * @param xRegion
     * @param zRegion
     * @param xRandom
     * @param zRandom
     * @param generator
     * @return
     */
    @Override
    public boolean structureWillSpawn(final int xRegion,
                                      final int zRegion,
                                      final int xRandom,
                                      final int zRandom,
                                      final BiomeGenerator generator){
        return 6 == generator.getBiomeAt(xRegion * 512 + xRandom * 16 + 8, zRegion * 512 +zRandom * 16 + 8);
    }

}
