package prosjektGruppe5.UtilityTests;

import org.junit.Test;
import prosjektGruppe5.Utilities.EncryptionUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EncryptionUtilTests {
    private EncryptionUtil eu = new EncryptionUtil();
    @Test
    public void testGetSalt() {
        String salt = eu.getSalt();
        assertNotNull(salt);
        assertNotEquals(salt, eu.getSalt());
    }
    @Test
    public void testHash() {
        String original = "originalpassword";
        String salt = eu.getSalt();
        String hashed = eu.hashPassword(original, salt);
        assertNotEquals(original, hashed);
        assertEquals(eu.hashPassword(original, salt), hashed);
        List<String> passwords = new ArrayList<String>();
        passwords.add("passord");
        passwords.add("dracula");
        passwords.add("HomerSimpson");
        for (int i = 0; i < 1000; i++) {
            passwords.add(eu.getSalt());
        }
        passwords.forEach(x -> {
            assertNotEquals(eu.hashPassword(x,salt), hashed);
        });
    }
}
