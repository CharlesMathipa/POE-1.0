package com.mycompany.quickchat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public final class Message {
    
    private final String messageID;
    private final int numMessagesSent;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    
    private static int totalMessages = 0;

    public Message(int numMessagesSent, String recipient, String messageText) {
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateRandomID();
        this.messageHash = createMessageHash();
        totalMessages++;
    }

    private String generateRandomID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    public boolean checkMessageID() {
        return this.messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.startsWith("+") && recipient.length() >= 10) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    public String createMessageHash() {
        String firstTwoID = messageID.substring(0, 2);
        
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].replaceAll("[^a-zA-Z]", "") : "";
        String lastWord = words.length > 0 ? words[words.length - 1].replaceAll("[^a-zA-Z]", "") : "";
        
        return (firstTwoID + ":" + numMessagesSent + ":" + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage(int choice) {
        return switch (choice) {
            case 1 -> "Message successfully sent.";
            case 2 -> "Press 0 to delete the message.";
            case 3 -> {
                storeMessage();
                yield "Message successfully stored.";
            }
            default -> "Invalid choice.";
        };
    }

    public String printMessages() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText + "\n";
    }

    public int returnTotalMessagess() {
        return totalMessages;
    }

    public void storeMessage() {
        String jsonStr = "{\n" +
                         "  \"messageID\": \"" + messageID + "\",\n" +
                         "  \"messageHash\": \"" + messageHash + "\",\n" +
                         "  \"recipient\": \"" + recipient + "\",\n" +
                         "  \"messageText\": \"" + messageText + "\"\n" +
                         "}\n";
        try (FileWriter file = new FileWriter("stored_messages.json", true)) {
            file.write(jsonStr);
        } catch (IOException e) {
            System.out.println("Error writing to JSON file.");
        }
    }

    public String getMessageID() {
        return messageID;
    }
}