package adventofcode2017;

import static java.nio.file.Files.readAllLines;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.list.mutable.ListAdapter;

public class Day20 {

    static class Particle {
        static int nextId = 0;
        final int id;
        long x, y, z;
        long xVelocity, yVelocity, zVelocity;
        long xAcceleration, yAcceleration, zAcceleration;

        public Particle(long x, long y, long z, long xVelocity, long yVelocity,
                long zVelocity, long xAcceleration, long yAcceleration,
                long zAcceleration) {
            this.id = nextId++;
            this.x = x;
            this.y = y;
            this.z = z;
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
            this.zVelocity = zVelocity;
            this.xAcceleration = xAcceleration;
            this.yAcceleration = yAcceleration;
            this.zAcceleration = zAcceleration;
        }

        long distanceFrom(long x, long y, long z) {
            return Math.abs(this.x - x) + Math.abs(this.y - y)
                    + Math.abs(this.z - z);
        }

        static Particle parse(String line) {
            String numpat = "\\s*([-0-9]+)";
            //@formatter:off
            var p = Pattern.compile(
                    "p=<" + numpat + "," + numpat + "," + numpat + ">,"
                    + "\\s*v=<" + numpat + "," + numpat + "," + numpat + ">,"
                    + "\\s*a=<" + numpat + "," + numpat + "," + numpat + ">");
                    //@formatter:on
            var m = p.matcher(line);
            if (!m.matches())
                throw new RuntimeException();
            return new Particle(Long.parseLong(m.group(1)),
                    Long.parseLong(m.group(2)), Long.parseLong(m.group(3)),
                    Long.parseLong(m.group(4)), Long.parseLong(m.group(5)),
                    Long.parseLong(m.group(6)), Long.parseLong(m.group(7)),
                    Long.parseLong(m.group(8)), Long.parseLong(m.group(9)));
        }

        void move() {
            xVelocity += xAcceleration;
            yVelocity += yAcceleration;
            zVelocity += zAcceleration;
            x += xVelocity;
            y += yVelocity;
            z += zVelocity;
        }

        @Override
        public String toString() {
            return "Particle " + "p=[" + x + "," + y + "," + z + "]" + " v=["
                    + xVelocity + "," + yVelocity + "," + zVelocity + "]"
                    + " a=[" + xAcceleration + "," + yAcceleration + ","
                    + zAcceleration + "]";
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Particle other = (Particle) obj;
            return x == other.x && y == other.y && z == other.z;
        }
    }

    public static void main(String[] args) throws IOException {
        var particles = ListAdapter
                .adapt(readAllLines(Paths.get("data", "day20.txt")))
                .collect(Particle::parse);

        // part 1
        for (int i = 0; i < 100_000; ++i)
            particles.forEach(Particle::move);
        Particle closestTo0 = particles.minBy(p -> p.distanceFrom(0, 0, 0));
        System.out.println("Day 20 part 1: " + closestTo0.id);

        particles = ListAdapter
                .adapt(readAllLines(Paths.get("data", "day20.txt")))
                .collect(Particle::parse);

        // part 2
        MutableBag<Particle> remainingParticles = HashBag.newBag(particles);
        for (int i = 0; i < 100_000; ++i) {
            // Particles are equal if their positions are the same, so multiple
            // occurrences means a collision. Remove those.
            remainingParticles = remainingParticles.collect(p -> {
                p.move();
                return p;
            }).selectByOccurrences(c -> c == 1);
        }

        System.out
                .println("Day 20 part 2: " + remainingParticles.sizeDistinct());
    }
}
