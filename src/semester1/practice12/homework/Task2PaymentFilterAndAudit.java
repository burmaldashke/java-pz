package semester1.practice12.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

public class Task2PaymentFilterAndAudit {
    public static void main(String[] args) {
        HashSet<String> blockedShops = new HashSet<>(Arrays.asList("ScamShop", "FakeStore"));
        double maxLimit = 20000.0;

        PaymentProcessor processor = new PaymentProcessor(blockedShops, maxLimit);
        processor.addPayment(new CardPayment("P-01", "Rozetka", 4000.0));
        processor.addPayment(new CardPayment("P-01", "Rozetka", 4000.0));
        processor.addPayment(new TransferPayment("P-02", "Comfy", 10000.0));
        processor.addPayment(new CardPayment("P-03", "ScamShop", 5000.0));
        processor.addPayment(new CardPayment("P-04", "LuxWatch", 25000.0));
        processor.addPayment(new CardPayment("P-05", "Rozetka", 2000.0));
        processor.addPayment(new TransferPayment("P-06", "Allo", 5000.0));

        int incomingPayments = processor.getPaymentsCopy().size();

        System.out.println("================ 🛡️ БАНКІВСЬКИЙ ПЛАТІЖНИЙ ФІЛЬТР 🛡️ ================");
        HashSet<String> blockedCopy = processor.getBlockedShopsCopy();
        System.out.printf("Чорний список магазинів [%d]: %s%n", blockedCopy.size(), blockedCopy);
        System.out.printf(Locale.US, "Ліміт одного платежу: %.2f грн | Вхідних платежів: %d%n",
                maxLimit, incomingPayments);

        System.out.println("\n--- Очищення черги через Iterator.remove() ---");
        int removed = processor.cleanPayments();

        ArrayList<Payment> approvedPayments = processor.getPaymentsCopy();
        System.out.printf("%n--- Схвалені платежі (залишилось %d) ---%n", approvedPayments.size());

        for (Payment payment : approvedPayments) {
            System.out.printf(Locale.US,
                    "  ✅ %-5s | %-7s | Сума: %7.2f | Комісія: %6.2f | Нетто: %7.2f грн%n",
                    payment.getId(),
                    payment.getShopName(),
                    payment.getAmount(),
                    payment.calculateFee(),
                    payment.getNetAmount());
        }

        double gross = processor.getTotalGross();
        double fees = processor.getTotalFees();
        double net = gross - fees;
        HashSet<String> approvedShops = processor.getApprovedShops();

        System.out.println("\n--- Фінансовий підсумок ---");
        System.out.printf(Locale.US, "Видалено платежів: %d | Схвалено: %d%n", removed, approvedPayments.size());
        System.out.printf(Locale.US, "Загальна сума (брутто): %.2f грн%n", gross);
        System.out.printf(Locale.US, "Загальна комісія:       %.2f грн%n", fees);
        System.out.printf(Locale.US, "Чиста сума (нетто):     %.2f грн%n", net);
        System.out.printf("Унікальні магазини [%d]: %s%n", approvedShops.size(), approvedShops);
        System.out.println("====================================================================");
    }
}

abstract class Payment {
    private final String id;
    private final String shopName;
    private final double amount;

    public Payment(String id, String shopName, double amount) {
        this.id = Objects.requireNonNull(id);
        this.shopName = Objects.requireNonNull(shopName);

        if (amount <= 0) {
            throw new IllegalArgumentException("Сума платежу має бути більшою за нуль");
        }

        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public double getAmount() {
        return amount;
    }

    public abstract double calculateFee();

    public double getNetAmount() {
        return amount - calculateFee();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        Payment payment = (Payment) o;
        return id.equals(payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

final class CardPayment extends Payment {
    public CardPayment(String id, String shopName, double amount) {
        super(id, shopName, amount);
    }

    @Override
    public double calculateFee() {
        return getAmount() * 0.015;
    }
}

final class TransferPayment extends Payment {
    public TransferPayment(String id, String shopName, double amount) {
        super(id, shopName, amount);
    }

    @Override
    public double calculateFee() {
        return getAmount() * 0.02 + 20.0;
    }
}

final class PaymentProcessor {
    private final ArrayList<Payment> payments = new ArrayList<>();
    private final HashSet<String> blockedShops;
    private final double maxLimit;

    public PaymentProcessor(HashSet<String> blockedShops, double maxLimit) {
        this.blockedShops = new HashSet<>(Objects.requireNonNull(blockedShops));

        if (maxLimit <= 0) {
            throw new IllegalArgumentException("Ліміт має бути більшим за нуль");
        }

        this.maxLimit = maxLimit;
    }

    public void addPayment(Payment payment) {
        payments.add(Objects.requireNonNull(payment));
    }

    public HashSet<String> getBlockedShopsCopy() {
        return new HashSet<>(blockedShops);
    }

    public ArrayList<Payment> getPaymentsCopy() {
        return new ArrayList<>(payments);
    }

    public int cleanPayments() {
        HashSet<String> seenIds = new HashSet<>();
        Iterator<Payment> iterator = payments.iterator();
        int removed = 0;

        while (iterator.hasNext()) {
            Payment payment = iterator.next();
            String reason = null;

            if (!seenIds.add(payment.getId())) {
                reason = "ДУБЛІКАТ";
            } else if (blockedShops.contains(payment.getShopName())) {
                reason = "ЧОРНИЙ СПИСОК";
            } else if (payment.getAmount() > maxLimit) {
                reason = "ПОНАД ЛІМІТ";
            }

            if (reason != null) {
                System.out.printf(Locale.US, "  ❌ [%-15s] %s | %s (%.2f грн) — видалено!%n",
                        reason, payment.getId(), payment.getShopName(), payment.getAmount());
                iterator.remove();
                removed++;
            }
        }

        return removed;
    }

    public double getTotalGross() {
        double total = 0;

        for (Payment payment : payments) {
            total += payment.getAmount();
        }

        return total;
    }

    public double getTotalFees() {
        double total = 0;

        for (Payment payment : payments) {
            total += payment.calculateFee();
        }

        return total;
    }

    public HashSet<String> getApprovedShops() {
        HashSet<String> shops = new HashSet<>();

        for (Payment payment : payments) {
            shops.add(payment.getShopName());
        }

        return shops;
    }
}