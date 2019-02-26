package adventofcode2017;

import static adventofcode2017.KnotHash.rotate;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import org.junit.Test;

public class KnotHashTest {

    @Test
    public void testRotate() {
        var src = asList(1, 2, 3, 4, 5, 6);
        assertEquals(src, rotate(src, 6));
        assertEquals(asList(2, 3, 4, 5, 6, 1), rotate(src, 1));

        var src2 = new int[] { 1, 2, 3, 4, 5, 6 };
        assertArrayEquals(src2, rotate(src2, 6));
        assertArrayEquals(new int[] { 2, 3, 4, 5, 6, 1 }, rotate(src2, 1));
    }

    @Test
    public void testReverseRotation() {
        var src = new int[] { 1, 2, 3, 4, 5, 6 };
        assertArrayEquals(src, rotate(rotate(src, 2), -2));
        assertArrayEquals(src, rotate(src, -6));
        assertArrayEquals(new int[] { 6, 1, 2, 3, 4, 5 }, rotate(src, -1));
    }

    @Test
    public void testKnotHashes() {
        assertEquals("a2582a3a0e66e6e86e3812dcb672a272", KnotHash.knotHash(""));
    }
}
