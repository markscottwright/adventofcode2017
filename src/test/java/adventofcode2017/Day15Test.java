package adventofcode2017;

import static org.junit.Assert.*;

import org.eclipse.collections.impl.list.Interval;
import org.junit.Test;

import adventofcode2017.Day15.Generator;

public class Day15Test {

    @Test
    public void testValues() {
        Generator a = new Generator(16807, 65);
        Generator b = new Generator(48271, 8921);

        long[] values = new long[] { 1092455, 430625591, 1181022009, 1233683848,
                245556042, 1431495498, 1744312007, 137874439, 1352636452,
                285222916 };

        int i = 0;
        while (i < values.length) {
            assertEquals(values[i++], a.next());
            assertEquals(values[i++], b.next());
        }
    }

    @Test
    public void testJudgeCount() {
        Generator a = new Generator(16807, 65);
        Generator b = new Generator(48271, 8921);

        int judgeCount = Interval.fromTo(1, 5)
                .count(each -> Generator.valuesMatch(a.next(), b.next()));
        assertEquals(1, judgeCount);
    }

    @Test
    public void testJudgeCount2() {

        Generator a = new Generator(16807, 65);
        Generator b = new Generator(48271, 8921);
        int judgeCount = Interval.fromTo(0, 40_000_000)
                .count(each -> Generator.valuesMatch(a.next(), b.next()));
        assertEquals(588, judgeCount);
    }

    @Test
    public void testJudgeCount3() {

        Generator a = new Generator(16807, 65, 4);
        Generator b = new Generator(48271, 8921, 8);
        int judgeCount = Interval.fromTo(0, 5_000_000)
                .count(each -> Generator.valuesMatch(a.next2(), b.next2()));
        assertEquals(309, judgeCount);
    }

    @Test
    public void testValues2() {
        Generator a = new Generator(16807, 65, 4);
        Generator b = new Generator(48271, 8921, 8);

        long[] values = new long[] { 1352636452, 1233683848, 1992081072,
                862516352, 530830436, 1159784568, 1980017072, 1616057672,
                740335192, 412269392 };

        int i = 0;
        while (i < values.length) {
            assertEquals(values[i++], a.next2());
            assertEquals(values[i++], b.next2());
        }
    }
}
