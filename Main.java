import auth.AuthService;
import vault.VaultService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Secure Password Manager Prototype ===");

        System.out.print("Register Email: ");
        String email = scanner.nextLine();

        System.out.print("Master Password: ");
        String password = scanner.nextLine();

        AuthService.register(email, password);
        System.out.println("Account created.\n");

        System.out.print("Login Password: ");
        String loginPass = scanner.nextLine();

        if (!AuthService.login(email, loginPass)) {
            System.out.println("Login failed.");
            return;
        }

        System.out.println("Login successful!\n");

        String generated = VaultService.generatePassword(16);
        System.out.println("Generated Password: " + generated);

        VaultService.addCredential(email, "example.com", "user123", generated);
        System.out.println("Credential stored securely.\n");

        System.out.println("=== Vault Contents ===");
        VaultService.viewVault(email);
    }
}