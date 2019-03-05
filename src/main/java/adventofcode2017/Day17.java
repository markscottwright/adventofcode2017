package adventofcode2017;

import static java.lang.System.currentTimeMillis;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Day17 {
    static class Node {
        Node next;
        int val;

        public Node(int val) {
            next = this;
            this.val = val;
        }
    }

    static public int noStorageSpinlock(int step, int rounds) {
        int valueAtOne = 1;
        int pos = 1;
        for (int i = 2; i <= rounds; i++) {
            pos = (pos + step) % i + 1;
            if (pos == 1)
                valueAtOne = i;
        }
        return valueAtOne;
    }

    public static Node runSpinlockFaster(int step, int rounds) {
        Node buffer = new Node(0);
        Node pos = buffer;
        for (int i = 1; i <= rounds; ++i) {
            for (int j = 0; j < step; ++j) {
                pos = pos.next;
            }
            Node v = new Node(i);
            v.next = pos.next;
            pos.next = v;
            pos = v;

            if (i % 1_000_000 == 0) {
                System.out.println(i);
            }
        }
        return pos;
    }

    public static void main(String[] args) {

        int step = 386, rounds = 2017;

        var buffer = runSpinlock(step, rounds);
        int value = valueAfter(buffer, rounds);
        System.out.println("Day 17 part one:" + value);

        int valueAtOne = noStorageSpinlock(step, 50_000_000);
        System.out.println("Day 17 part two (faster):" + valueAtOne);
    }

    public static LinkedList<Integer> runSpinlock(int step, int rounds) {
        long lastTime = currentTimeMillis();
        LinkedList<Integer> buffer = new LinkedList<>();
        buffer.add(0);
        ListIterator<Integer> iterator = buffer.listIterator();
        for (int i = 1; i <= rounds; ++i) {
            for (int j = 0; j < step; ++j) {
                if (!iterator.hasNext()) {
                    iterator = buffer.listIterator();
                }
                iterator.next();
            }
            iterator.add(i);
            if (i % 100_000 == 0) {
                long currentTime = currentTimeMillis();
                System.out.println(currentTime - lastTime + ":" + i);
                lastTime = currentTime;
            }
        }
        return buffer;
    }

    public static int valueAfter(List<Integer> buffer, int toFind) {
        var iterator = buffer.listIterator();
        while (iterator.next() != toFind)
            ;

        // toFind at end of list? return first element
        if (iterator.hasNext())
            return iterator.next();
        else
            return buffer.get(0);
    }
}
