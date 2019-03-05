package adventofcode2017;

import static org.junit.Assert.*;

import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.Before;
import org.junit.Test;

import adventofcode2017.Day19.Diagram;
import adventofcode2017.Day19.Direction;
import adventofcode2017.Day19.Position;

public class Day19Test {

    private Diagram diagram;

    @Test
    public void testFindingEntryPoint() {
        assertEquals(new Day19.Position(5, 0, Direction.DOWN),
                diagram.entryPoint());
    }

    @Test
    public void testFollowingPath() {
        Position position = diagram.entryPoint();

        var next = diagram.move(position);
        assertEquals('|', next.getTwo().charValue());
        assertEquals(Direction.DOWN, next.getOne().direction);

        position = diagram.entryPoint();
        char mapTile = '|';
        for (int i = 0; i < 6; ++i) {
            Pair<Position, Character> move = diagram.move(position);
            position = move.getOne();
            mapTile = move.getTwo();
        }
        assertEquals('B', mapTile);

    }

    @Test
    public void testFollowingPathToEnd() {
        var pos = Tuples.pair(diagram.entryPoint(), '|');
        String chars = "";
        while (pos != null) {
            chars = Day19.addIfLetter(chars, pos.getTwo());
            pos = diagram.move(pos.getOne());
        }
        assertEquals("ABCDEF", chars);
    }

    @Before
    public void setUp() {
        String sampleDiagram =
        //@formatter:off
                "     |          \r\n" + 
                "     |  +--+    \r\n" + 
                "     A  |  C    \r\n" + 
                " F---|----E|--+ \r\n" + 
                "     |  |  |  D \r\n" + 
                "     +B-+  +--+ ";
        diagram = Day19.Diagram.parse(sampleDiagram.split("\r\n"));
    }

}
