package com.seedfinder;

import com.scicraft.seedfinder.BiomeGenerator;
import com.scicraft.seedfinder.BitIterator;
import com.scicraft.seedfinder.structure.StructureHut;
import com.scicraft.seedfinder.model.XzPair;

import java.util.Random;

public class QuadHutFinder {
    public final static int TOPRIGHT = 0;
    public final static int BOTTOMRIGHT = 1;
    public final static int BOTTOMLEFT = 2;
    public final static int TOPLEFT = 3;
    public static Random rnd = new Random();
    public static int[] xpos = new int[4];
    public static int[] zpos = new int[4];
    public static int xmon, zmon;
    public static StructureHut hut;
    public static BitIterator bitIt;


    public static boolean allSwamp(final int[] x, final int[] z, final BiomeGenerator generate) {
        for (int i = 0; i < 4; i++) {
            if (generate.getBiomeAt(x[i] * 16 + 8, z[i] * 16 + 8) != 6) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkForStructureBR(final int x, final int z, final long seed) {
        final XzPair coords = hut.structurePosInRegion(x, z, seed);
        final int xrand = coords.getX();
        final int zrand = coords.getZ();
        xpos[TOPLEFT] = x  * 32 + xrand;
        zpos[TOPLEFT] = z  * 32 + zrand;

        return xrand >= 22 && zrand >= 22;
    }

    private static boolean checkForStructureBL(final int x, final int z, final long seed) {
        final XzPair coords = hut.structurePosInRegion(x, z, seed);
        final int xrand = coords.getX();
        final int zrand = coords.getZ();
        xpos[TOPRIGHT] = x  * 32 + xrand;
        zpos[TOPRIGHT] = z  * 32 + zrand;

        return xrand <= 1 && zrand >= 22;
    }

    private static boolean checkForStructureTR(final int x, final int z, final long seed) {
        final XzPair coords = hut.structurePosInRegion(x, z, seed);
        final int xrand = coords.getX();
        final int zrand = coords.getZ();
        xpos[BOTTOMLEFT] = x  * 32 + xrand;
        zpos[BOTTOMLEFT] = z  * 32 + zrand;

        return xrand >= 22 && zrand <= 1;
    }

    private static boolean checkForStructureTL(final int x, final int z, final long seed) {
        final XzPair coords = hut.structurePosInRegion(x, z, seed);
        final int xrand = coords.getX();
        final int zrand = coords.getZ();
        xpos[BOTTOMRIGHT] = x  * 32 + xrand;
        zpos[BOTTOMRIGHT] = z  * 32 + zrand;

        return xrand <= 1 && zrand <= 1;
    }

    public static void checkBits(final long seed) {
        //magic number
        final long seedBit = seed & 281474976710655L;
        bitIt = new BitIterator(seedBit);

        System.out.println("checking bits of base " + seedBit);
        System.out.println((xpos[0] * 16) + " " + (zpos[0] * 16));
        System.out.println((xpos[1] * 16) + " " + (zpos[1] * 16));
        System.out.println((xpos[2] * 16) + " " + (zpos[2] * 16));
        System.out.println((xpos[3] * 16) + " " + (zpos[3] * 16));

        while(bitIt.hasNext()){
            final long seedFull = bitIt.next();
            final BiomeGenerator generate = new BiomeGenerator(seedFull, 2);
            if (allSwamp(xpos, zpos, generate)) {
                System.out.println(seedFull);
            }
        }
    }

    public static void main(final String[] args) {
        //final long startSeed = Long.parseLong(args[0]);
        final long startSeed = 278827814000L;
        System.out.println(startSeed);

        //higher than 2^48 will be useless
        final long endSeed = 281474976710656L;
        final int radius = 4;
        int xr;
        int zr;
        hut = new StructureHut();
        for (long currentSeed = startSeed; currentSeed <= endSeed; currentSeed++){

            for (int x = -radius; x < radius - 1; x += 2) {

                final long xPart = hut.xPart(x);

                for (int z = -radius; z < radius - 1; z += 2) {

                    final long zPart = hut.zPart(z);
                    final XzPair coords = hut.structurePosInRegionFast(xPart, zPart, currentSeed, 1, 22);

                    if (coords != null) {
                        xr = coords.getX();
                        zr = coords.getZ();

                        if (xr <= 1) {

                            if (zr <= 1) {
                                // candidate witch hut, is in the top left of the 32x32 chunk array
                                // this means that to be in a quad it would be in bottom right of the quad

                                // check the 32x32 chunk area neighbors to the left and above
                                if (checkForStructureTR(x - 1, z, currentSeed)
                                        && checkForStructureBR(x - 1, z - 1, currentSeed)
                                        && checkForStructureBL(x, z - 1, currentSeed)) {
                                        xpos[BOTTOMRIGHT] =  x * 32 + xr;
                                        zpos[BOTTOMRIGHT] =  z * 32 + zr;
                                        checkBits(currentSeed);
                                }

                            } else if (zr >= 22) {
                                // candidate witch hut, is in the bottom left of the 32x32 chunk array
                                // this means that to be in a quad it would be in top right of the quad

                                // check the 32x32 chunk area neighbors to the left and below
                                if (checkForStructureTL(x, z + 1, currentSeed)
                                        && checkForStructureTR(x - 1, z + 1, currentSeed)
                                        && checkForStructureBR(x - 1, z, currentSeed)) {
                                        xpos[TOPRIGHT] =  x * 32 + xr;
                                        zpos[TOPRIGHT] =  z * 32 + zr;
                                        checkBits(currentSeed);
                                }
                            }

                        } else {
                            if (zr <= 1) {
                                // candidate witch hut, is in the top right of the 32x32 chunk array
                                // this means that to be in a quad it would be in bottom left of the quad

                                // check the 32x32 chunk area neighbors to the right and above
                                if (checkForStructureBR(x, z - 1, currentSeed)
                                        && checkForStructureBL(x + 1, z - 1, currentSeed)
                                        && checkForStructureTL(x + 1, z, currentSeed)) {
                                        xpos[BOTTOMLEFT] =  x * 32 + xr;
                                        zpos[BOTTOMLEFT] =  z * 32 + zr;
                                        checkBits(currentSeed);
                                }
                            } else if (zr >= 22) {
                                // candidate witch hut, is in the bottom right of the 32x32 chunk array
                                // this means that to be in a quad it would be in top left of the quad

                                // check the 32x32 chunk area neighbors to the right and below
                                if (checkForStructureBL(x + 1, z, currentSeed)
                                        && checkForStructureTL(x + 1, z + 1, currentSeed)
                                        && checkForStructureTR(x, z + 1, currentSeed)) {
                                        xpos[TOPLEFT] =  x * 32 + xr;
                                        zpos[TOPLEFT] =  z * 32 + zr;
                                        checkBits(currentSeed);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}