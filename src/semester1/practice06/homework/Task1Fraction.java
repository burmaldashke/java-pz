package semester1.practice06.homework;

class Fraction {
    private int numerator;
    private int denominator;

    public void setValues(int num, int den) {
        if (den == 0) {
            System.out.println("Помилка: знаменник не може дорівнювати 0!");
            this.denominator = 1;
            this.numerator = num;
        } else if (den < 0) {
            this.numerator = -num;
            this.denominator = -den;
        } else {
            this.numerator = num;
            this.denominator = den;
        }
    }
    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public void simplify() {
        int common = gcd(this.numerator, this.denominator);
        if (common != 0) {
            this.numerator /= common;
            this.denominator /= common;
        }
    }
    public double toDecimal() {
        return (double) this.numerator / this.denominator;
    }

    public void add(int otherNum, int otherDen) {
        int newNum = (this.numerator * otherDen) + (otherNum * this.denominator);
        int newDen = this.denominator * otherDen;

        this.numerator = newNum;
        this.denominator = newDen;

        simplify();
    }
    public void printFraction() {
        if (this.denominator == 1) {
            System.out.print(this.numerator);
        } else {
            System.out.print(this.numerator + "/" + this.denominator);
        }
    }
}
public class Task1Fraction {
    public static void main(String[] args) {
        System.out.println("==================== КАЛЬКУЛЯТОР ДРОБІВ ====================");
        Fraction f1 = new Fraction();
        f1.setValues(8, 12);
        System.out.print("Початковий дріб:               ");
        f1.printFraction();
        System.out.println();
        f1.simplify();
        System.out.print("Після скорочення:             ");
        f1.printFraction();
        System.out.println(" (десятковий вигляд: " + f1.toDecimal() + ")");
        System.out.println("-----------------------------------------------------------");
        System.out.println("Додаємо дріб:                 3/4");
        System.out.println("Формула обчислення:           2/3 + 3/4 = (2*4 + 3*3) / (3*4) = 17/12");
        f1.add(3, 4);
        System.out.print("Результат додавання:          ");
        f1.printFraction();
        System.out.println(" (десятковий вигляд: " + f1.toDecimal() + ")");
        System.out.println("===========================================================");
    }
}
