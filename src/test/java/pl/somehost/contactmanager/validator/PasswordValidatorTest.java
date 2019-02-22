package pl.somehost.contactmanager.validator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;



public class PasswordValidatorTest {

    @Test
    public void isolatedPasswordPatternTest() {

        String passwordField = "1Yasdfdffff";

         boolean result = passwordField != null && passwordField.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{7,}")
                /*&& (passwordField.length() > 6)*/;
        Assert.assertTrue(result);
    }



    @Test
    public void shouldPasswordBeValid() {

    }
}