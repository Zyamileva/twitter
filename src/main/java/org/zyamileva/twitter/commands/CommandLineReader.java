package org.zyamileva.twitter.commands;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class CommandLineReader {

    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String commandMessage) {
        int commandNumber = Integer.MIN_VALUE;
        while (commandNumber == Integer.MIN_VALUE) {
            System.out.println(commandMessage);
            try {
                commandNumber = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException ex) {
                System.err.println("Input invalid. Only numbers allowed.");
                scanner.next();
            }
        }
        return commandNumber;
    }

    public static UUID readUUID(String commandMessage) {
        UUID id = null;
        while (id == null) {
            System.out.println(commandMessage);
            String idAsString = scanner.nextLine();
            try {
                id = UUID.fromString(idAsString);
            } catch (IllegalArgumentException ex) {
                System.err.println("Invalid id: " + idAsString);
            }
        }
        return id;
    }

    public static String readLine(String commandMessage) {
        System.out.println(commandMessage);
        return scanner.nextLine();
    }
}