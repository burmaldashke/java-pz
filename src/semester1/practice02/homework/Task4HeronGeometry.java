package semester1.practice02.homework;

public class Task4HeronGeometry {
    public static void main(String[] args){
        double a = 7.0;
        double b = 8.0;
        double c = 9.0;

        double P = a + b + c;

        double p = P / 2.0;

        double S_triangle = Math.sqrt(p * (p - a) * (p - b) * (p - c));

        double r = S_triangle / p;
        double S_inscribed = Math.PI * Math.pow(r, 2);

        double R = (a * b * c) / (4.0 * S_triangle);
        double S_circumscribed = Math.PI * Math.pow(R, 2);

        System.out.println("================ ГЕОМЕТРИЧНИЙ ЗВІТ ===============");
        System.out.println("Сторони: a = " +a+", b = " +b+ ", c = " +c+ " ");
        System.out.println("Периметр (P):               "+P+" ");
        System.out.println("Напівпериметр (p)           "+p+" ");
        System.out.println("Площа трикутника (S)        "+S_triangle+" кв. од.");
        System.out.println("Радіус вписаного кола (r):  "+r+" ");
        System.out.println("Площа вписаного круга:      "+S_inscribed+" кв. од.");
        System.out.println("Радіус описаного кола (R):  "+R+" ");
        System.out.println("Площа описаного круга:      "+S_circumscribed+" кв.од.");
        System.out.println("==================================================");




    }
}
