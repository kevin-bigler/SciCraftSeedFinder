package com.scicraft.seedfinder;

import com.minecraft.layer.GenLayer;
import com.minecraft.layer.IntCache;

public class BiomeGenerator {
    public GenLayer biomeIndexLayer;
    public GenLayer biomeIndexLayerQuarter;

    public BiomeGenerator(final long seed, final int quarter) {
        if (quarter == 0) {
            //1:1 resolution
            biomeIndexLayer = GenLayer.func_180781_a(seed, "")[1];
        } else if (quarter == 1) {
            // 1:4 fourth resolution less calculations
            biomeIndexLayerQuarter = GenLayer.func_180781_a(seed, "")[0];
        } else {
            // 1:4 fourth resolution less calculations
            biomeIndexLayer = GenLayer.func_180781_a(seed, null)[1];
            biomeIndexLayerQuarter = GenLayer.func_180781_a(seed, "")[0];
        }

    }

    public int getBiomeAt(final int x, final int y) {
        IntCache.resetIntCache();
        return biomeIndexLayer.getInts(x, y, 1, 1)[0];
    }

    public int[] getBiomeData(final int x, final int y, final int width, final int height, final boolean quarter) {
        IntCache.resetIntCache();
        if(quarter) {
            return biomeIndexLayerQuarter.getInts(x, y, width, height);
        } else {
            return biomeIndexLayer.getInts(x, y, width, height);
        }
    }
}
