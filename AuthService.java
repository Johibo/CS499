package auth;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static Map<String, String> users = new HashMap<>();

    public static void register(String email, String password) throws Exception {
        users.put(email, hashPassword(password));
    }

    public static boolean login(String email, String password) throws Exception {
        if (!users.containsKey(email)) return false;
        return users.get(email).equals(hashPassword(password));
    }

    private static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}