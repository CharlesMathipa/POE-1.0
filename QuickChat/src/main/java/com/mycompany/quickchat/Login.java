/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author sammy
 */


import java.util.regex.Pattern;

public class Login {
    private String storedUsername;
    private String storedPassword;
    private String name;
    private String surname;
    private String phoneNumber;

    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        String regex = "^\\+27\\d{9}$";
        return Pattern.matches(regex, phoneNumber);
    }

    public String registerUser(String username, String password, String name, String surname, String phoneNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number, and a special character";
        }
        if (!checkPhoneNumber(phoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        this.storedUsername = username;
        this.storedPassword = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        
        return "The two above conditions have been met and the user has been registered successfully.";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (this.storedUsername == null || this.storedPassword == null) return false;
        return enteredUsername.equals(this.storedUsername) && enteredPassword.equals(this.storedPassword);
    }

    public String returnLoginStatus(boolean isLoginSuccessful) {
        if (isLoginSuccessful) {
            return "Welcome " + name + ", " + surname + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
