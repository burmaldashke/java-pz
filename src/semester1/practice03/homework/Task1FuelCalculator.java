package semester1.practice03.homework;

import java.util.Scanner;

public class Task1FuelCalculator {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== КАЛЬКУЛЯТОР ПОЇЗДКИ ТА ПАЛЬНОГО ===");
        System.out.println("Введіть відстань поїздки (км):  ");
        double distantce =scanner.nextDouble();
        System.out.println("Введіть середню витрату на 100 км (л):  ");
        double fuelConsumption = scanner.nextDouble();
        System.out.println("Оберіть тип пального (1 - А-95, 2 - Дизель, 3 - Газ):   ");
        int fuelType = scanner.nextInt();

        if (distantce <= 0 || fuelConsumption <= 0) {
            System.out.println("Помилка: Відстань та витрата пального повинні бути більше 0");
            scanner.close();
            return;
        }
        double pricePerLiter = 0;
        String fuelName = "";
        switch (fuelType){
            case 1 -> {
                pricePerLiter = 56.50;
                fuelName = "Бензин А-95";
            }
            case 2 -> {
                pricePerLiter = 52.80;
                fuelName = "Дизель";
            }
            case 3 -> {
                pricePerLiter = 29.40;
                fuelName = "Газ";
            }
            default -> {
                System.out.println("Помилка: Невідомий тип пального");
                scanner.close();
                return;
            }
        }
        double neededFuel = (distantce * fuelConsumption) / 100.0;
        double baseCost = neededFuel * pricePerLiter;
        double discount = (baseCost >= 2000.0) ? baseCost * 0.05 : 0.0;
        double finalCost = baseCost - discount;

        System.out.println("---------------- ЗВІТ ПОЇЗДКИ ----------------");
        System.out.println("Обране пальне:               "+fuelName+" ("+pricePerLiter+" грн/л");
        System.out.println("Необхідно пального:          "+neededFuel+" л");
        System.out.println("Базова вартість:             "+baseCost+" грн");
        System.out.println("Знижка АЗС (5%):             "+(baseCost - finalCost)+" грн ");
        System.out.println("----------------------------------------------");
        System.out.println("Підсумкова сума до сплати:   "+finalCost+"  грн");
        System.out.println("==============================================");





    }
}
