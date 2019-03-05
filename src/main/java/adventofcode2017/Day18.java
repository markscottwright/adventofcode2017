package adventofcode2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import adventofcode2017.Day18.Duet;

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
            if (duet.version == 1) {
                duet.setLastSound(duet.get(x));
                return 1;
            } else {
                duet.sendMessage(duet.get(x));
                return 1;
            }
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
            if (duet.version == 1) {
                if (duet.get(x) != 0)
                    return 1000000;
                else
                    return 1;
            } else {
                if (duet.hasMessages()) {
                    duet.setRegister(x, duet.getMessage());
                    duet.waiting = false;
                    return 1;
                } else {
                    duet.waiting = true;
                    // no pending messages. Try again
                    return 0;
                }
            }
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

        public final int version;
        private List<Instruction> program;
        private Duet partner;

        public boolean waiting = false;
        private HashMap<String, Long> registers = new HashMap<>();
        private LinkedList<Long> messageQueue = new LinkedList<>();
        private Long lastSound = 0L;
        private int lineNumber = 0;
        private int numSends = 0;

        Duet(List<Instruction> program) {
            this(program, -1);
        }

        Duet(List<Instruction> program, int programId) {
            this.program = program;
            if (programId == -1) {
                this.version = 1;
            } else {
                this.version = 2;
                registers.put("p", (long) programId);
            }
        }

        boolean next() {
            if (lineNumber < 0 || lineNumber >= program.size()) {
//                System.out.println("stopped");
                return false;
            } else {
                Instruction instruction = program.get(lineNumber);
//                System.out.println(this);
//                System.out.println(instruction);
                lineNumber += instruction.applyTo(this);
//                System.out.println(this);
                return true;
            }
        }

        Long runToCompletion() {
            while (next())
                ;
            return lastSound;
        }

        public Long getMessage() {
            assert !messageQueue.isEmpty();
            return messageQueue.remove();
        }

        public boolean hasMessages() {
            return !messageQueue.isEmpty();
        }

        public void sendMessage(Long v) {
            numSends++;
            partner.messageQueue.add(v);
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

        public void setPartner(Duet partner) {
            this.partner = partner;
        }

        @Override
        public String toString() {
            return "Duet [version=" + version + ", lineNumber=" + lineNumber
                    + ", registers=" + registers + ", messageQueue="
                    + messageQueue + "]";
        }

        public int getNumSends() {
            return this.numSends;
        }

        public void runToCompletionWith(Duet partner) {
            this.setPartner(partner);
            partner.setPartner(this);

            while (true) {
                boolean meComplete = !next();
                boolean partnerComplete = !partner.next();
                // both complete or both deadlocked?
                if (meComplete && partnerComplete
                        || waiting && partner.waiting)
                    break;
            }
            
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Instruction> program = new ArrayList<>();
        Files.readAllLines(Paths.get("data", "day18.txt"))
                .forEach(l -> program.add(Duet.parse(l)));

        Duet duet = new Duet(program);
        duet.runToCompletion();
        System.out.println("Day 18 part 1:" + duet.getLastSound());

        Duet duet0 = new Duet(program, 0);
        Duet duet1 = new Duet(program, 1);
        duet0.runToCompletionWith(duet1);
        System.out.println("Day 18 part 2:" + duet1.getNumSends());
    }
}
