package semester1.practice12.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

public class Task1UniquePointsAndSets {
    public static void main(String[] args) {
        ArrayList<Point2D> sensorA = new ArrayList<>(Arrays.asList(
                new Point2D(0, 0),
                new Point2D(3, 4),
                new Point2D(1, 2),
                new Point2D(3, 4),
                new Point2D(6, 8),
                new Point2D(5, 0),
                new Point2D(1, 2)
        ));

        ArrayList<Point2D> sensorB = new ArrayList<>(Arrays.asList(
                new Point2D(3, 4),
                new Point2D(5, 0),
                new Point2D(2, 2),
                new Point2D(8, 6),
                new Point2D(2, 2),
                new Point2D(0, 10)
        ));

        HashSet<Point2D> setA = new HashSet<>(sensorA);
        HashSet<Point2D> setB = new HashSet<>(sensorB);

        System.out.println("================ 📡 ГЕОДЕЗИЧНИЙ РАДАР: HASHSET ТА ITERATOR 📡 ================");
        System.out.printf(Locale.US, "Сенсор A: отримано %d точок -> унікальних (HashSet): %d %s%n",
                sensorA.size(), setA.size(), setA);
        System.out.printf(Locale.US, "Сенсор B: отримано %d точок -> унікальних (HashSet): %d %s%n",
                sensorB.size(), setB.size(), setB);

        HashSet<Point2D> union = PointSetUtils.union(setA, setB);
        HashSet<Point2D> intersection = PointSetUtils.intersection(setA, setB);
        HashSet<Point2D> difference = PointSetUtils.difference(setA, setB);

        System.out.println("\n--- Операції над множинами (HashSet) ---");
        System.out.printf("Об'єднання (A U B) [%d]: %s%n", union.size(), union);
        System.out.printf("Перетин (A ∩ B) [%d]: %s%n", intersection.size(), intersection);
        System.out.printf("Різниця (A \\ B) [%d]: %s%n", difference.size(), difference);

        int removed = PointSetUtils.removeFarPoints(union, 7.0);

        System.out.println("\n--- Фільтрація через Iterator (R_max = 7.0) ---");
        System.out.println("Видалено точок поза радіусом: " + removed);
        System.out.printf("Залишилось у зоні [%d]: %s%n", union.size(), union);
        System.out.println("==============================================================================");
    }
}

final class Point2D {
    private final int x;
    private final int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double distanceFromOrigin() {
        return Math.hypot(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point2D)) {
            return false;
        }
        Point2D point = (Point2D) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}

final class PointSetUtils {
    private PointSetUtils() {
    }

    public static HashSet<Point2D> union(HashSet<Point2D> a, HashSet<Point2D> b) {
        HashSet<Point2D> result = new HashSet<>(a);
        result.addAll(b);
        return result;
    }

    public static HashSet<Point2D> intersection(HashSet<Point2D> a, HashSet<Point2D> b) {
        HashSet<Point2D> result = new HashSet<>(a);
        result.retainAll(b);
        return result;
    }

    public static HashSet<Point2D> difference(HashSet<Point2D> a, HashSet<Point2D> b) {
        HashSet<Point2D> result = new HashSet<>(a);
        result.removeAll(b);
        return result;
    }

    public static int removeFarPoints(HashSet<Point2D> points, double maxRadius) {
        int removed = 0;
        Iterator<Point2D> iterator = points.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().distanceFromOrigin() > maxRadius) {
                iterator.remove();
                removed++;
            }
        }

        return removed;
    }
}