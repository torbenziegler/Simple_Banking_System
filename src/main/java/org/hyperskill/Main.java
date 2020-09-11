package org.hyperskill;


import java.util.Scanner;

public class Main {

    static final Scanner scanner = new Scanner(System.in);
    static boolean exit = false;

    static String path;

    public static void main(String[] args) {
        if (args.length > 1) {
            String url = "jdbc:sqlite:" + args[1]; // for creating DB
            path = url;
            JDBC.connect(url);

            Menu.mainMenu();

            while (!exit) {
                Menu.mainMenu();
            }
        } else {
            System.exit(1);
        }


    }


}