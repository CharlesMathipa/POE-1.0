package com.mycompany.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    
    private final Login login = new Login();

    @Test
    public void testCheckUserNameCorrect() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserNameIncorrect() {
        assertFalse(login.checkUserName("kyle_12345"));
    }

    @Test
    public void testCheckPasswordComplexitySuccess() {
        assertTrue(login.checkPasswordComplexity("Ch@tt3r2023!"));
    }

    @Test
    public void testCheckPasswordComplexityFailure() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testPhoneNumberValid() {
        assertTrue(login.checkPhoneNumber("+27123456789"));
    }

    @Test
    public void testPhoneNumberInvalid() {
        assertFalse(login.checkPhoneNumber("08966553"));
    }

    @Test
    public void testLoginLogic() {
        login.registerUser("ky_1", "P@ssword1!", "John", "Doe", "+27821234567");
        assertTrue(login.loginUser("ky_1", "P@ssword1!"));
    }
}