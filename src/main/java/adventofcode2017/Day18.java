package adventofcode2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day18 {

    public static abstract class Instruction {
        abstract int applyTo(Duet duet);

        final String x;
        final String y;

        public Instruction(String x, String y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            if (y == null)
                return getClass().getSimpleName() + " [x=" + x + "]";
            else
                return getClass().getSimpleName() + " [x=" + x + ", y=" + y
                        + "]";
        }
    }

    public static class Snd extends Instruction {

        public Snd(String x) {
            super(x, null);
        }

        @Override
        int applyTo(Duet duet) {
            duet.setLastSound(duet.get(x));
            return 1;
        }

    }

    public static class Set extends Instruction {

        public Set(String x, String y) {
            super(x, y);
        }

        @Override
        int applyTo(Duet duet) {
            duet.setRegister(x, duet.get(y));
            return 1;
        }

    }

    public static class Add extends Instruction {

        public Add(String x, String y) {
            super(x, y);
        }

        @Override
        int applyTo(Duet duet) {
            duet.setRegister(x, duet.get(x) + duet.get(y));
            return 1;
        }

    }

    public static class Mul extends Instruction {

        public Mul(String x, String y) {
            super(x, y);
        }

        @Override
        int applyTo(Duet duet) {
            duet.setRegister(x, duet.get(x) * duet.get(y));
            return 1;
        }

    }

    public static class Mod extends Instruction {

        public Mod(String x, String y) {
            super(x, y);
        }

        @Override
        int applyTo(Duet duet) {
            duet.setRegister(x, duet.get(x) % duet.get(y));
            return 1;
        }

    }

    public static class Rcv extends Instruction {

        public Rcv(String x) {
            super(x, null);
        }

        @Override
        int applyTo(Duet duet) {
            if (duet.get(x) != 0)
                return 1000000;
            else
                return 1;
        }

    }

    public static class Jgz extends Instruction {

        public Jgz(String x, String y) {
            super(x, y);
        }

        @Override
        int applyTo(Duet duet) {
            if (duet.get(x) > 0)
                return duet.get(y).intValue();
            else
                return 1;
        }

    }

    static class Duet {

        HashMap<String, Long> registers;
        Long lastSound = 0L;

        Long run(List<Instruction> program) {
            lastSound = 0L;
            registers = new HashMap<>();
            int lineNumber = 0;
            while (lineNumber >= 0 && lineNumber < program.size()) {
                Instruction instruction = program.get(lineNumber);
//                System.out.print(lineNumber + " " + instruction + ":"
//                        + lastSound + ":" + registers);
                lineNumber += instruction.applyTo(this);
                // System.out.println(" -> " + lastSound + ":" + registers);
            }
            return lastSound;
        }

        public void setRegister(String registerName, Long value) {
            registers.put(registerName, value);
        }

        public void setLastSound(Long value) {
            lastSound = value;
        }

        public Long get(String x) {
            try {
                return Long.parseLong(x);
            } catch (NumberFormatException e) {
                return registers.getOrDefault(x, 0L);
            }
        }

        static Instruction parse(String line) {
            var fields = line.split(" ");
            switch (fields[0]) {
            //@formatter:off
            case "snd": return new Snd(fields[1]);
            case "rcv": return new Rcv(fields[1]);
            case "set": return new Set(fields[1], fields[2]);
            case "add": return new Add(fields[1], fields[2]);
            case "mul": return new Mul(fields[1], fields[2]);
            case "mod": return new Mod(fields[1], fields[2]);
            case "jgz": return new Jgz(fields[1], fields[2]);
            //@formatter:on
            }
            throw new RuntimeException(line);
        }

        public Long getLastSound() {
            return lastSound;
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Instruction> program = new ArrayList<>();
        Files.readAllLines(Paths.get("data", "day18.txt"))
                .forEach(l -> program.add(Duet.parse(l)));

        Duet duet = new Duet();
        duet.run(program);
        System.out.println("Day 18 part 1:" + duet.getLastSound());
    }
}
