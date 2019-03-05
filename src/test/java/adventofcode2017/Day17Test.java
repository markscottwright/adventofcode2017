package adventofcode2017;

import static org.junit.Assert.*;

import org.junit.Test;

public class Day17Test {

    @Test
    public void testSampleData() {
        var buffer = Day17.runSpinlock(3, 2017);
        assertEquals(638, Day17.valueAfter(buffer, 2017));
    }
    
    @Test
    public void testPartTwo() {
        assertEquals(1, Day17.noStorageSpinlock(3, 1));
        assertEquals(2, Day17.noStorageSpinlock(3, 2));
        assertEquals(2, Day17.noStorageSpinlock(3, 3));
        assertEquals(2, Day17.noStorageSpinlock(3, 4));
        assertEquals(5, Day17.noStorageSpinlock(3, 5));
    }
    
}
