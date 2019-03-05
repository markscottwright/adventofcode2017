package adventofcode2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;

public class Day19 {

    public enum Direction {
        DOWN, UP, RIGHT, LEFT
    }

    public static class Position {
        final int x;
        final int y;
        final Direction direction;

        public Position(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Position other = (Position) obj;
            return direction == other.direction && x == other.x && y == other.y;
        }

        @Override
        public String toString() {
            return "Position [x=" + x + ", y=" + y + ", direction=" + direction
                    + "]";
        }
    }

    public static class Diagram {
        // careful - this is in y,x order
        final private char[][] diagram;
        final private int maxY;
        final private int maxX;

        public Diagram(char[][] diagram) {
            this.diagram = diagram;
            this.maxY = diagram.length;
            this.maxX = diagram[0].length;
        }

        Position entryPoint() {
            for (int x = 0; x < maxX; ++x) {
                if (diagram[0][x] == '|')
                    return new Position(x, 0, Direction.DOWN);
            }
            throw new RuntimeException();
        }

        Pair<Position, Character> move(Position pos) {
            char pathChar = diagram[pos.y][pos.x];
            Position newPos = null;

            // vertical and hit a junction
            if (pathChar == '+' && (pos.direction == Direction.DOWN
                    || pos.direction == Direction.UP)) {
                if (pos.x > 0 && diagram[pos.y][pos.x - 1] != ' ') {
                    newPos = new Position(pos.x - 1, pos.y, Direction.LEFT);
                } else if (pos.x < maxX && diagram[pos.y][pos.x + 1] != ' ') {
                    newPos = new Position(pos.x + 1, pos.y, Direction.RIGHT);
                }
            }

            // horizontal and hit a junction
            else if (pathChar == '+' && (pos.direction == Direction.RIGHT
                    || pos.direction == Direction.LEFT)) {
                if (pos.y > 0 && diagram[pos.y - 1][pos.x] != ' ') {
                    newPos = new Position(pos.x, pos.y - 1, Direction.UP);
                } else if (pos.y < maxY && diagram[pos.y + 1][pos.x] != ' ') {
                    newPos = new Position(pos.x, pos.y + 1, Direction.DOWN);
                }
            }

            // keep moving in current direction
            else {
                if (pos.direction == Direction.UP)
                    newPos = new Position(pos.x, pos.y - 1, Direction.UP);
                else if (pos.direction == Direction.DOWN)
                    newPos = new Position(pos.x, pos.y + 1, Direction.DOWN);
                else if (pos.direction == Direction.RIGHT)
                    newPos = new Position(pos.x + 1, pos.y, Direction.RIGHT);
                else if (pos.direction == Direction.LEFT)
                    newPos = new Position(pos.x - 1, pos.y, Direction.LEFT);
            }

            char newPosPathChar = diagram[newPos.y][newPos.x];
            if (newPosPathChar == ' ') {
                // pos was the end
                return null;
            } else {
                return Tuples.pair(newPos, newPosPathChar);
            }
        }

        static Diagram parse(String[] lines) {
            return new Diagram(Arrays
                    .stream(lines).map(l -> l.replaceAll("\n", "")
                            .replaceAll("\r", "").toCharArray())
                    .toArray(char[][]::new));
        }
    }

    public static void main(String[] args) throws IOException {
        String[] lines = Files.readAllLines(Paths.get("data", "day19.txt"))
                .toArray(String[]::new);
        Diagram diagram = Diagram.parse(lines);
        var posAndTile = Tuples.pair(diagram.entryPoint(), '|');
        
        
        int steps = 0;
        String lettersFound = "";
        while (posAndTile != null) {
            lettersFound = addIfLetter(lettersFound, posAndTile.getTwo());
            posAndTile = diagram.move(posAndTile.getOne());
            steps++;
        }
        
        System.out.println("Day 19 part 1:" + lettersFound);
        System.out.println("Day 19 part 2:" + steps);
    }

    public static String addIfLetter(String accumulatedLetters, Character c) {
        String cString = Character.toString(c);
        if ("abcdefghijklmnopqrstuvwxyz".contains(cString.toLowerCase()))
            return accumulatedLetters + cString;
        else
            return accumulatedLetters;
    }
}
