package semester1.practice06.homework;

class QuadraticEquation {
    private double a;
    private double b;
    private double c;

    public void setCoefficients(double a, double b, double c) {
        if (a == 0) {
            System.out.println("Увага: коефіцієнт 'a' не може дорівнювати 0 (це лінійне рівняння)!");
            this.a = 1.0;
        } else {
            this.a = a;
        }
        this.b = b;
        this.c = c;
    }

    public double getDiscriminant() {
        return (this.b * this.b) - (4 * this.a * this.c);
    }

    public boolean hasRealRoots() {
        return getDiscriminant() >= 0;
    }

    public double getRoot1() {
        double d = getDiscriminant();
        if (d < 0) {
            return Double.NaN;
        }
        return (-this.b + Math.sqrt(d)) / (2 * this.a);
    }

    public double getRoot2() {
        double d = getDiscriminant();
        if (d < 0) {
            return Double.NaN;
        }
        return (-this.b - Math.sqrt(d)) / (2 * this.a);
    }

    public void printSolution() {
        System.out.println("Рівняння:                     " + this.a + "*x^2 + (" + this.b + ")*x + (" + this.c + ") = 0");

        double d = getDiscriminant();
        System.out.println("Дискримінант D:              " + d);

        if (d > 0) {
            System.out.println("Рівняння має 2 дійсні корені:");
            System.out.println("  x1 =                       " + getRoot1());
            System.out.println("  x2 =                       " + getRoot2());
        } else if (d == 0) {
            System.out.println("Рівняння має один дійсний корінь:");
            System.out.println("  x =                        " + getRoot1());
        } else {
            System.out.println("Рівняння не має дійсних коренів у множині R (D < 0).");
        }
    }
}

public class Task2QuadraticEquation {
    public static void main(String[] args) {
        System.out.println("==================== РОЗВ'ЯЗУВАЧ КВАДРАТНИХ РІВНЯНЬ ====================");
        System.out.println("Тест 1 (два корені):");
        QuadraticEquation eq1 = new QuadraticEquation();
        eq1.setCoefficients(1.0, -5.0, 6.0);
        eq1.printSolution();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Тест 2 (один корінь):");
        QuadraticEquation eq2 = new QuadraticEquation();
        eq2.setCoefficients(1.0, -4.0, 4.0);
        eq2.printSolution();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Тест 3 (без коренів):");
        QuadraticEquation eq3 = new QuadraticEquation();
        eq3.setCoefficients(1.0, 2.0, 5.0);
        eq3.printSolution();
        System.out.println("======================================================================");
    }
}