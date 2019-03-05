package adventofcode2017;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class Day18Test {

    @Test
    public void test() {
        //@formatter:off
        String src = "set a 1\r\n" + 
        "add a 2\r\n" + 
        "mul a a\r\n" + 
        "mod a 5\r\n" + 
        "snd a\r\n" + 
        "set a 0\r\n" + 
        "rcv a\r\n" + 
        "jgz a -1\r\n" + 
        "set a 1\r\n" + 
        "jgz a -2";
        //@formatter:on

        var program = Arrays.stream(src.split("\r\n")).map(Day18.Duet::parse)
                .collect(Collectors.toList());
        program.forEach(i -> System.out.println(i));
        Day18.Duet duet = new Day18.Duet();
        duet.run(program);
        assertEquals(4, duet.getLastSound().intValue());
    }

}
