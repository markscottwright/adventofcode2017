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

        @Override
        public String toString() {
            String out = "";
            for (int y = getSide() - 1; y >= 0; --y) {
                if (!out.equals(""))
                    out += "/";
                for (int x = 0; x < getSide(); ++x) {
                    out += Character.toString(get(x, y));
                }
            }
            return out;

        }

        String print() {
            return toString().replace("/", "\n");
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
            else if (m2.length == 2)
                return new Matrix2(m2);

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
            m[0][0] = matrix[0][2];
            m[1][0] = matrix[0][1];
            m[2][0] = matrix[0][0];
            m[2][1] = matrix[1][0];
            m[2][2] = matrix[2][0];
            m[1][2] = matrix[2][1];
            m[0][2] = matrix[2][2];
            m[0][1] = matrix[1][2];
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

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.deepHashCode(matrix);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Matrix3 other = (Matrix3) obj;
            return Arrays.deepEquals(matrix, other.matrix);
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
            char[][] m = new char[2][2];
            m[0][0] = matrix[1][0];
            m[1][0] = matrix[0][0];
            m[1][1] = matrix[0][1];
            m[0][1] = matrix[1][1];
            return new Matrix2(m);
        }

        @Override
        Matrix flipHorizontal() {
            char[][] m = new char[2][2];
            m[0][0] = matrix[0][1];
            m[0][1] = matrix[0][0];
            m[1][1] = matrix[1][0];
            m[1][0] = matrix[1][1];
            return new Matrix2(m);
        }

        @Override
        int getSide() {
            return 2;
        }

        @Override
        char get(int x, int y) {
            return matrix[y][x];
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.deepHashCode(matrix);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Matrix2 other = (Matrix2) obj;
            return Arrays.deepEquals(matrix, other.matrix);
        }
    }

    static class BigMatrix {

        static char[][] submatrix(char[][] src, int xOrigin, int yOrigin,
                int side) {
            char[][] dest = new char[side][side];
            for (int x = 0; x < side; x++) {
                for (int y = 0; y < side; ++y) {
                    dest[y][x] = src[y + yOrigin][x + xOrigin];
                }
            }
            return dest;
        }

        private int side;
        private char[][] matrix;

        public BigMatrix(Matrix[][] components) {
            int componentSide = components[0][0].getSide();
            side = components.length * componentSide;

            matrix = new char[side][side];
            for (int y = 0; y < side; ++y) {
                for (int x = 0; x < side; ++x) {
                    matrix[y][x] = components[y / componentSide][x
                            / componentSide].get(x % componentSide,
                                    y % componentSide);
                }
            }
        }

        Matrix[][] divide() {
            Matrix[][] dest;
            if (side % 2 == 0) {
                int newSide = side / 2;
                dest = new Matrix[newSide][newSide];
                for (int x = 0; x < newSide; ++x) {
                    for (int y = 0; y < newSide; ++y) {
                        dest[x][y] = new Matrix2(
                                submatrix(matrix, x * 2, y * 2, 2));
                    }
                }
            } else {
                assert side % 3 == 0;
                int newSide = side / 3;
                dest = new Matrix[newSide][newSide];
                for (int x = 0; x < newSide; ++x) {
                    for (int y = 0; y < newSide; ++y) {
                        dest[x][y] = new Matrix2(
                                submatrix(matrix, x * 3, y * 3, 3));
                    }
                }
            }
            return dest;
        }
    }

    public static void main(String[] args) {
        Matrix matrix = Matrix.parse(".#./..#/###");
    }
}
