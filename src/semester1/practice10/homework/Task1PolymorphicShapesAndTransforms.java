package semester1.practice10.homework;

interface Scalable2D {
    double MIN_SCALE_FACTOR = 0.1;
    void scale(double factor);
}

interface Rotatable2D {
    void rotate(double deltaDegrees);
    double getRotationAngle();
}

abstract class AbstractShape2D {
    private final String name;
    private double centerX;
    private double centerY;

    public AbstractShape2D(String name, double centerX, double centerY) {
        this.name = name;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public String getName() {
        return name;
    }

    public abstract double getArea();
    public abstract double getPerimeter();

    public final double getIsoperimetricQuotient() {
        double p = getPerimeter();
        if (p == 0) return 0;
        return (4 * Math.PI * getArea()) / (p * p);
    }

    @Override
    public String toString() {
        return "'" + name + "', center=(" + centerX + "; " + centerY + ")";
    }
}

class CircleShape extends AbstractShape2D implements Scalable2D {
    private double radius;

    public CircleShape(String name, double centerX, double centerY, double radius) {
        super(name, centerX, centerY);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
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
    public void scale(double factor) {
        if (factor < MIN_SCALE_FACTOR) {
            factor = MIN_SCALE_FACTOR;
        }
        this.radius *= factor;
    }

    @Override
    public String toString() {
        return "CircleShape[" + super.toString() +
                String.format(", r=%.2f | S=%.2f, P=%.2f, Q=%.4f]", radius, getArea(), getPerimeter(), getIsoperimetricQuotient());
    }
}

class RectangleShape extends AbstractShape2D implements Scalable2D, Rotatable2D {
    private double width;
    private double height;
    private double rotationAngle;

    public RectangleShape(String name, double centerX, double centerY, double width, double height) {
        super(name, centerX, centerY);
        this.width = width;
        this.height = height;
        this.rotationAngle = 0.0;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
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
    public void scale(double factor) {
        if (factor < MIN_SCALE_FACTOR) {
            factor = MIN_SCALE_FACTOR;
        }
        this.width *= factor;
        this.height *= factor;
    }

    @Override
    public void rotate(double deltaDegrees) {
        rotationAngle = (rotationAngle + deltaDegrees) % 360;
        if (rotationAngle < 0) {
            rotationAngle += 360;
        }
    }

    @Override
    public double getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public String toString() {
        return "RectangleShape[" + super.toString() +
                String.format(", %.2fx%.2f, angle=%.1f° | S=%.2f, P=%.2f, Q=%.4f]", width, height, rotationAngle, getArea(), getPerimeter(), getIsoperimetricQuotient());
    }
}

final class ImmutableReferenceSquare extends AbstractShape2D {
    private final double side;

    public ImmutableReferenceSquare(String name, double centerX, double centerY, double side) {
        super(name, centerX, centerY);
        this.side = side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getPerimeter() {
        return 4 * side;
    }

    @Override
    public String toString() {
        return "ImmutableReferenceSquare[" + super.toString() +
                String.format(", side=%.2f | S=%.2f, P=%.2f, Q=%.4f]", side, getArea(), getPerimeter(), getIsoperimetricQuotient());
    }
}

public class Task1PolymorphicShapesAndTransforms {

    public static double calculateTotalArea(AbstractShape2D[] shapes) {
        double totalArea = 0;
        for (AbstractShape2D shape : shapes) {
            totalArea += shape.getArea();
        }
        return totalArea;
    }

    public static void transformScene(AbstractShape2D[] shapes, double scaleFactor, double rotateDeg) {
        System.out.println("-> Виконуємо пакетну трансформацію (scale x" + scaleFactor + ", rotate +" + rotateDeg + "°) через instanceof...");

        for (AbstractShape2D shape : shapes) {
            boolean isTransformed = false;

            if (shape instanceof Scalable2D) {
                Scalable2D scalable = (Scalable2D) shape;
                scalable.scale(scaleFactor);

                if (shape instanceof CircleShape) {
                    CircleShape circle = (CircleShape) shape;
                    System.out.println("   [Scalable2D] '" + shape.getName() + "' масштабовано в " + scaleFactor + " разів (новий r=" + String.format("%.2f", circle.getRadius()) + ")");
                } else if (shape instanceof RectangleShape) {
                    RectangleShape rect = (RectangleShape) shape;
                    System.out.println("   [Scalable2D] '" + shape.getName() + "' масштабовано в " + scaleFactor + " разів (" + String.format("%.2fx%.2f", rect.getWidth(), rect.getHeight()) + ")");
                }
                isTransformed = true;
            }

            if (shape instanceof Rotatable2D) {
                Rotatable2D rotatable = (Rotatable2D) shape;
                rotatable.rotate(rotateDeg);
                System.out.println("   [Rotatable2D] '" + shape.getName() + "' повернуто на +" + rotateDeg + "° (поточний кут: " + rotatable.getRotationAngle() + "°)");
                isTransformed = true;
            }

            if (!isTransformed) {
                System.out.println("   [Info] '" + shape.getName() + "' (final class) пропущено — не підтримує трансформації.");
            }
        }
    }

    public static void main(String[] args) {
        AbstractShape2D[] shapes = new AbstractShape2D[] {
                new CircleShape("Коло-Лінза", 0.0, 0.0, 5.0),
                new RectangleShape("Рамка-А", 2.0, 3.0, 4.0, 6.0),
                new ImmutableReferenceSquare("Еталон-Квадрат", 10.0, 10.0, 5.0)
        };

        System.out.println("1. ================ ПОЛІМОРФНА ГЕОМЕТРІЯ ТА ІНТЕРФЕЙСИ ТРАНСФОРМАЦІЙ ================");
        System.out.println("[СТАН СЦЕНИ ДО ТРАНСФОРМАЦІЇ]:");
        for (int i = 0; i < shapes.length; i++) {
            System.out.println((i == 0 ? "" : "  " + (i + 1) + ". ") + shapes[i].toString());
        }

        System.out.println("Сумарна площа сцени: " + String.format("%.2f", calculateTotalArea(shapes)) + " кв.од.");

        transformScene(shapes, 2.0, 45.0);

        System.out.println("[СТАН СЦЕНИ ПІСЛЯ ТРАНСФОРМАЦІЇ]:");
        for (int i = 0; i < shapes.length; i++) {
            System.out.println((i == 0 ? "" : "  " + (i + 1) + ". ") + shapes[i].toString());
        }

        System.out.println("Нова сумарна площа сцени: " + String.format("%.2f", calculateTotalArea(shapes)) + " кв.од.");
        System.out.println("==================================================================================");
    }
}