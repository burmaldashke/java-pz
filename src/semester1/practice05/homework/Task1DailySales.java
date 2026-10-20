
package semester1.practice05.homework;

import java.util.Scanner;
import java.util.Locale;

public class Task1DailySales {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        String[] days = {"Понеділок", "Вівторок", "Середа", "Четвер", "П'ятниця", "Субота", "Неділя"};
        double[] sales = new double[7];

        System.out.println("=== ФІНАНСОВИЙ ЗВІТ КАВ'ЯРНІ ЗА ТИЖДЕНЬ ===");

        for (int i = 0; i < sales.length; i++) {
            System.out.print("Введіть виторг за " + days[i] + " (грн): ");
            sales[i] = scanner.nextDouble();
        }

        double total = 0;
        double maxSales = sales[0];
        double minSales = sales[0];
        int maxDay = 0;
        int minDay = 0;

        for (int i = 0; i < sales.length; i++) {
            total += sales[i];

            if (sales[i] > maxSales) {
                maxSales = sales[i];
                maxDay = i;
            }

            if (sales[i] < minSales) {
                minSales = sales[i];
                minDay = i;
            }
        }

        double average = total / sales.length;

        System.out.println();
        System.out.println("---------------- ПІДСУМКИ ----------------");
        System.out.println("Загальний тижневий виторг: " + String.format(Locale.US, "%.2f", total) + " грн");
        System.out.println("Середній виторг на день:    " + String.format(Locale.US, "%.2f", average) + " грн");
        System.out.println("Найкращий день:            " + days[maxDay] + " (" + String.format(Locale.US, "%.2f", maxSales) + " грн)");
        System.out.println("Найгірший день:            " + days[minDay] + " (" + String.format(Locale.US, "%.2f", minSales) + " грн)");

        System.out.println();
        System.out.println("Дні з виторгом вище середнього:");

        for (int i = 0; i < sales.length; i++) {
            if (sales[i] > average) {
                System.out.println("- " + days[i] + ": " + String.format(Locale.US, "%.2f", sales[i]) + " грн");
            }
        }

        System.out.println("==========================================");

        scanner.close();
    }
}
