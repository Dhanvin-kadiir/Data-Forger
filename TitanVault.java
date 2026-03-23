import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TitanVault {

    // Configuration
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String ALL_CHARS = LOWER + UPPER + DIGITS + SPECIAL;
    private static final int PASSWORD_LENGTH = 20;
    
    // Change this path to your Pendrive location (e.g., "E:\\titan_keys.txt")
    // For now, it saves in the same folder as the code.
    private static final String FILE_PATH = "titan_keys.txt"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SecureRandom random = new SecureRandom();

        System.out.println(">>> TITAN VAULT: SECURE PASSWORD GENERATOR <<<");
        System.out.print("Enter the Account Name (e.g., Gmail Main, Steam): ");
        String accountName = scanner.nextLine();

        if (accountName.trim().isEmpty()) {
            System.out.println("Error: Account name cannot be empty.");
            return;
        }

        // Generate Password
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        
        // Ensure at least one of each type exists
        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Fill the rest randomly
        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        // Shuffle the characters to mix the forced ones
        String finalPassword = shuffleString(password.toString(), random);

        // Save to File
        if (saveToFile(accountName, finalPassword)) {
            System.out.println("------------------------------------------------");
            System.out.println("SUCCESS! Credentials saved to: " + FILE_PATH);
            System.out.println("Account:  " + accountName);
            System.out.println("Password: " + finalPassword);
            System.out.println("------------------------------------------------");
        } else {
            System.out.println("CRITICAL FAILURE: Could not save to file.");
        }
        
        scanner.close();
    }

    private static String shuffleString(String input, SecureRandom random) {
        char[] a = input.toCharArray();
        for (int i = a.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        return new String(a);
    }

    private static boolean saveToFile(String account, String pass) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter out = new PrintWriter(fw)) {
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            
            out.println("[" + dtf.format(now) + "] " + account + " : " + pass);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}