package adventofcode2017;

import static adventofcode2017.KnotHash.knotHash;
import static org.eclipse.collections.impl.list.Interval.zeroTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Day14Test {

    @Test
    public void testSample() {
        String sampleKeyString = "flqrgnkx";
        var used = zeroTo(127).collect(
                i -> Day14.stringToBits(knotHash(sampleKeyString + "-" + i)))
                .toList();

        assertTrue(Day14.toString(used.get(0)).startsWith("##.#.#.."));
        assertTrue(Day14.toString(used.get(1)).startsWith(".#.#.#.#"));
        assertTrue(Day14.toString(used.get(7)).startsWith("##.#.##."));

        var usedCount = zeroTo(127).flatCollect(
                i -> Day14.stringToBits(knotHash(sampleKeyString + "-" + i)))
                .count(v -> v == true);
        assertEquals(8108, usedCount);
    }

}
