package semester1.practice11.homework;

import java.util.ArrayList;
import java.util.List;

public class Task1WrapperAndAutoboxingMemory {

    public static void main(String[] args) {
        System.out.println("============== ДОСЛІДЖЕННЯ WRAPPER CLASSES ==============");

        int[] testValues = {-128, -50, 0, 127, 128, 500, 2026};
        inspectIntegerCache(testValues);

        demonstrateAutoboxingUnderTheHood();

        String[] rawPackets = {
                "  145 ", "-30", "ERR_404", "980", "", null, "2147483647", "-85"
        };

        List<Integer> validData = parseTelemetryStream(rawPackets);
        System.out.println("  Успішно розпізнаний список Integer: " + validData);

        printExtremesAndStats(validData);

        System.out.println("==========================================================");
    }

    public static void inspectIntegerCache(int[] testValues) {
        System.out.println();
        System.out.println("[1. INTEGER CACHE: порівняння == та equals()]");

        for (int val : testValues) {
            Integer first = Integer.valueOf(val);
            Integer second = Integer.valueOf(val);

            String source;
            if (val >= -128 && val <= 127) {
                source = "IntegerCache";
            } else {
                source = "Новий об'єкт у Heap";
            }

            System.out.println("  Значення: " + val
                    + " | (a == b): " + (first == second)
                    + " | a.equals(b): " + first.equals(second)
                    + " | Джерело: " + source);
        }
    }

    public static void demonstrateAutoboxingUnderTheHood() {
        System.out.println();
        System.out.println("[2. AUTOBOXING / UNBOXING ТА ЗАХИСТ ВІД NULL]");

        Integer number = 42;
        int primitiveNumber = number;

        Integer explicitNumber = Integer.valueOf(42);
        int explicitPrimitiveNumber = explicitNumber.intValue();

        Double decimal = 3.14;
        double primitiveDecimal = decimal;

        Double explicitDecimal = Double.valueOf(3.14);
        double explicitPrimitiveDecimal = explicitDecimal.doubleValue();

        Boolean flag = true;
        boolean primitiveFlag = flag;

        Boolean explicitFlag = Boolean.valueOf(true);
        boolean explicitPrimitiveFlag = explicitFlag.booleanValue();

        System.out.println("  Integer: " + primitiveNumber
                + " | valueOf / intValue: " + explicitPrimitiveNumber);
        System.out.println("  Double: " + primitiveDecimal
                + " | valueOf / doubleValue: " + explicitPrimitiveDecimal);
        System.out.println("  Boolean: " + primitiveFlag
                + " | valueOf / booleanValue: " + explicitPrimitiveFlag);

        List<Integer> sensorData = new ArrayList<>();
        sensorData.add(45);
        sensorData.add(null);
        sensorData.add(-12);
        sensorData.add(88);
        sensorData.add(null);
        sensorData.add(102);

        int sum = 0;
        String skippedIndexes = "";

        System.out.println("  Вхідний список: " + sensorData);

        for (int i = 0; i < sensorData.size(); i++) {
            Integer value = sensorData.get(i);

            if (value == null) {
                if (!skippedIndexes.isEmpty()) {
                    skippedIndexes += " та ";
                }
                skippedIndexes += i;
            } else {
                sum += value.intValue();
            }
        }

        System.out.println("  Пропущено null на індексах: " + skippedIndexes);
        System.out.println("  Сума валідних значень: " + sum);
    }

    public static List<Integer> parseTelemetryStream(String[] rawPackets) {
        System.out.println();
        System.out.println("[3. ПАРСИНГ РЯДКІВ ТА ПОШУК ЕКСТРЕМУМІВ]");

        List<Integer> validData = new ArrayList<>();

        for (String packet : rawPackets) {
            if (packet == null || packet.trim().isEmpty()) {
                System.out.println("  Порожній або null-пакет відхилено");
                continue;
            }

            String cleanPacket = packet.trim();

            try {
                int value = Integer.parseInt(cleanPacket);
                validData.add(value);
            } catch (NumberFormatException e) {
                System.out.println("  Пакет '" + cleanPacket
                        + "' відхилено (NumberFormatException)");
            }
        }

        return validData;
    }

    public static void printExtremesAndStats(List<Integer> validData) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        long sum = 0;

        System.out.println("  Початкові межі: min = " + min + ", max = " + max);

        if (validData.isEmpty()) {
            System.out.println("  Немає даних для пошуку мінімуму та максимуму");
            return;
        }

        for (Integer value : validData) {
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
            sum += value;
        }

        System.out.println("  Мінімум: " + min + " | Максимум: " + max);
        System.out.println("  Сума даних: " + sum);

        List<Integer> removeByIndex = new ArrayList<>(validData);
        Integer removed = removeByIndex.remove(0);
        System.out.println("  remove(0) видалив елемент за індексом: "
                + removed + " | список: " + removeByIndex);

        int val = -30;
        List<Integer> removeByValue = new ArrayList<>(validData);
        removeByValue.remove(Integer.valueOf(val));
        System.out.println("  remove(Integer.valueOf(" + val
                + ")) видалив значення зі списку: " + removeByValue);
    }
}