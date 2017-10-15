package com.scicraft.seedfinder;

import java.util.Iterator;

import static com.scicraft.seedfinder.constants.MessageConstant.BIT_ITER_ERROR_NO_NEXT;

public class BitIterator implements Iterator<Long> {
    private long baseSeed;
    private long current;
    private long baseEnd = 65536;

    /**
     * insert potential seed to do whatever with first 16 bits this class iterates over all of them
     * use this class to for example to check biomes
     * use this as a normal iterator: while(hasNext()){BitIterator.next()}
     *
     * @param baseSeed
     */
    public BitIterator(final long baseSeed) {
        //magic number check it in binary this removes the first 16 bits
        this.baseSeed = baseSeed & 281474976710655L;
        this.current = 0;
    }

    @Override
    public boolean hasNext(){
        return this.current < this.baseEnd;
    }

    /**
     * return the next modified seed
     *
     * @return
     */
    @Override
    public Long next() {
        if (!hasNext()) {
            throw new IllegalStateException(BIT_ITER_ERROR_NO_NEXT);
        }

        current++;
        return (long) baseSeed ^ ((long) current << 48);
    }
}
