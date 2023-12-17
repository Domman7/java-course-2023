package edu.hw8.task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordCrackerMultiThread {
    private final Map<String, String> encryptedData = new HashMap<>();
    private final int alphabetSize;
    private final Map<Integer, Character> chatTable = new HashMap<>();
    private final int[] passwordArray;
    private final int passwordLength;

    public PasswordCrackerMultiThread(String alphabet, int passwordLength) {
        alphabetSize = alphabet.length();
        passwordArray = new int[passwordLength];
        this.passwordLength = passwordLength;

        for (int i = 0; i < passwordLength; i++) {
            passwordArray[i] = alphabetSize - 1;
        }

        for (int i = 0; i < alphabetSize; i++) {
            chatTable.put(i, alphabet.charAt(i));
        }
    }

    public void put(String K, String V) {

        encryptedData.put(K, V);
    }

    public Map<String, String> decryptMultiThread(int numThreads) {
        var result = new HashMap<String, String>();

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                String password = nextPassword();

                while (encryptedData.size() != 0) {
                    String hash = md5Hash(password);

                    if (encryptedData.containsKey(hash)) {
                        result.put(encryptedData.get(hash), password);
                        encryptedData.remove(hash);
                    }

                    password = nextPassword();
                }
            });
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
        }

        return result;
    }

    private synchronized String nextPassword() {
        if (decrement()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passwordLength; i++) {
                sb.append(chatTable.get(passwordArray[i]));
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
