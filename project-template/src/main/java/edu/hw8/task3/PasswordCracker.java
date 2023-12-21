package edu.hw8.task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PasswordCracker {
    private final Map<String, String> encryptedData = new HashMap<>();
    private final int alphabetSize;
    private final Map<Integer, Character> charTable = new HashMap<>();
    private final int[] passwordArray;
    private final int passwordLength;

    public PasswordCracker(String alphabet, int passwordLength) {
        alphabetSize = alphabet.length();
        passwordArray = new int[passwordLength];
        this.passwordLength = passwordLength;

        for (int i = 0; i < passwordLength; i++) {
            passwordArray[i] = alphabetSize - 1;
        }

        for (int i = 0; i < alphabetSize; i++) {
            charTable.put(i, alphabet.charAt(i));
        }
    }

    public void put(String K, String V) {

        encryptedData.put(K, V);
    }

    public Map<String, String> decrypt() {
        String password = nextPassword();
        var result = new HashMap<String, String>();

        while (encryptedData.size() != 0) {
            String hash = md5Hash(password);

            if (encryptedData.containsKey(hash)) {
                result.put(encryptedData.get(hash), password);
                encryptedData.remove(hash);
            }

            password = nextPassword();
        }

        return result;
    }

    private String nextPassword() {
        if (decrement()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passwordLength; i++) {
                sb.append(charTable.get(passwordArray[i]));
            }

            return sb.toString();
        } else {

            throw new RuntimeException("It is impossible to decrypt data");
        }
    }

    private boolean decrement() {
        int i = 0;

        while (passwordArray[i] == 0) {
            i++;
        }

        if (i != alphabetSize) {
            passwordArray[i]--;
            for (int j = 0; j < i; j++) {
                passwordArray[j] = alphabetSize - 1;
            }

            return true;
        } else {

            return false;
        }
    }

    private static String md5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger bigInt = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(bigInt.toString(16));

            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }

            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);
        }
    }

}
