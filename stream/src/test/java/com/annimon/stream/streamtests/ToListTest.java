package com.annimon.stream.streamtests;

import com.annimon.stream.Stream;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public final class ToListTest {

    @Test
    public void testToList() {
        assertThat(Stream.range(0, 5).toList(),
                contains(0, 1, 2, 3, 4));
    }
}
