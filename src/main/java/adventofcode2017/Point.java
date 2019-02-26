package adventofcode2017;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class Point {

    final int x;
    final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    UnifiedSet<Point> neighbors() {
        return UnifiedSet.newSetWith(new Point(x, y - 1), new Point(x - 1, y),
                new Point(x + 1, y), new Point(x, y + 1));
    }

    public boolean isPositive() {
        return x > 0 && y > 0;
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
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
        Point other = (Point) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    public boolean in(List<ArrayList<Boolean>> used) {
        return used.get(y).get(x);
    }

}
