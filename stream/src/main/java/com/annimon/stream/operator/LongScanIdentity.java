package com.annimon.stream.operator;

import com.annimon.stream.function.LongBinaryOperator;
import com.annimon.stream.iterator.PrimitiveExtIterator;
import com.annimon.stream.iterator.PrimitiveIterator;
import org.jetbrains.annotations.NotNull;

public class LongScanIdentity extends PrimitiveExtIterator.OfLong {

    private final PrimitiveIterator.OfLong iterator;
    private final long identity;
    private final LongBinaryOperator accumulator;

    public LongScanIdentity(
            @NotNull PrimitiveIterator.OfLong iterator,
            long identity,
            @NotNull LongBinaryOperator accumulator) {
        this.iterator = iterator;
        this.identity = identity;
        this.accumulator = accumulator;
    }

    @Override
    protected void nextIteration() {
        if (!isInit) {
            // Return identity
            hasNext = true;
            next = identity;
            return;
        }
        hasNext = iterator.hasNext();
        if (hasNext) {
            final long current = iterator.nextLong();
            next = accumulator.applyAsLong(next, current);
        }
    }
}
