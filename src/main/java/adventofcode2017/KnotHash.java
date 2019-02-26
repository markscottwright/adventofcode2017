package adventofcode2017;

import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOf;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.ArrayUtils.reverse;

import java.util.ArrayList;
import java.util.List;

public class KnotHash {
    public static <T> ArrayList<T> rotate(List<T> source, int offset) {
        if (offset < 0) {
            offset = source.size() + offset;
        }

        ArrayList<T> dest = new ArrayList<>(source.size());
        dest.addAll(source.subList(offset, source.size()));
        dest.addAll(source.subList(0, offset));
        return dest;
    }

    public static int[] rotate(int[] string, int offset) {
        if (offset < 0) {
            offset = string.length + offset;
        }

        int[] dest = new int[string.length];
        System.arraycopy(string, offset, dest, 0, string.length - offset);
        System.arraycopy(string, 0, dest, string.length - offset, offset);
        return dest;
    }

    /**
     * Convert an array of 256 ints into 16 xored values
     */
    static int[] denseHash(int[] sparseHash) {
        assert 256 == sparseHash.length;

        int[] denseHashPart = new int[16];
        int outPos = 0;
        for (int i = 0; i < 256; i += 16) {
            int hashPart = 0;
            for (int j = 0; j < 16; ++j) {
                hashPart = hashPart ^ sparseHash[i + j];
            }
            denseHashPart[outPos++] = hashPart;
        }
        return denseHashPart;
    }

    public static int[] sparseHash(List<Integer> lengths) {
        lengths = new ArrayList<>(lengths);
        lengths.addAll(List.of(17, 31, 73, 47, 23));

        int[] string = range(0, 256).toArray();
        int pos = 0;
        int skipSize = 0;
        for (int i = 0; i < 64; ++i) {
            for (Integer length : lengths) {
                assert length < string.length;

                // rotate string so we're always working from 0
                var workString = rotate(string, pos);

                // reverse the first <length> bytes of string
                var twistedSection = copyOf(workString, length);
                reverse(twistedSection);
                arraycopy(twistedSection, 0, workString, 0, length);

                // rotate back
                workString = rotate(workString, -pos);

                // move ahead, with wrap around
                pos = (pos + length + skipSize) % string.length;
                skipSize++;
                string = workString;
            }
        }

        return string;
    }

    public static String knotHash(String s) {
        var hashValue = denseHash(sparseHash(asciiValues(s)));
        
        String out = "";
        for (int v : hashValue) {
            out += String.format("%02x", v);
        }
        return out;
    }

    private static ArrayList<Integer> asciiValues(String s) {
        ArrayList<Integer> lengths = new ArrayList<>();
        for (char c : s.toCharArray()) {
            lengths.add((int) c);
        }
        return lengths;
    }

}
