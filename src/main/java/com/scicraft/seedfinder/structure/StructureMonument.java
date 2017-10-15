package com.scicraft.seedfinder.structure;

import com.scicraft.seedfinder.Biome;
import com.scicraft.seedfinder.BiomeGenerator;
import com.scicraft.seedfinder.model.XzPair;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StructureMonument extends Structure {
    private Random rnd = new Random();
    public static List<Biome> validSurroundingBiomes = Arrays.asList(
                    Biome.ocean,
                    Biome.deepOcean,
                    Biome.frozenOcean,
                    Biome.river,
                    Biome.frozenRiver,
                    // Not sure if the extended biomes count
                    Biome.oceanM,
                    Biome.deepOceanM,
                    Biome.frozenOceanM,
                    Biome.riverM,
                    Biome.frozenRiverM
    );

    /**
     * return the chunk position in the region of the possible Structure
     *
     * @param x
     * @param z
     * @param seed
     * @return
     */
    @Override
    public XzPair structurePosInRegion(long x, long z, long seed){
        rnd.setSeed((long) x * 341873128712L + (long)z * 132897987541L + seed + 10387313);
        return new XzPair((rnd.nextInt(27) + rnd.nextInt(27)) / 2 , (rnd.nextInt(27) + rnd.nextInt(27)) / 2);
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
    public XzPair structurePosInRegionFast(long xPart, long zPart, long seed, int lowerThen, int higherThen){
        rnd.setSeed(xPart + zPart + seed + 10387313);
        int xRand = (rnd.nextInt(27) + rnd.nextInt(27)) / 2;
        if(xRand <= lowerThen || xRand >= higherThen)
            return new XzPair(xRand, (rnd.nextInt(27) + rnd.nextInt(27)) / 2);
        else
            return null;
    }

    /**
     *
     *
     * @param x
     * @param y
     * @param size
     * @param validBiomes
     * @param generator
     * @return
     */
    public static boolean isValidBiome(int x, int y, int size, List<Biome> validBiomes, BiomeGenerator generator) {
        int x1 = x - size >> 2;
        int y1 = y - size >> 2;
        int x2 = x + size >> 2;
        int y2 = y + size >> 2;

        int width = x2 - x1 + 1;
        int height = y2 - y1 + 1;

        int[] arrayOfInt = generator.getBiomeData(x1, y1, width, height, true);
        for (int i = 0; i < width * height; i++) {
            Biome localBiome = Biome.biomes[arrayOfInt[i]];
            if (!validBiomes.contains(localBiome)) return false;
        }
        return true;
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
    public boolean structureWillSpawn(int xRegion, int zRegion, int xRandom, int zRandom, BiomeGenerator generator){
        if(		24 == generator.getBiomeAt(xRegion * 512 + xRandom * 16 + 8, zRegion * 512 +zRandom * 16 + 8)
                && isValidBiome(xRegion * 512 + xRandom * 16 + 8, zRegion * 512 +zRandom * 16 + 8, 29, validSurroundingBiomes, generator))
            return true;
        return false;
    }

}
