package edu.hw8.task3;

import com.mifmif.common.regex.Generex;
import com.mifmif.common.regex.util.Iterator;
import edu.hw8.task2.FixedThreadPool;
import edu.hw8.task2.ThreadPool;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PasswordCracker {
    private static final Map<String, String> ENCRYPTED_DATA = new HashMap<>();
    private static Iterator iterator;

    public PasswordCracker(String pattern) {

        Generex generator = new Generex(pattern);
        iterator = generator.iterator();
    }

    public void put(String K, String V) {

        ENCRYPTED_DATA.put(K, V);
    }

    public Map<String, String> decrypt() {
        String password = nextPassword();
        var result = new HashMap<String, String>();

        while (ENCRYPTED_DATA.size() != 0) {
            String hash = md5Hash(password);

            if (ENCRYPTED_DATA.containsKey(hash)) {
                result.put(ENCRYPTED_DATA.get(hash), password);
                ENCRYPTED_DATA.remove(hash);
            }

            password = nextPassword();
        }

        return result;
    }

    private static String nextPassword() {
        if (iterator.hasNext()) {

            return iterator.next();
        } else {

            throw new RuntimeException("It is impossible to decrypt data");
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
