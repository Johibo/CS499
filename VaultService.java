package vault;

import crypto.CryptoUtils;
import model.Credential;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VaultService {

    private static Map<String, List<Credential>> vault = new HashMap<>();

    public static void addCredential(String user, String site, String username, String password) throws Exception {

        vault.putIfAbsent(user, new ArrayList<>());

        vault.get(user).add(
                new Credential(
                        site,
                        CryptoUtils.encrypt(username),
                        CryptoUtils.encrypt(password)
                )
        );
    }

    public static void editCredential(String user, int index, String site, String username, String password) throws Exception {

        List<Credential> credentials = vault.get(user);

        if (credentials == null || index < 0 || index >= credentials.size()) {
            throw new Exception("Invalid credential index");
        }

        credentials.set(index,
                new Credential(
                        site,
                        CryptoUtils.encrypt(username),
                        CryptoUtils.encrypt(password)
                )
        );
    }

    public static void deleteCredential(String user, int index) throws Exception {

        List<Credential> credentials = vault.get(user);

        if (credentials == null || index < 0 || index >= credentials.size()) {
            throw new Exception("Invalid credential index");
        }

        credentials.remove(index);
    }

    public static String generatePassword(int length) {

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    public static String getFormattedVault(String user) throws Exception {

        StringBuilder sb = new StringBuilder();

        List<Credential> credentials = vault.getOrDefault(user, new ArrayList<>());

        if (credentials.isEmpty()) {
            return "Vault is empty.\n";
        }

        for (int i = 0; i < credentials.size(); i++) {

            Credential c = credentials.get(i);

            sb.append("ID: ").append(i).append("\n");
            sb.append("Site: ").append(c.site).append("\n");
            sb.append("Username: ").append(CryptoUtils.decrypt(c.username)).append("\n");
            sb.append("Password: ").append(CryptoUtils.decrypt(c.password)).append("\n");
            sb.append("----------------------------\n");
        }

        return sb.toString();
    }
}
