package semester1.practice09.homework;

class Point2D {
    private double x;
    private double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(Point2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public double distanceTo(Point2D other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", x, y);
    }
}

abstract class AbstractShape2D {
    private final String name;
    private final Point2D anchorPoint;

    public AbstractShape2D(String name, Point2D anchorPoint) {
        this.name = name;
        this.anchorPoint = (anchorPoint != null) ? new Point2D(anchorPoint) : new Point2D(0, 0);
    }

    public String getName() {
        return name;
    }

    public Point2D getAnchorPoint() {
        return new Point2D(anchorPoint);
    }

    public void moveBy(double dx, double dy) {
        anchorPoint.translate(dx, dy);
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    @Override
    public String toString() {
        return String.format("%s[name='%s', center=%s | S=%.2f, P=%.2f]",
                getClass().getSimpleName(), name, anchorPoint, getArea(), getPerimeter());
    }
}

class Circle2D extends AbstractShape2D {
    private double radius;

    public Circle2D(String name, Point2D center, double radius) {
        super(name, center);
        if (radius <= 0) {
            throw new IllegalArgumentException("Радіус має бути більше 0");
        }
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format("Circle2D[name='%s', center=%s, r=%.2f | S=%.2f, P=%.2f]",
                getName(), getAnchorPoint(), radius, getArea(), getPerimeter());
    }
}

class Rectangle2D extends AbstractShape2D {
    private double width;
    private double height;

    public Rectangle2D(String name, Point2D center, double width, double height) {
        super(name, center);
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Сторони мають бути більше 0");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return String.format("Rectangle2D[name='%s', center=%s, %.2fx%.2f | S=%.2f, P=%.2f]",
                getName(), getAnchorPoint(), width, height, getArea(), getPerimeter());
    }
}

class RightTriangle2D extends AbstractShape2D {
    private double legA;
    private double legB;

    public RightTriangle2D(String name, Point2D center, double legA, double legB) {
        super(name, center);
        if (legA <= 0 || legB <= 0) {
            throw new IllegalArgumentException("Катети мають бути більше 0");
        }
        this.legA = legA;
        this.legB = legB;
    }

    public double getHypotenuse() {
        return Math.sqrt(legA * legA + legB * legB);
    }

    @Override
    public double getArea() {
        return legA * legB / 2;
    }

    @Override
    public double getPerimeter() {
        return legA + legB + getHypotenuse();
    }

    @Override
    public String toString() {
        return String.format("RightTriangle2D[name='%s', center=%s, a=%.2f, b=%.2f, c=%.2f | S=%.2f, P=%.2f]",
                getName(), getAnchorPoint(), legA, legB, getHypotenuse(), getArea(), getPerimeter());
    }
}

class GeometryCanvas {
    private final String title;
    private final AbstractShape2D[] shapes;
    private int count;

    public GeometryCanvas(String title, int capacity) {
        this.title = title;
        this.shapes = new AbstractShape2D[capacity];
        this.count = 0;
    }

    public boolean addShape(AbstractShape2D shape) {
        if (shape == null || count >= shapes.length) {
            return false;
        }
        shapes[count] = shape;
        count++;
        return true;
    }

    public double getTotalArea() {
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += shapes[i].getArea();
        }
        return sum;
    }

    public double getTotalPerimeter() {
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += shapes[i].getPerimeter();
        }
        return sum;
    }

    public AbstractShape2D findLargestShape() {
        if (count == 0) {
            return null;
        }
        AbstractShape2D max = shapes[0];
        for (int i = 1; i < count; i++) {
            if (shapes[i].getArea() > max.getArea()) {
                max = shapes[i];
            }
        }
        return max;
    }

    public void moveAllShapes(double dx, double dy) {
        for (int i = 0; i < count; i++) {
            shapes[i].moveBy(dx, dy);
        }
    }

    @Override
    public String toString() {
        String result = "Полотно: '" + title + "' (Фігур: " + count + "/" + shapes.length + ")";
        for (int i = 0; i < count; i++) {
            result += "\n  " + (i + 1) + ". " + shapes[i];
        }
        return result;
    }
}

public class Task1CompositeShapesCanvas {
    public static void main(String[] args) {
        System.out.println("================ ГЕОМЕТРИЧНЕ ПОЛОТНО (КОМПОЗИЦІЯ + ABSTRACT) ================");

        // перевірка захисного копіювання
        Point2D p = new Point2D(0, 0);
        Circle2D circle = new Circle2D("Коло-А", p, 5);
        p.translate(100, 100);
        circle.getAnchorPoint().translate(50, 50);
        System.out.println("Перевірка копіювання, центр кола досі: " + circle.getAnchorPoint());

        GeometryCanvas canvas = new GeometryCanvas("Інженерне креслення №1", 5);
        canvas.addShape(circle);
        canvas.addShape(new Rectangle2D("Плита-Б", new Point2D(10, 5), 4, 6));
        canvas.addShape(new RightTriangle2D("Клин-В", new Point2D(-3, 2), 3, 4));

        System.out.println(canvas);
        System.out.println("-----------------------------------------------------------------------------");

        double totalArea = Math.round(canvas.getTotalArea() * 100) / 100.0;
        double totalPerimeter = Math.round(canvas.getTotalPerimeter() * 100) / 100.0;
        AbstractShape2D largest = canvas.findLargestShape();
        double largestArea = Math.round(largest.getArea() * 100) / 100.0;

        System.out.println("Сумарна площа креслення:    " + totalArea + " кв.од.");
        System.out.println("Сумарний периметр:          " + totalPerimeter + " од.");
        System.out.println("Найбільша фігура:           " + largest.getName() + " (S = " + largestArea + ")");

        System.out.println("-> Переміщуємо всі фігури полотна на вектор (+5.0; -2.0)...");
        canvas.moveAllShapes(5.0, -2.0);
        System.out.println("Новий центр '" + circle.getName() + "': " + circle.getAnchorPoint());
        System.out.println("=============================================================================");
    }
}