package adventofcode2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class Day21 {

    static abstract class Matrix {
        abstract Matrix rotate90();

        abstract Matrix flip();

        abstract int getSide();

        abstract char get(int x, int y);

        Matrix rotate180() {
            return rotate90().rotate90();
        }

        Matrix rotate270() {
            return rotate90().rotate90().rotate90();
        }

        String print() {
            String out = "";
            for (int y = getSide() - 1; y >= 0; --y) {
                for (int x = 0; x < getSide(); ++x) {
                    out += Character.toString(get(x, y));
                }
                out += "\n";
            }
            return out;
        }

        static Matrix parse(String line) {
            ArrayList<char[]> m = Arrays.stream(line.split("/"))
                    .map(String::toCharArray)
                    .collect(Collectors.toCollection(ArrayList::new));

            // matrix ctors expect arrays in reverse order
            Collections.reverse(m);
            char[][] m2 = m.toArray(new char[0][]);
            if (m2.length == 3)
                return new Matrix3(m2);

            throw new RuntimeException(line);
        }
    }

    /**
     * Internally, use 0, 0 as upper left corner. [x][y]
     * 
     * @author mwright
     *
     */
    static class Matrix3 extends Matrix {
        private char[][] matrix;

        public Matrix3(char[][] m) {
            // should come in with row 0 first
            this.matrix = m;
        }

        @Override
        Matrix3 rotate90() {
            char[][] m = new char[3][3];
            put(0, 0, get(1, 0));
            put(1, 0, get(2, 0));
            put(2, 0, get(2, 1));
            put(2, 1, get(2, 2));
            put(2, 2, get(1, 2));
            put(1, 2, get(0, 2));
            put(0, 2, get(0, 1));
            put(0, 1, get(0, 0));
            put(1, 1, get(1, 1));
            return new Matrix3(m);
        }

        @Override
        Matrix3 flip() {
            char[][] m = new char[3][3];
            put(0, 0, get(0, 2));
            put(1, 0, get(1, 2));
            put(2, 0, get(2, 2));
            put(2, 0, get(2, 2));
            return new Matrix3(m);
        }

        @Override
        int getSide() {
            return 3;
        }

        @Override
        char get(int x, int y) {
            return matrix[y][x];
        }

        private void put(int x, int y, char v) {
            matrix[y][x] = v;
        }
    }

    static class Matrix2 extends Matrix {

        @Override
        Matrix rotate90() {
            char[]][] m = new char[2][2];
            m[0][0] = get(0, 1);
            m[1][0] = get(0, 0);
            m[1][1] = get(0, 1);
        }

        @Override
        Matrix flip() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        int getSide() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        char get(int x, int y) {
            // TODO Auto-generated method stub
            return 0;
        }

    }

    public static void main(String[] args) {
        System.out.println(Matrix.parse(".#./..#/###").print());
    }
}
