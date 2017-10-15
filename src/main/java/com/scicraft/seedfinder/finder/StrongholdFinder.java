package com.scicraft.seedfinder.finder;

import com.scicraft.seedfinder.Biome;
import com.scicraft.seedfinder.BiomeGenerator;
import com.scicraft.seedfinder.model.XzPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrongholdFinder {

    public static XzPair findValidLocation(final int searchX,
                                           final int searchY,
                                           final int size,
                                           final List<Biome> paramList,
                                           final Random random,
                                           final BiomeGenerator generator) {
        // TODO: Find out if we should useQuarterResolutionMap or not
        final int x1 = searchX - size >> 2;
        final int y1 = searchY - size >> 2;
        final int x2 = searchX + size >> 2;
        final int y2 = searchY + size >> 2;

        final int width = x2 - x1 + 1;
        final int height = y2 - y1 + 1;
        final int[] arrayOfInt = generator.getBiomeData(x1, y1, width, height, true);
        XzPair location = null;
        int numberOfValidFound = 0;
        for (int i = 0; i < width*height; i++) {
            final int x = x1 + i % width << 2;
            final int y = y1 + i / width << 2;
            if (arrayOfInt[i] > Biome.biomes.length)
                return null;
            final Biome localBiome = Biome.biomes[arrayOfInt[i]];
            if ((!paramList.contains(localBiome)) || ((location != null) && (random.nextInt(numberOfValidFound + 1) != 0))) {
                continue;
            }
            location = new XzPair(x, y);
            numberOfValidFound++;
        }

        return location;
    }

    public XzPair[] findStrongholds(final long seed, final BiomeGenerator generator) {
        final XzPair[] strongholds = new XzPair[3];
        final Random random = new Random();
        random.setSeed(seed);

        final List<Biome> biomeArrayList = new ArrayList<Biome>();

        //TODO: don't be lazy and put them in a list
        for (int i = 0; i < Biome.biomes.length; i++) {
            if ((Biome.biomes[i] != null) && (Biome.biomes[i].type.value1 > 0f)) {
                biomeArrayList.add(Biome.biomes[i]);
            }
        }

        double angle = random.nextDouble() * 3.141592653589793D * 2.0D;
        for (int i = 0; i < 3; i++) {
            final double distance = (1.25D + random.nextDouble()) * 32.0D;
            int x = (int)Math.round(Math.cos(angle) * distance);
            int z = (int)Math.round(Math.sin(angle) * distance);

            final XzPair location = findValidLocation((x << 4) + 8, (z << 4) + 8, 112, biomeArrayList,random , generator);
            if (location != null){
                x = location.getX() >> 4;
                z = location.getZ() >> 4;
            }

            strongholds[i] = new XzPair((x << 4), (z << 4));
            angle += 6.283185307179586D / 3.0D;
        }

        return strongholds;
    }
}
