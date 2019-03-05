package adventofcode2017;

import org.junit.Test;

import adventofcode2017.Day20.Particle;

public class Day20Test {

    @Test
    public void testSample() {
        Particle p1 = Day20.Particle.parse("p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>");
        Particle p2 = Day20.Particle.parse("p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>");
        
        for (int i=0; i < 1_000_000; ++i) {
            p1.move();
            p2.move();
        }
        System.out.println(p1.distanceFrom(0, 0, 0));
        System.out.println(p2.distanceFrom(0, 0, 0));
    }

}
