package adventofcode2017;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import adventofcode2017.Day18.Duet;
import adventofcode2017.Day18.Instruction;

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
        Day18.Duet duet = new Day18.Duet(program);
        duet.runToCompletion();
        assertEquals(4, duet.getLastSound().intValue());
    }

    @Test
    public void testParallel() {
        String sampleSource =
        //@formatter:off
                "snd 1\r\n" + 
                "snd 2\r\n" + 
                "snd p\r\n" + 
                "rcv a\r\n" + 
                "rcv b\r\n" + 
                "rcv c\r\n" + 
                "rcv d";
                //@formatter:on
        ArrayList<Instruction> program = new ArrayList<>();
        for (String l : sampleSource.split("\r\n")) {
            program.add(Duet.parse(l));
        }

        Duet duet0 = new Duet(program, 0);
        Duet duet1 = new Duet(program, 1);
        duet0.setPartner(duet1);
        duet1.setPartner(duet0);

        while (true) {
            boolean duet1Complete = !duet0.next();
            boolean duet2Complete = !duet1.next();
            // both complete or both deadlocked?
            if (duet1Complete && duet2Complete
                    || duet0.waiting && duet1.waiting)
                break;
        }
        assertEquals(3, duet0.getNumSends());
    }

}
