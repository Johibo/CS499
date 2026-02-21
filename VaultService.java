package vault;

import crypto.CryptoUtils;
import model.Credential;
import java.util.*;

public class VaultService {

    private static Map<String, List<Credential>> vault = new HashMap<>();

    public static void addCredential(String user, String site, String username, String password) throws Exception {
        vault.putIfAbsent(user, new ArrayList<>());

        vault.get(user).add(new Credential(
                site,
                CryptoUtils.encrypt(username),
                CryptoUtils.encrypt(password)
        ));
    }

    public static void viewVault(String user) throws Exception {
        List<Credential> creds = vault.getOrDefault(user, new ArrayList<>());
        for (Credential c : creds) {
            System.out.println("Site: " + c.site);
            System.out.println("Username: " + CryptoUtils.decrypt(c.username));
            System.out.println("Password: " + CryptoUtils.decrypt(c.password));
            System.out.println("-------------------------");
        }
    }

    public static String generatePassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return password.toString();
    }
}