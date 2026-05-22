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
 * @author 
 */
public class LoginIT {
    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
    }

    @Test
    void testCheckUserNameValid() {
        assertTrue(login.checkUserName("j_doe"));
        assertTrue(login.checkUserName("a_b"));
        assertTrue(login.checkUserName("_"));
        assertTrue(login.checkUserName("abc_"));
    }

    @Test
    void testCheckUserNameInvalid() {
        assertFalse(login.checkUserName(null));
        assertFalse(login.checkUserName("johndoe"));      // no underscore
        assertFalse(login.checkUserName("john_doe"));     // too long (8 chars)
        assertFalse(login.checkUserName("abcdefgh"));     // no underscore, too long
        assertFalse(login.checkUserName(""));             // empty
    }

    @Test
    void testCheckPasswordComplexityValid() {
        assertTrue(login.checkPasswordComplexity("Password1!"));
        assertTrue(login.checkPasswordComplexity("Abc123!@#"));
        assertTrue(login.checkPasswordComplexity("Zz9$abcd"));
    }

    @Test
    void testCheckPasswordComplexityInvalid() {
        assertFalse(login.checkPasswordComplexity(null));
        assertFalse(login.checkPasswordComplexity("short"));               // <8 chars
        assertFalse(login.checkPasswordComplexity("nouppercase1!"));       // no uppercase
        assertFalse(login.checkPasswordComplexity("NODIGIT!"));            // no digit
        assertFalse(login.checkPasswordComplexity("NoSpecial1"));          // no special char
        assertFalse(login.checkPasswordComplexity("Password1"));           // no special char
    }

    @Test
    void testCheckPhoneNumberValid() {
        assertTrue(login.checkPhoneNumber("+27123456789"));
        assertTrue(login.checkPhoneNumber("+27876543210"));
        assertTrue(login.checkPhoneNumber("+27999000111"));
    }

    @Test
    void testCheckPhoneNumberInvalid() {
        assertFalse(login.checkPhoneNumber(null));
        assertFalse(login.checkPhoneNumber("27123456789"));    // missing +
        assertFalse(login.checkPhoneNumber("+2712345678"));    // only 8 digits
        assertFalse(login.checkPhoneNumber("+271234567890"));  // 10 digits
        assertFalse(login.checkPhoneNumber("+27123abc789"));   // letters
        assertFalse(login.checkPhoneNumber("+27"));            // too short
    }

    @Test
    void testRegisterUserSuccess() {
        String result = login.registerUser("j_doe", "Password1!", "John", "Doe", "+27123456789");
        assertEquals("The two above conditions have been met and the user has been registered successfully.", result);
        
        // Verify login works after registration
        assertTrue(login.loginUser("j_doe", "Password1!"));
        assertEquals("Welcome John, Doe it is great to see you again.", login.returnLoginStatus(true));
        assertEquals("+27123456789", login.getPhoneNumber());
    }

    @Test
    void testRegisterUserInvalidUsername() {
        String result = login.registerUser("invalid", "Password1!", "John", "Doe", "+27123456789");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", result);
        assertFalse(login.loginUser("invalid", "Password1!"));
    }

    @Test
    void testRegisterUserInvalidPassword() {
        String result = login.registerUser("j_doe", "weak", "John", "Doe", "+27123456789");
        assertEquals("Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number, and a special character", result);
        assertFalse(login.loginUser("j_doe", "weak"));
    }

    @Test
    void testRegisterUserInvalidPhone() {
        String result = login.registerUser("j_doe", "Password1!", "John", "Doe", "12345");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);
        assertFalse(login.loginUser("j_doe", "Password1!"));
    }

    @Test
    void testLoginUserSuccessAfterRegistration() {
        login.registerUser("j_doe", "Password1!", "John", "Doe", "+27123456789");
        assertTrue(login.loginUser("j_doe", "Password1!"));
        assertEquals("Welcome John, Doe it is great to see you again.", login.returnLoginStatus(true));
    }

    @Test
    void testLoginUserWrongPassword() {
        login.registerUser("j_doe", "Password1!", "John", "Doe", "+27123456789");
        assertFalse(login.loginUser("j_doe", "wrongpass"));
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(false));
    }

    @Test
    void testLoginUserNonExistent() {
        assertFalse(login.loginUser("unknown", "pass"));
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(false));
    }

    @Test
    void testReturnLoginStatusTrue() {
        login.registerUser("j_doe", "Password1!", "Alice", "Smith", "+27876543210");
        login.loginUser("j_doe", "Password1!");
        assertEquals("Welcome Alice, Smith it is great to see you again.", login.returnLoginStatus(true));
    }

    @Test
    void testReturnLoginStatusFalse() {
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(false));
    }

    @Test
    void testGetPhoneNumber() {
        assertNull(login.getPhoneNumber());
        login.registerUser("j_doe", "Password1!", "John", "Doe", "+27123456789");
        assertEquals("+27123456789", login.getPhoneNumber());
    }
}