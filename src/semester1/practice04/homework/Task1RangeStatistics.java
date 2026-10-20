
package semester1.practice04.homework;

import java.util.Scanner;

public class Task1RangeStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== АНАЛІЗАТОР ДІАПАЗОНУ ЧИСЕЛ ===");
        System.out.print("Введіть число N: ");
        int N;

        do {
            N = scanner.nextInt();
            if (N <= 0) {
                System.out.println("Помилка: N має бути більшим за 0!");
                System.out.print("Введіть число N: ");
            }
        } while (N <= 0);

        int totalSum = 0;
        int evenSum = 0;
        int oddSum = 0;
        int evenCount = 0;
        int oddCount = 0;

        for (int i = 1; i <= N; i++) {
            totalSum += i;

            if (i % 2 == 0) {
                evenSum += i;
                evenCount++;
            } else {
                oddSum += i;
                oddCount++;
            }
        }

        double average = (double) totalSum / N;

        System.out.println();
        System.out.println("--- РЕЗУЛЬТАТИ ДЛЯ ДІАПАЗОНУ [1 .. " + N + "] ---");
        System.out.println("Загальна сума чисел:       " + totalSum);
        System.out.println("Сума парних чисел:         " + evenSum + " (кількість: " + evenCount + ")");
        System.out.println("Сума непарних чисел:       " + oddSum + " (кількість: " + oddCount + ")");
        System.out.println("Середнє арифметичне:       " + average);
        System.out.println("=========================================");

        scanner.close();
    }
}
