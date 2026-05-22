/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;

/**
 *
 * @author sammy
 */
import java.util.Scanner;

public class QuickChat{

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Login login = new Login();
            boolean exitProgram = false;

            String name = "";
            String surname = "";
            String username = "";
            String password = "";
            String phoneNumber = "";

            while (!exitProgram) {
                System.out.println("\n=== Quickchat MENU ===");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Please choose an option (1-3): ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {
                        System.out.println("\n--- REGISTRATION ---");
                        System.out.print("Enter your Name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter your Surname: ");
                        surname = scanner.nextLine();

                        boolean checkUserName = false;
                        while (!checkUserName) {
                            System.out.print("Enter a username: ");
                            username = scanner.nextLine();
                            if (login.checkUserName(username)) {
                                System.out.println("Username successfully captured.");
                                checkUserName = true;
                            } else {
                                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
                            }
                        }

                        boolean checkPasswordComplexity = false;
                        while (!checkPasswordComplexity) {
                            System.out.print("Enter a password: ");
                            password = scanner.nextLine();
                            if (login.checkPasswordComplexity(password)) {
                                System.out.println("Password successfully captured.");
                                checkPasswordComplexity = true;
                            } else {
                                System.out.println("Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number, and a special character.");
                            }
                        }

                        boolean checkCellPhoneNumber = false;
                        while (!checkCellPhoneNumber) {
                            System.out.print("Enter your phone number with the international South African code included (e.g., +27123456789): ");
                            phoneNumber = scanner.nextLine();
                            if (login.checkPhoneNumber(phoneNumber)) {
                                System.out.println("Phone number successfully added.");
                                checkCellPhoneNumber = true;
                            } else {
                                System.out.println("Cell phone number is incorrectly formatted or does not contain the international code.");
                            }
                        }

                        System.out.println(login.registerUser(username, password, name, surname, phoneNumber));
                    }

                    case "2" -> {
                        System.out.println("\n--- LOGIN ---");

                        if (username.isEmpty() || password.isEmpty()) {
                            System.out.println("No registered user found. Please register first.");
                            continue;
                        }

                        System.out.print("Enter your username to login: ");
                        String loginUserName = scanner.nextLine();

                        System.out.print("Enter your password to login: ");
                        String loginPassword = scanner.nextLine();

                        boolean isValidLogin = login.loginUser(loginUserName, loginPassword);
                        System.out.println(login.returnLoginStatus(isValidLogin));

                        if (isValidLogin) {
                            System.out.println("Welcome to QuickChat.");

                            boolean quitChat = false;
                            while (!quitChat) {
                                System.out.println("\nOption 1) Send Messages");
                                System.out.println("Option 2) Show recently sent messages");
                                System.out.println("Option 3) Quit");
                                System.out.print("Choose an option: ");
                                String chatChoice = scanner.nextLine();

                                switch (chatChoice) {
                                    case "1" -> {
                                        System.out.print("How many messages do you wish to enter? ");
                                        int numMessages;
                                        try {
                                            numMessages = Integer.parseInt(scanner.nextLine());
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid number. Please enter a whole number.");
                                            continue;
                                        }

                                        for (int i = 0; i < numMessages; i++) {
                                            System.out.println("\n--- Message " + (i + 1) + " ---");

                                            System.out.print("Enter recipient cell number (must start with + and be at least 10 digits): ");
                                            String recipient = scanner.nextLine();

                                            System.out.print("Enter your message: ");
                                            String messageText = scanner.nextLine();

                                            // Create Message object with message number = i
                                            Message newMsg = new Message(i, recipient, messageText);

                                            System.out.println(newMsg.checkMessageLength());
                                            if (messageText.length() > 250) {
                                                System.out.println("Message discarded due to length violation.");
                                                continue;
                                            }

                                            System.out.println(newMsg.checkRecipientCell());

                                            System.out.println("Message ID generated: " + newMsg.getMessageID());

                                            System.out.println("\nOptions:");
                                            System.out.println("1 - Send Message");
                                            System.out.println("2 - Disregard Message");
                                            System.out.println("3 - Store Message to send later");
                                            System.out.print("Choice: ");
                                            int actionChoice;
                                            try {
                                                actionChoice = Integer.parseInt(scanner.nextLine());
                                            } catch (NumberFormatException e) {
                                                System.out.println("Invalid choice. Message discarded.");
                                                continue;
                                            }

                                            System.out.println(newMsg.SentMessage(actionChoice));

                                            System.out.println("\n" + newMsg.printMessages());
                                        }

                                        // Display total number of Message objects created so far
                                        Message tempMsg = new Message(0, "", "");
                                        System.out.println("Total number of messages accumulated: " + (tempMsg.returnTotalMessagess()));
                                    }
                                    case "2" -> System.out.println("Coming Soon.");
                                    case "3" -> quitChat = true;
                                    default -> System.out.println("Invalid option.");
                                }
                            }
                        }
                    }

                    case "3" -> {
                        System.out.println("Exiting QuickChat... Goodbye!");
                        exitProgram = true;
                    }

                    default -> System.out.println("Invalid selection. Please choose 1, 2, or 3.");
                }
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}