package util;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class PasswordEncoder {
    public static String generateSalt() {
        int saltSize = 256;
        return DigestUtils.sha256Hex(RandomStringUtils.randomAlphanumeric(saltSize));
    }

    public static String generatePassword(String password, String salt) {
        return DigestUtils.sha256Hex(xor(password.getBytes(), salt.getBytes()));
    }

    private static String xor(byte[] password, byte[] salt) {
        byte[] out = new byte[password.length];
        for (int i = 0; i < password.length; i++) {
            out[i] = (byte) (password[i] ^ salt[i % salt.length]);
        }
        return new String(out, StandardCharsets.UTF_8);
    }
}
