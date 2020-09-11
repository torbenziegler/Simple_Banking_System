package org.hyperskill;

public class Menu {

    public static void mainMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");

        switch (Main.scanner.nextLine()) {
            case "1":
                AccountFactory.createAccount();
                break;
            case "2":
                AccountFactory.logIntoAccount();
                break;
            case "0":
                System.out.println("Bye!");
                Main.exit = true;
                break;
        }
    }


    public static void customerMenu(String cardNumber) {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");

        JDBC jdbc = new JDBC();

        switch (Main.scanner.nextLine()) {
            case "1":
                System.out.println("Balance: " + jdbc.getBalance(cardNumber));
                customerMenu(cardNumber);
                break;
            case "2":
                jdbc.updateBalance(setBalanceUpdate(), cardNumber);
                customerMenu(cardNumber);
                break;
            case "3":
                transferDialog(cardNumber);
                customerMenu(cardNumber);
                break;
            case "4":
                jdbc.deleteAccount(cardNumber);
                System.out.println("The account has been closed!");
                mainMenu();
                break;
            case "5":
                System.out.println("You have successfully logged out!");
                mainMenu();
                break;
            case "0":
                System.out.println("Bye!");
                Main.exit = true;
                break;
        }
    }

    private static int setBalanceUpdate() {
        System.out.println("Enter income:");
        String income = Main.scanner.nextLine();
        int incomeConv = 0;
        try {
            incomeConv = Integer.parseInt(income);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        if (incomeConv < 0) {
            incomeConv = 0;
        }

        return incomeConv;
    }

    private static void transferDialog(String sourceCard) {
        JDBC transfer = new JDBC();

        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String targetCard = Main.scanner.nextLine();

        try {
            if (sourceCard.equals(targetCard)) {
                System.out.println("You can't transfer money to the same account!");
            } else if (!Luhn.verifyChecksum(targetCard) || targetCard.length() < 16) {
                System.out.println("Probably you made a mistake in the card number. Please try again!");
                Menu.customerMenu(sourceCard);
            } else if (!transfer.checkCardNumber(targetCard)) {
                System.out.println("Such a card does not exist.");
            } else {
                System.out.println("Enter how much money you want to transfer:");
                int amount = Integer.parseInt(Main.scanner.nextLine());
                if (amount > transfer.getBalance(sourceCard)) {
                    System.out.println("Not enough money!");
                } else {
                    transfer.makeTransfer(sourceCard, targetCard, amount);
                }
            }
        } catch (NumberFormatException ignored) {
        }

    }
}