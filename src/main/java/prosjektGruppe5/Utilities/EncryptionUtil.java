package prosjektGruppe5.Utilities;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
@Service
public class EncryptionUtil {
    public String hashPassword(String passwordPrehash, String salt) {
        if (passwordPrehash == null || salt == null) {
            throw new IllegalArgumentException();

        }
        char[] charArr = passwordPrehash.toCharArray();
        byte[] saltbytes = DatatypeConverter.parseHexBinary(salt);
        byte[] keyhash = null;
        try {
            PBEKeySpec pks = new PBEKeySpec(charArr, saltbytes, 1000, 256);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            keyhash = skf.generateSecret(pks).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return DatatypeConverter.printHexBinary(keyhash);

    }

    public String getSalt() {
        SecureRandom sr;
        byte[] salt = new byte[16];
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return DatatypeConverter.printHexBinary(salt);
    }
}