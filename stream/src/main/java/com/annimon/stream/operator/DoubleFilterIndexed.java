package com.annimon.stream.operator;

import com.annimon.stream.function.IndexedDoublePredicate;
import com.annimon.stream.iterator.PrimitiveIndexedIterator;
import com.annimon.stream.iterator.PrimitiveIterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

public class DoubleFilterIndexed extends PrimitiveIterator.OfDouble {

    private final PrimitiveIndexedIterator.OfDouble iterator;
    private final IndexedDoublePredicate predicate;
    private boolean hasNext, hasNextEvaluated;
    private double next;

    public DoubleFilterIndexed(
            @NotNull PrimitiveIndexedIterator.OfDouble iterator,
            @NotNull IndexedDoublePredicate predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        if (!hasNextEvaluated) {
            nextIteration();
            hasNextEvaluated = true;
        }
        return hasNext;
    }

    @Override
    public double nextDouble() {
        if (!hasNextEvaluated) {
            hasNext = hasNext();
        }
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        hasNextEvaluated = false;
        return next;
    }

    private void nextIteration() {
        while (iterator.hasNext()) {
            final int index = iterator.getIndex();
            next = iterator.nextDouble();
            if (predicate.test(index, next)) {
                hasNext = true;
                return;
            }
        }
        hasNext = false;
    }
}
