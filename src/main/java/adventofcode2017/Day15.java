package adventofcode2017;

import org.eclipse.collections.impl.list.Interval;

public class Day15 {
    public static class Generator {

        private static final long DIVISOR = 2147483647;
        private long value;
        private long factor;
        private long mustBeMultipleOf;

        public Generator(long factor, long initialValue) {
            this(factor, initialValue, 0);
        }

        public Generator(long factor, long initialValue,
                long mustBeMultipleOf) {
            this.factor = factor;
            this.value = initialValue;
            this.mustBeMultipleOf = mustBeMultipleOf;
        }

        public long next() {
            value = (value * factor) % DIVISOR;
            return value;
        }

        public long next2() {
            assert mustBeMultipleOf > 0;

            long possibleValue = 0;
            do {
                possibleValue = next();
            } while ((possibleValue % mustBeMultipleOf) != 0);
            return possibleValue;
        }

        static boolean valuesMatch(long a, long b) {
            long aLeast16 = a & 0xffffL;
            long bLeast16 = b & 0xffffL;

            return aLeast16 == bLeast16;
        }
    }

    public static void main(String[] args) {
        {
            Generator a = new Generator(16807, 512);
            Generator b = new Generator(48271, 191);

            long judgeCount = Interval.fromTo(0, 40_000_000)
                    .count(each -> Generator.valuesMatch(a.next(), b.next()));
            System.out.println("Day 15 part 1:" + judgeCount);
        }
        {
            Generator a = new Generator(16807, 512, 4);
            Generator b = new Generator(48271, 191, 8);
            long judgeCount = Interval.fromTo(0, 5_000_000)
                    .count(each -> Generator.valuesMatch(a.next2(), b.next2()));
            System.out.println("Day 15 part 2:" + judgeCount);
        }
    }
}
