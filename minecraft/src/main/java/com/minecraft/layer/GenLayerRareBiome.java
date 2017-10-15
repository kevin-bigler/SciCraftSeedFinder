package com.minecraft.layer;

import minecraft.biome.BiomeGenBase;

public class GenLayerRareBiome extends GenLayer
{
    public GenLayerRareBiome(long p_i45478_1_, GenLayer p_i45478_3_)
    {
        super(p_i45478_1_);
        this.parent = p_i45478_3_;
    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] var5 = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
        int[] var6 = IntCache.getIntCache(areaWidth * areaHeight);

        for (int var7 = 0; var7 < areaHeight; ++var7)
        {
            for (int var8 = 0; var8 < areaWidth; ++var8)
            {
                this.initChunkSeed((long)(var8 + areaX), (long)(var7 + areaY));
                int var9 = var5[var8 + 1 + (var7 + 1) * (areaWidth + 2)];

                if (this.nextInt(57) == 0)
                {
                    if (var9 == BiomeGenBase.plains.biomeID)
                    {
                        var6[var8 + var7 * areaWidth] = BiomeGenBase.plains.biomeID + 128;
                    }
                    else
                    {
                        var6[var8 + var7 * areaWidth] = var9;
                    }
                }
                else
                {
                    var6[var8 + var7 * areaWidth] = var9;
                }
            }
        }

        return var6;
    }
}