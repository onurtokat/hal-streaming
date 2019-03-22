// 
// Decompiled by Procyon v0.5.30
// 

package com.motive.mas.util;

import java.util.Base64;
import java.security.spec.AlgorithmParameterSpec;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncryptionDecryptionUtil
{
    private static final String k1 = "mMds_9qh_4q:Haz`";
    
    public static String encrypt(final String s) {
        byte[] iv2 = null;
        String encrypted = null;
        if (s != null) {
            try {
                final byte[] k = "mMds_9qh_4q:Haz`".getBytes("UTF-8");
                final SecretKeySpec spec = new SecretKeySpec(k, "AES");
                final byte[] iv3 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                final IvParameterSpec ivs = new IvParameterSpec(iv3);
                final Cipher cps = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cps.init(1, spec, ivs);
                iv2 = cps.doFinal(s.getBytes("UTF-8"));
                encrypted = new String(Base64.getEncoder().encode(iv2), "UTF-8");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return encrypted;
    }
    
    public static String decrypt(final String passwordAsString) {
        String decrypted = null;
        if (passwordAsString != null) {
            try {
                final byte[] passwordAsByte = Base64.getDecoder().decode(passwordAsString);
                final byte[] k = "mMds_9qh_4q:Haz`".getBytes("UTF-8");
                final SecretKeySpec spec = new SecretKeySpec(k, "AES");
                final byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                final IvParameterSpec ivs = new IvParameterSpec(iv);
                final Cipher cpr = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cpr.init(2, spec, ivs);
                final byte[] iv2 = cpr.doFinal(passwordAsByte);
                decrypted = new String(iv2);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return decrypted;
    }
    
    private static boolean strongPasswordValidation(final String password) {
        final String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,30}";
        return password.matches(pattern);
    }
    
    public static void main(final String[] args) {
        if (args == null || args.length < 1) {
            System.out.println("Usage: java -cp $CRYPTOGRAPHY_CLASSPATH com.motive.mas.util.PasswordEncryptionDecryptionUtil $TEXT_TO_ENCRYPT");
        }
        if (!strongPasswordValidation(args[0])) {
            System.out.println("When replacing this password, please make sure that the following minimum rules for complex password enforcement, are fulfilled:");
            System.out.println("\t- length should be at least 8 chars");
            System.out.println("\t- the password should contain a mix of upper and lower cases");
            System.out.println("\t- the password should contain at least one digit");
            System.out.println("\t- the password should contain at least one special (non-alphanumeric) char");
        }
        final String text = args[0];
        final String encrypted = encrypt(text);
        final String decrypted = decrypt(encrypted);
        System.out.println("ENCRYPTED_TEXT=" + encrypted);
        System.out.println("ENCRYPTED_TEXT_LENGTH=" + encrypted.length());
        if (!text.equals(decrypted)) {
            System.out.println("WARNING: Text encryption has been corrupted! Do not use the value provided above. \n");
        }
    }
}
