package adventofcode2017;

import static adventofcode2017.KnotHash.knotHash;
import static java.util.Arrays.asList;
import static org.eclipse.collections.impl.list.Interval.zeroTo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

public class Day14 {

    static List<Boolean> hexCharToBits(char h) {
        switch (Character.toLowerCase(h)) {
        //@formatter:off
        case '0': return asList(false, false, false, false);
        case '1': return asList(false, false, false, true);
        case '2': return asList(false, false, true, false);
        case '3': return asList(false, false, true, true);
        case '4': return asList(false, true, false, false);
        case '5': return asList(false, true, false, true);
        case '6': return asList(false, true, true, false);
        case '7': return asList(false, true, true, true);
        case '8': return asList(true, false, false, false);
        case '9': return asList(true, false, false, true);
        case 'a': return asList(true, false, true, false);
        case 'b': return asList(true, false, true, true);
        case 'c': return asList(true, true, false, false);
        case 'd': return asList(true, true, false, true);
        case 'e': return asList(true, true, true, false);
        case 'f': return asList(true, true, true, true);
        //@formatter:on
        }
        throw new IllegalArgumentException("not hex:" + h);
    }

    static ArrayList<Boolean> stringToBits(String s) {
        ArrayList<Boolean> out = new ArrayList<>();
        s.chars().forEach(c -> out.addAll(hexCharToBits((char) c)));
        return out;
    }

    static String toString(ArrayList<Boolean> bits) {
        String out = "";
        for (boolean b : bits) {
            out += b ? "#" : ".";
        }
        return out;
    }

    static class UsedSquares {

        private final UnifiedMap<Point, Integer> pointToRegion = new UnifiedMap<>();
        private int numRegions = 0;

        void assignToRegion(Point p, List<ArrayList<Boolean>> used,
                Integer regionNumber) {

            LinkedList<Point> candidates = new LinkedList<>();
            candidates.add(p);

            while (!candidates.isEmpty()) {
                Point candidate = candidates.remove();
                if (candidate.in(used)
                        && !pointToRegion.containsKey(candidate)) {
                    pointToRegion.put(candidate, regionNumber);
                    candidate.neighbors().forEach(each -> {
                        if (each.x >= 0 && each.x < 128 && each.y >= 0
                                && each.y < 128)
                            candidates.add(each);
                    });
                }
            }
        }

        public UsedSquares(List<ArrayList<Boolean>> used) {

            final int maxY = used.size();
            final int maxX = used.get(0).size();

            for (int y = 0; y < maxY; ++y) {
                for (int x = 0; x < maxX; ++x) {
                    Point pt = new Point(x, y);
                    if (pt.in(used) && !pointToRegion.containsKey(pt)) {
                        assignToRegion(pt, used, ++numRegions);
                    }
                }
            }
        }

        public int getNumRegions() {
            return numRegions;
        }

    }

    public static void main(String[] args) {
        String keyString = "stpzcrnm";
        var usedCount = zeroTo(127).flatCollect(i -> {
            return stringToBits(knotHash(keyString + "-" + i));
        }).count(v -> v == true);
        System.out.println("day 14 part 1:" + usedCount);

        var used = zeroTo(127).collect(i -> {
            return stringToBits(knotHash(keyString + "-" + i));
        }).toList();
        UsedSquares usage = new UsedSquares(used);
        System.out.println("day 14 part 2:" + usage.getNumRegions());
    }

}
