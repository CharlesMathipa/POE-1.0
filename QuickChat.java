import com.mycompany.quickchat.Login;
import com.mycompany.quickchat.Message;

void main() {
    try (Scanner scanner = new Scanner(System.in)) {
        Login login = new Login();
        boolean exitProgram = false;

        String name;
        String surname;
        String username = "";
        String password = "";
        String phoneNumber = "";
        boolean checkUserName;
        boolean checkPasswordComplexity;
        boolean checkCellPhoneNumber;

        while (!exitProgram) {
            IO.println("\n=== NEOAPP MENU ===");
            IO.println("1. Register");
            IO.println("2. Login");
            IO.println("3. Exit");
            IO.print("Please choose an option (1-3): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    IO.println("\n--- REGISTRATION ---");
                    IO.print("Enter your Name: ");
                    name = scanner.nextLine();
                    IO.print("Enter your Surname: ");
                    surname = scanner.nextLine();

                    checkUserName = false;
                    while (!checkUserName) {
                        IO.print("Enter a username: ");
                        username = scanner.nextLine();
                        if (login.checkUserName(username)) {
                            IO.println("Username successfully captured.");
                            checkUserName = true;
                        } else {
                            IO.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
                        }
                    }

                    checkPasswordComplexity = false;
                    while (!checkPasswordComplexity) {
                        IO.print("Enter a password: ");
                        password = scanner.nextLine();
                        if (login.checkPasswordComplexity(password)) {
                            IO.println("Password successfully captured.");
                            checkPasswordComplexity = true;
                        } else {
                            IO.println("Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number, and a special character.");
                        }
                    }

                    checkCellPhoneNumber = false;
                    while (!checkCellPhoneNumber) {
                        IO.print("Enter your phone number with the international South African code included (e.g., +27123456789): ");
                        phoneNumber = scanner.nextLine();
                        if (login.checkPhoneNumber(phoneNumber)) {
                            IO.println("Phone number successfully added.");
                            checkCellPhoneNumber = true;
                        } else {
                            IO.println("Cell phone number is incorrectly formatted or does not contain the international code.");
                        }
                    }

                    System.out.println(login.registerUser(username, password, name, surname, phoneNumber));
                }

                case "2" -> {
                    IO.println("\n--- LOGIN ---");

                    if (username.isEmpty() || password.isEmpty()) {
                        IO.println("No registered user found. Please register first.");
                        continue;
                    }

                    String loginUserName = "";
                    checkUserName = false;
                    while (!checkUserName) {
                        IO.print("Enter your username to login: ");
                        loginUserName = scanner.nextLine();
                        if (login.checkUserName(loginUserName)) {
                            IO.println("Username successfully captured.");
                            checkUserName = true;
                        } else {
                            IO.println("Username is not correctly formatted; please ensure that the username contains an underscore and is no more than five characters long.");
                        }
                    }

                    String loginPassword = "";
                    checkPasswordComplexity = false;
                    while (!checkPasswordComplexity) {
                        IO.print("Enter your password to login: ");
                        loginPassword = scanner.nextLine();
                        if (login.checkPasswordComplexity(loginPassword)) {
                            IO.println("Password successfully captured.");
                            checkPasswordComplexity = true;
                        } else {
                            IO.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
                        }
                    }

                    boolean isValidLogin = login.loginUser(loginUserName, loginPassword);
                    System.out.println(login.returnLoginStatus(isValidLogin));

                    if (isValidLogin) {
                        IO.println("Welcome to QuickChat.");

                        boolean quitChat = false;
                        while (!quitChat) {
                            IO.println("\nOption 1) Send Messages");
                            IO.println("Option 2) Show recently sent messages");
                            IO.println("Option 3) Quit");
                            IO.print("Choose an option: ");
                            String chatChoice = scanner.nextLine();

                            switch (chatChoice) {
                                case "1" -> {
                                    IO.print("How many messages do you wish to enter? ");
                                    int numMessages = Integer.parseInt(scanner.nextLine());
                                    int messagesProcessedInSession = 0;

                                    for (int i = 0; i < numMessages; i++) {
                                        IO.println("\n--- Message " + (i + 1) + " ---");

                                        IO.print("Enter recipient cell number: ");
                                        String recipient = scanner.nextLine();

                                        IO.print("Enter your message: ");
                                        String messageText = scanner.nextLine();

                                        Message newMsg = new Message(i, recipient, messageText);

                                        System.out.println(newMsg.checkMessageLength());
                                        if (messageText.length() > 250) {
                                            continue;
                                        }

                                        System.out.println(newMsg.checkRecipientCell());

                                        IO.println("Message ID generated: " + newMsg.getMessageID());

                                        IO.println("\nOptions:");
                                        IO.println("1 - Send Message");
                                        IO.println("2 - Disregard Message");
                                        IO.println("3 - Store Message to send later");
                                        IO.print("Choice: ");
                                        int actionChoice = Integer.parseInt(scanner.nextLine());

                                        System.out.println(newMsg.SentMessage(actionChoice));

                                        IO.println("\n" + newMsg.printMessages());
                                        messagesProcessedInSession++;
                                    }

                                    if (messagesProcessedInSession > 0) {
                                        Message temp = new Message(0, "", "");
                                        IO.println("Total number of messages accumulated: " + (temp.returnTotalMessagess() - 1));
                                    }
                                }
                                case "2" -> IO.println("Coming Soon.");
                                case "3" -> quitChat = true;
                                default -> IO.println("Invalid option.");
                            }
                        }
                    }
                }

                case "3" -> {
                    IO.println("Exiting NEOAPP... Goodbye!");
                    exitProgram = true;
                }

                default -> IO.println("Invalid selection. Please choose 1, 2, or 3.");
            }
        }
    }
}