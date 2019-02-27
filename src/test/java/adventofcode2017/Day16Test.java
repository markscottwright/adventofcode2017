package adventofcode2017;

import static org.eclipse.collections.impl.list.mutable.FastList.newListWith;
import static org.junit.Assert.*;

import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Test;

import adventofcode2017.Day16.DanceMove;

public class Day16Test {

    @Test
    public void testSpin() {
        FastList<String> programs = newListWith("a", "b", "c");
        new Day16.Spin(2).performWith(programs);

        assertEquals(newListWith("b", "c", "a"), programs);
    }

    @Test
    public void testSample() {
        FastList<String> programs = newListWith("a", "b", "c", "d", "e");
        newListWith("s1", "x3/4", "pe/b")
                .forEach(move -> DanceMove.parse(move).performWith(programs));
        assertEquals(newListWith("b", "a", "e", "d", "c"), programs);
    }

}
