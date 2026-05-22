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

/**
 *
 * @author ST10511987
 */

public class MessageIT {
    private Message message;
    private static final String TEST_RECIPIENT = "+27123456789";
    private static final String TEST_MESSAGE = "Hello world test message";

    @BeforeEach


    @Test
    void testGenerateRandomID() {
        // ID should be 10 digits
        String id = message.getMessageID();
        assertEquals(10, id.length());
        assertTrue(id.matches("\\d{10}"));
    }

    @Test
    void testCheckMessageID() {
        assertTrue(message.checkMessageID()); // 10 digits is <=10
        // We cannot easily create a message with longer ID because generateRandomID always makes 10 digits.
        // But method exists.
    }

    @Test
    void testCheckRecipientCellValid() {
        Message validMsg = new Message(1, "+27123456789", "test");
        assertEquals("Cell phone number successfully captured.", validMsg.checkRecipientCell());
        
        Message withPlusAndLong = new Message(1, "+1234567890", "test");
        assertEquals("Cell phone number successfully captured.", withPlusAndLong.checkRecipientCell());
    }

    @Test
    void testCheckRecipientCellInvalid() {
        Message nullRecipient = new Message(1, null, "test");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", nullRecipient.checkRecipientCell());
        
        Message noPlus = new Message(1, "27123456789", "test");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", noPlus.checkRecipientCell());
        
        Message tooShort = new Message(1, "+27", "test");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", tooShort.checkRecipientCell());
    }

    @Test
    void testCheckMessageLengthValid() {
        String shortMsg = "This is a short message";
        Message m = new Message(1, TEST_RECIPIENT, shortMsg);
        assertEquals("Message ready to send.", m.checkMessageLength());
    }

    @Test
    void testCheckMessageLengthExceeds() {
        String longMsg = "a".repeat(251);
        Message m = new Message(1, TEST_RECIPIENT, longMsg);
        assertEquals("Message exceeds 250 characters by 1; please reduce the size.", m.checkMessageLength());
        
        String longer = "b".repeat(300);
        Message m2 = new Message(1, TEST_RECIPIENT, longer);
        assertEquals("Message exceeds 250 characters by 50; please reduce the size.", m2.checkMessageLength());
    }

    @Test
    void testCreateMessageHash() {
        // For message "Hello world test message", firstWord="Hello", lastWord="message"
        String idPrefix = message.getMessageID().substring(0, 2);
        String expectedHash = (idPrefix + ":1:Hello" + "message").toUpperCase();
        assertEquals(expectedHash, message.createMessageHash());
    }

    @Test
    void testCreateMessageHashSingleWord() {
        Message single = new Message(2, TEST_RECIPIENT, "Hello");
        String idPrefix = single.getMessageID().substring(0, 2);
        String expected = (idPrefix + ":2:HelloHello").toUpperCase();
        assertEquals(expected, single.createMessageHash());
    }

    @Test
    void testCreateMessageHashWithPunctuation() {
        Message punct = new Message(3, TEST_RECIPIENT, "Hello, world!");
        String idPrefix = punct.getMessageID().substring(0, 2);
        // firstWord becomes "Hello", lastWord becomes "world"
        String expected = (idPrefix + ":3:Hello" + "world").toUpperCase();
        assertEquals(expected, punct.createMessageHash());
    }

    @Test
    void testSentMessageSend() {
        assertEquals("Message successfully sent.", message.SentMessage(1));
    }

    @Test
    void testSentMessageDisregard() {
        assertEquals("Press 0 to delete the message.", message.SentMessage(2));
    }

 

    @Test
    void testSentMessageInvalid() {
        assertEquals("Invalid choice.", message.SentMessage(99));
        assertEquals("Invalid choice.", message.SentMessage(0));
    }

  



    @Test
    void testPrintMessages() {
        String printed = message.printMessages();
        assertTrue(printed.contains("Message ID: " + message.getMessageID()));
        assertTrue(printed.contains("Message Hash: " + message.getMessageHash()));
        assertTrue(printed.contains("Recipient: " + TEST_RECIPIENT));
        assertTrue(printed.contains("Message: " + TEST_MESSAGE));
    }

    @Test
    void testReturnTotalMessagess() {
        assertEquals(1, message.returnTotalMessagess());
        Message second = new Message(2, TEST_RECIPIENT, "Another");
        assertEquals(2, second.returnTotalMessagess());
        Message third = new Message(3, TEST_RECIPIENT, "Third");
        assertEquals(3, third.returnTotalMessagess());
    }

    @Test
    void testGetMessageID() {
        assertNotNull(message.getMessageID());
        assertEquals(10, message.getMessageID().length());
    }
}