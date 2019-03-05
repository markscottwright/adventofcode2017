package adventofcode2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class Day21 {

    static abstract class Matrix {
        abstract Matrix rotate90();

        abstract Matrix flipVertical();

        abstract Matrix flipHorizontal();

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
            m[1][1] = matrix[1][1];
            m[0][0] = matrix[1][0];
            m[1][0] = matrix[2][0];
            m[2][0] = matrix[2][1];
            m[2][1] = matrix[2][2];
            m[2][2] = matrix[1][2];
            m[1][2] = matrix[0][2];
            m[0][2] = matrix[0][1];
            m[0][1] = matrix[0][0];
            return new Matrix3(m);
        }

        @Override
        Matrix3 flipVertical() {
            char[][] m = new char[3][3];
            m[0][0] = matrix[2][0];
            m[0][1] = matrix[2][1];
            m[0][2] = matrix[2][2];
            m[1][0] = matrix[1][0];
            m[1][1] = matrix[1][1];
            m[1][2] = matrix[1][2];
            m[2][0] = matrix[0][0];
            m[2][1] = matrix[0][1];
            m[2][2] = matrix[0][2];
            return new Matrix3(m);
        }

        @Override
        Matrix3 flipHorizontal() {
            char[][] m = new char[3][3];
            m[0][0] = matrix[0][2];
            m[1][0] = matrix[1][2];
            m[2][0] = matrix[2][2];
            m[0][1] = matrix[0][1];
            m[1][1] = matrix[1][1];
            m[2][1] = matrix[2][1];
            m[0][2] = matrix[0][0];
            m[1][2] = matrix[1][0];
            m[2][2] = matrix[2][0];
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
    }

    static class Matrix2 extends Matrix {

        final private char[][] matrix;

        public Matrix2(char[][] m) {
            matrix = m;
        }

        @Override
        Matrix rotate90() {
            char[][] m = new char[2][2];
            m[0][0] = matrix[0][1];
            m[1][0] = matrix[0][0];
            m[1][1] = matrix[1][0];
            m[0][1] = matrix[1][1];
            return new Matrix2(m);
        }

        @Override
        Matrix flipVertical() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        Matrix flipHorizontal() {
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
        Matrix matrix = Matrix.parse(".#./..#/###");
        System.out.println(matrix.print());
        System.out.println(matrix.rotate90().print());
        System.out.println(matrix.flipHorizontal().print());
        System.out.println(matrix.flipVertical().print());
    }
}
