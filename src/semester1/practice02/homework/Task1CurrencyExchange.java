package semester1.practice02.homework;

public class Task1CurrencyExchange {
    public static void main(String[] args){
        //ВХІДНІ ДАНІ
        double usdAmount = 250.0;
        final double EXCHANGE_RATE = 41.25;
        final double COMMISSION_PERCENT = 1.5;

        double totalUah = usdAmount * EXCHANGE_RATE;
        double commissionUah = totalUah * (COMMISSION_PERCENT / 100.0);
        double netUah = totalUah - commissionUah;
        long cashToIssue = Math.round(netUah);

        System.out.println("================ КВИТАНЦІЯ ОБМІНУ ================");
        System.out.println("Сума до обміну:             " +usdAmount + " USD");
        System.out.println("Курс валют:                 " +EXCHANGE_RATE + " UAH/USD");
        System.out.println("Нараховано до комісії:      " +totalUah+ " UAH");
        System.out.println("Комісія банку (1.5%):       " +commissionUah+ " UAH");
        System.out.println("--------------------------------------------------");
        System.out.println("Чиста сума до видачі:       " +netUah+ " UAH");
        System.out.println("До видачі готівкою:         " +cashToIssue+ " UAH");
        System.out.println("==================================================");
    }
}
