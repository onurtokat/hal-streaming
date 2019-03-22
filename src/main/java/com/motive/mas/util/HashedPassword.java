// 
// Decompiled by Procyon v0.5.30
// 

package com.motive.mas.util;

import javax.crypto.SecretKey;
import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import org.apache.commons.codec.binary.Base64;
import java.security.SecureRandom;

public class HashedPassword
{
    private static final int iterations = 20000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;
    
    public static String getSaltedHash(final String password) throws Exception {
        final SecureRandom random = new SecureRandom();
        final byte[] salt = new byte[32];
        random.nextBytes(salt);
        return scramble(hash(password, salt), Base64.encodeBase64String(salt));
    }
    
    public static boolean check(final String password, final String stored) throws Exception {
        final String salt = stored.substring(8, 52);
        final String hash = stored.replace(salt, "");
        final String hashOfInput = hash(password, Base64.decodeBase64(salt));
        return hashOfInput.equals(hash);
    }
    
    private static String hash(final String password, final byte[] salt) throws Exception {
        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException("Empty passwords are not supported.");
        }
        final SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        final SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, 20000, 256));
        return Base64.encodeBase64String(key.getEncoded());
    }
    
    private static String scramble(final String hash, final String salt) {
        final StringBuilder sb = new StringBuilder();
        sb.append(hash.substring(0, 8));
        sb.append(salt);
        sb.append(hash.substring(8, hash.length()));
        return sb.toString();
    }
    
    private static boolean strongPasswordValidation(final String password) {
        final String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,30}";
        return password.matches(pattern);
    }
    
    public static void main(final String[] args) throws Exception {
        if (args == null || args.length < 1) {
            System.out.println("Usage: java -cp $CRYPTOGRAPHY_CLASSPATH com.motive.mas.util.HashedPassword $TEXT_TO_HASH");
        }
        if (!strongPasswordValidation(args[0])) {
            System.out.println("When replacing this password, please make sure that the following minimum rules for complex password enforcement, are fulfilled:");
            System.out.println("\t- length should be at least 8 chars");
            System.out.println("\t- the password should contain a mix of upper and lower cases");
            System.out.println("\t- the password should contain at least one digit");
            System.out.println("\t- the password should contain at least one special (non-alphanumeric) char");
        }
        final String text = args[0];
        final String hashed = getSaltedHash(text);
        final boolean dehashed = check(text, hashed);
        System.out.println("HASHED_TEXT=" + hashed);
        System.out.println("HASHED_TEXT_LENGTH=" + hashed.length());
        if (!dehashed) {
            System.out.println("WARNING: Text hashing has been corrupted! Do not use the value provided above. \n");
        }
    }
}
