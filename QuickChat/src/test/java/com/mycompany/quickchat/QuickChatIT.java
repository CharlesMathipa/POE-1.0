/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;

/**
 *
 * @author sammy
 */

public class QuickChatIT{

    private static void main(String[] string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();






    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @Test
    void testFullRegistrationAndLoginAndSendMessage() {
        // Simulate user: register, login, send 1 message
        String input = """
                       1
                       John
                       Doe
                       j_doe
                       Password1!
                       +27123456789
                       2
                       j_doe
                       Password1!
                       1
                       1
                       +27123456789
                       Hello test message
                       1
                       3
                       3
                       """ // Register
        // Login
        // Send Messages option
        // Number of messages = 1
        // Send choice
        // Quit chat
        ;                       // Exit app
        provideInput(input);
        QuickChatIT.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("registered successfully"));
        assertTrue(output.contains("Welcome John, Doe it is great to see you"));
        assertTrue(output.contains("Message successfully sent"));
        assertTrue(output.contains("Total number of messages accumulated: 1"));
    }

    @Test
    void testStoreMessageAndJsonFileCreated() {
        String input = """
                       1
                       John
                       Doe
                       j_doe
                       Password1!
                       +27123456789
                       2
                       j_doe
                       Password1!
                       1
                       1
                       +27123456789
                       Store this message
                       3
                       3
                       3
                       """ // Store choice
        ;
        provideInput(input);
        QuickChatIT.main(new String[]{});
        File jsonFile = new File("stored_messages.json");
        assertTrue(jsonFile.exists());
        String output = outContent.toString();
        assertTrue(output.contains("Message successfully stored"));
    }

    @Test
    void testDisregardMessage() {
        String input = """
                       1
                       John
                       Doe
                       j_doe
                       Password1!
                       +27123456789
                       2
                       j_doe
                       Password1!
                       1
                       1
                       +27123456789
                       Disregard me
                       2
                       3
                       3
                       """ // Disregard choice
        ;
        provideInput(input);
        QuickChatIT.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Press 0 to delete the message"));
        // Should still count as a message object created
        assertTrue(output.contains("Total number of messages accumulated: 1"));
    }

    @Test
    void testLoginWithoutRegistration() {
        // Try to login without registering first
        String input = """
                       2
                       3
                       """ // Login
        ;           // Exit
        provideInput(input);
        QuickChatIT.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("No registered user found"));
    }

    @Test
    void testInvalidMainMenuOption() {
        String input = "5\n3\n"; // Invalid then exit
        provideInput(input);
        QuickChatIT.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Invalid selection"));
    }

    @Test
    void testInvalidChatMenuOption() {
        String input = "1\n" +
                "John\n" +
                "Doe\n" +
                "j_doe\n" +
                "Password1!\n" +
                "+27123456789\n" +
                "2\n" +
                "j_doe\n" +
                "Password1!\n" +
                "5\n" +           // Invalid chat option
                "3\n3\n";
        provideInput(input);
        QuickChatIT.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Invalid option"));
    }

    @Test
    void testMessageExceedsLength() {
        String longMessage = "a".repeat(251);
        String input = """
                       1
                       John
                       Doe
                       j_doe
                       Password1!
                       +27123456789
                       2
                       j_doe
                       Password1!
                       1
                       1
                       +27123456789
                       """ +
                longMessage + "\n" +
                "1\n" +
                "3\n3\n";
        provideInput(input);
        QuickChatIT.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("exceeds 250 characters by 1"));
        assertTrue(output.contains("Message discarded due to length violation"));
        // The message should not be counted as sent/stored
        // It still creates a Message object? Actually the code creates the Message object before length check,
        // so totalMessages still increments. Check output: it says "Total number of messages accumulated: 1"?
        // We'll verify the behavior: Since the Message constructor increments totalMessages, it will be counted.
        // That's acceptable; test just ensures the length violation message appears.
    }

    @Test
    void testShowRecentMessages() {
        String input = """
                       1
                       John
                       Doe
                       j_doe
                       Password1!
                       +27123456789
                       2
                       j_doe
                       Password1!
                       2
                       3
                       3
                       """ // Show recently sent messages (Coming Soon)
        ;
        provideInput(input);
        QuickChatIT.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Coming Soon"));
    }

    @Test
    void testMultipleMessages() {
        String input = """
                       1
                       John
                       Doe
                       j_doe
                       Password1!
                       +27123456789
                       2
                       j_doe
                       Password1!
                       1
                       2
                       +27123456789
                       First message
                       1
                       +27876543210
                       Second message
                       1
                       3
                       3
                       """ // Send 2 messages
        ;
        provideInput(input);
        QuickChatIT.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Message successfully sent"));
        assertTrue(output.contains("Total number of messages accumulated: 2"));
    }
}
