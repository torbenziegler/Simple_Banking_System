package org.hyperskill;


public class Luhn {

    static int luhnAlgo(long number) {
        String str = "" + 400000 + number;
        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        for (int i = 0; i < ints.length; i += 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;

        for (int anInt : ints) {
            sum += anInt;
        }
        if (sum % 10 == 0) {
            return 0;
        } else return 10 - (sum % 10);
    }

    public static boolean verifyChecksum(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}

