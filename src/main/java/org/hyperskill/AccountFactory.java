package org.hyperskill;

import java.util.Random;

public class AccountFactory {

    public static void createAccount() {
        Random generator = new Random();
        final int IIN = 400000;
        int low = 100000000;
        int high = 1000000000;

        int accountNumber;
        int pin;
        String cardNumber;
        String pinFormatted;


        accountNumber = generator.nextInt(high - low) + low;
        cardNumber = "" + IIN + accountNumber + Luhn.luhnAlgo(accountNumber);
        pin = generator.nextInt(10000);
        pinFormatted = String.format("%04d", pin);


        JDBC addAcc = new JDBC();
        addAcc.addAccountToDatabase(cardNumber, pinFormatted);

        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(pinFormatted);
    }


    public static void logIntoAccount() {
        System.out.println("Enter your card number:");
        String cardNumber = Main.scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pin = Main.scanner.nextLine();

        JDBC jdbc = new JDBC();

        if (jdbc.loginVerify(cardNumber, pin)) {
            System.out.println("You have successfully logged in!");
            Menu.customerMenu(cardNumber);
        } else {
            System.out.println("Wrong card number or PIN!");
        }

    }
}

