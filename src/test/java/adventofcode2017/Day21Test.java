package adventofcode2017;

import static org.junit.Assert.*;

import org.junit.Test;

import adventofcode2017.Day21.Matrix;

public class Day21Test {

    @Test
    public void testMatrix3Operations() {
        Matrix matrix = Matrix.parse(".#./..#/###");

        assertNotEquals(matrix, matrix.rotate90());
        assertEquals(matrix,
                matrix.rotate90().rotate90().rotate90().rotate90());
        assertEquals(matrix, matrix.flipHorizontal().flipHorizontal());
        assertEquals(matrix, matrix.flipVertical().flipVertical());
    }

    @Test
    public void testMatrix2Operations() {
        Matrix matrix = Matrix.parse(".#/##");
        assertNotEquals(matrix, matrix.rotate90());
        assertEquals(matrix,
                matrix.rotate90().rotate90().rotate90().rotate90());
        assertEquals(matrix, matrix.flipHorizontal().flipHorizontal());
        assertEquals(matrix, matrix.flipVertical().flipVertical());
    }
}
