package adventofcode2017;

import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.list.mutable.FastList;

public class Day16 {

    public static class Partner extends DanceMove {
        final String partnerA;
        final String partnerB;

        public Partner(String partnerA, String partnerB) {
            this.partnerA = partnerA;
            this.partnerB = partnerB;
        }

        @Override
        public String toString() {
            return "Partner [partnerA=" + partnerA + ", partnerB=" + partnerB
                    + "]";
        }

        @Override
        void performWith(MutableList<String> programs) {
            int aPos = programs.indexOf(partnerA);
            int bPos = programs.indexOf(partnerB);
            swap(programs, aPos, bPos);
        }
    }

    public static class Exchange extends DanceMove {
        final int positionA;
        final int positionB;

        public Exchange(int positionA, int positionB) {
            this.positionA = positionA;
            this.positionB = positionB;
        }

        @Override
        public String toString() {
            return "Exchange [positionA=" + positionA + ", positionB="
                    + positionB + "]";
        }

        @Override
        void performWith(MutableList<String> programs) {
            swap(programs, positionA, positionB);
        }
    }

    public static class Spin extends DanceMove {
        final int count;

        public Spin(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "Spin [count=" + count + "]";
        }

        @Override
        void performWith(MutableList<String> programs) {
            FastList<String> spun = FastList.newList(
                    programs.subList(programs.size() - count, programs.size()));
            spun.addAll(programs.subList(0, programs.size() - count));
            programs.clear();
            programs.addAll(spun);
        }
    }

    static abstract class DanceMove {
        static DanceMove parse(String text) {
            if (text.startsWith("s"))
                return new Spin(parseInt(text.substring(1)));
            else if (text.startsWith("x"))
                return new Exchange(parseInt(text.substring(1).split("/")[0]),
                        parseInt(text.substring(1).split("/")[1]));
            else if (text.startsWith("p"))
                return new Partner(text.substring(1, 2), text.substring(3, 4));
            else
                throw new IllegalArgumentException(text);
        }

        protected static void swap(MutableList<String> programs, int positionA,
                int positionB) {
            String tmp = programs.get(positionA);
            programs.set(positionA, programs.get(positionB));
            programs.set(positionB, tmp);
        }

        abstract void performWith(MutableList<String> programs);
    }

    public static void main(String[] args) throws IOException {
        String data = new String(
                Files.readAllBytes(Paths.get("data", "day16.txt")));

        var moves = ArrayAdapter.adapt(data.split(","))
                .collect(DanceMove::parse);
        var programs = Interval.fromTo('a', 'p').collect(Character::toString)
                .toList();

        moves.forEach(move -> move.performWith(programs));
        String finalState = programs.makeString("");
        System.out.println("Day 16 part 1:" + finalState);

        FastList<String> finalStates = FastList.newList();
        while (!finalStates.contains(finalState)) {
            finalStates.add(finalState);
            moves.forEach(move -> move.performWith(programs));
            finalState = programs.makeString("");
        }

        // subtract one, since we start at 0, so the billionth dance's index is
        // billion-1
        int expectedDanceNumberAtEnd = (1_000_000_000 - 1) % finalStates.size();
        System.out.println("Day 16 part 2:" + finalStates.get(expectedDanceNumberAtEnd));
    }
}
