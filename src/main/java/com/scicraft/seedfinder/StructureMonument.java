package com.scicraft.seedfinder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StructureMonument extends Structure {
    private Random rnd = new Random();

    /*
     * return the chunk position in the region of the possible Structure
     */
    public XzPair structurePosInRegion(long x, long z, long seed){
        rnd.setSeed((long) x * 341873128712L + (long)z * 132897987541L + seed + 10387313);
        return new XzPair((rnd.nextInt(27) + rnd.nextInt(27)) / 2 , (rnd.nextInt(27) + rnd.nextInt(27)) / 2);
    }

    /*
     * first check if the x pos is valid else return null
     */
    public XzPair structurePosInRegionFast(long xPart, long zPart, long seed, int lowerThen, int higherThen){
        rnd.setSeed(xPart + zPart + seed + 10387313);
        int xRand = (rnd.nextInt(27) + rnd.nextInt(27)) / 2;
        if(xRand <= lowerThen || xRand >= higherThen)
            return new XzPair(xRand, (rnd.nextInt(27) + rnd.nextInt(27)) / 2);
        else
            return null;
    }

    public static List<Biome> validSurroundingBiomes = Arrays.asList(
            new Biome[] {
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
                Biome.frozenRiverM,
            }
        );

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


    /*
     * checks if it will spawn
     * @see com.scicraft.seedfinder.Structure#structureWillSpawn(int xRegion, int zRegion, int xRandom, int zRandom, com.scicraft.seedfinder.BiomeGenerator)
     */
    public boolean structureWillSpawn(int xRegion, int zRegion, int xRandom, int zRandom, BiomeGenerator generator){
        if(		generator.getBiomeAt(xRegion * 512 + xRandom * 16 + 8, zRegion * 512 +zRandom * 16 + 8) == 24 &&
                isValidBiome(xRegion * 512 + xRandom * 16 + 8, zRegion * 512 +zRandom * 16 + 8, 29, validSurroundingBiomes, generator))
            return true;
        return false;
    }

}
