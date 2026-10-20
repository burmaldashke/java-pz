
package semester1.practice05.homework;

public class Task2WarehouseInventory {
    public static void main(String[] args) {
        String[] items = {"Ноутбук", "Смартфон", "Бездротові навушники", "Механічна клавіатура", "Монітор 27\""};
        int[] quantities = {8, 3, 25, 4, 12};
        double[] prices = {28500.0, 19200.0, 1850.0, 3100.0, 9400.0};

        double[] totalValues = new double[items.length];
        double total = 0;
        double maxValue = 0;
        int maxIndex = 0;

        System.out.println("=================== ЗВІТ СКЛАДУ ЕЛЕКТРОНІКИ ===================");
        System.out.println("№ | Назва товару | Кількість | Ціна (грн) | Загальна вартість");
        System.out.println("---------------------------------------------------------------");

        for (int i = 0; i < items.length; i++) {
            totalValues[i] = quantities[i] * prices[i];
            total += totalValues[i];

            if (totalValues[i] > maxValue) {
                maxValue = totalValues[i];
                maxIndex = i;
            }
            System.out.println((i + 1) + " | " + items[i] + " | " + quantities[i]
                    + " шт. | " + prices[i] + " грн | " + totalValues[i] + " грн");
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println("Загальна вартість складу: " + total + " грн");
        System.out.println("Найбільший капітал у товарі: " + items[maxIndex] + " (" + maxValue + " грн)");
        System.out.println("ДЕФІЦИТНІ ПОЗИЦІЇ (менше 5 шт.):");

        for (int i = 0; i < items.length; i++) {
            if (quantities[i] < 5) {
                System.out.println("- " + items[i] + " (залишилось: " + quantities[i]
                        + " шт.) -> Терміново замовити постачальнику!");
            }
        }
        System.out.println("===============================================================");
    }
}
