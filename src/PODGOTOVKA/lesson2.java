package PODGOTOVKA;
 public class lesson2{
     public static void main(String[] args){
         double usdAmount = 250.0;
         final double EXCHARGE_RATE = 41.25;
         final double COMMISION_PERCENT = 1.5;  //1.5%
         double totalUAH = usdAmount * EXCHARGE_RATE;
         double Summa = totalUAH * (COMMISION_PERCENT/100);
         double Chista = totalUAH - Summa;
         long Vudacha = Math.round(Chista);

         System.out.println("================   КВИТАНЦІЯ ОБМІНУ    ================");
         System.out.println("Сума до обміну:                 "+usdAmount+" USD");
         System.out.println("Курс валют:                     "+EXCHARGE_RATE+" UAH/USD");
         System.out.println("Нараховано до комісії:          "+totalUAH+" UAH");
         System.out.println("Комісія банку (1.5%):           "+Summa+" UAH");
         System.out.println("-------------------------------------------------------");
         System.out.println("Чиста сума до видачі:           "+Chista+" UAH");
         System.out.println("До видачі готівкою:             "+Vudacha+" UAH");
     }
 }