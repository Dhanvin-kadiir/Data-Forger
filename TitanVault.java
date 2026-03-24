/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  TITAN VAULT — STANDALONE LEGACY ENTRY POINT        ║
 * ║                                                      ║
 * ║  This file is the original standalone CLI script.   ║
 * ║  The full integrated version (with UI) lives in:    ║
 * ║  src/main/java/com/dataforger/TitanVaultService.java ║
 * ║                                                      ║
 * ║  To run the complete Ghost System with UI:           ║
 * ║    Windows:  run.bat                                 ║
 * ║    Unix/Mac: bash run.sh                             ║
 * ║    Browser:  http://localhost:7070                   ║
 * ╚══════════════════════════════════════════════════════╝
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Standalone CLI version of TitanVault.
 * Password length is now adjustable between 10 (MIN) and 32 (MAX) characters.
 */
public class TitanVault {

    // ── Character pools ─────────────────────────────────
    private static final String LOWER   = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS  = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String ALL     = LOWER + UPPER + DIGITS + SPECIAL;

    /** Minimum allowed password length. */
    public static final int MIN_LENGTH = 10;
    /** Maximum allowed password length. */
    public static final int MAX_LENGTH = 32;

    private static final String FILE_PATH = "titan_keys.txt";

    public static void main(String[] args) {
        Scanner scanner    = new Scanner(System.in);
        SecureRandom random = new SecureRandom();

        System.out.println(">>> TITAN VAULT: SECURE PASSWORD GENERATOR <<<");
        System.out.println(">>> TIP: For the full UI experience, run run.bat <<<");
        System.out.println();

        System.out.print("Enter the Account Name (e.g., Gmail Main, Steam): ");
        String accountName = scanner.nextLine();

        if (accountName.trim().isEmpty()) {
            System.out.println("[ERROR] Account name cannot be empty.");
            scanner.close();
            return;
        }

        // ── Password Length ──────────────────────────────
        int length = 0;
        while (length < MIN_LENGTH || length > MAX_LENGTH) {
            System.out.printf("Enter password length (%d – %d): ", MIN_LENGTH, MAX_LENGTH);
            try {
                length = Integer.parseInt(scanner.nextLine().trim());
                if (length < MIN_LENGTH || length > MAX_LENGTH) {
                    System.out.printf("[WARN] Value out of range. Must be between %d and %d.%n",
                            MIN_LENGTH, MAX_LENGTH);
                }
            } catch (NumberFormatException e) {
                System.out.println("[WARN] Invalid input. Enter a number.");
            }
        }

        // ── Generate Password ────────────────────────────
        StringBuilder password = new StringBuilder(length);

        // Guarantee at least one of each character class
        password.append(LOWER  .charAt(random.nextInt(LOWER.length())));
        password.append(UPPER  .charAt(random.nextInt(UPPER.length())));
        password.append(DIGITS .charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        for (int i = 4; i < length; i++) {
            password.append(ALL.charAt(random.nextInt(ALL.length())));
        }

        String finalPassword = shuffleString(password.toString(), random);

        // ── Save & Display ───────────────────────────────
        if (saveToFile(accountName, finalPassword)) {
            System.out.println("------------------------------------------------");
            System.out.println("SUCCESS! Credentials saved to: " + FILE_PATH);
            System.out.println("Account:  " + accountName);
            System.out.println("Password: " + finalPassword);
            System.out.println("Length:   " + finalPassword.length() + " chars");
            System.out.println("------------------------------------------------");
        } else {
            System.out.println("[CRITICAL FAILURE] Could not save to file.");
        }

        scanner.close();
    }

    private static String shuffleString(String input, SecureRandom random) {
        char[] a = input.toCharArray();
        for (int i = a.length - 1; i > 0; i--) {
            int j    = random.nextInt(i + 1);
            char tmp = a[i];
            a[i]     = a[j];
            a[j]     = tmp;
        }
        return new String(a);
    }

    private static boolean saveToFile(String account, String pass) {
        try (FileWriter fw  = new FileWriter(FILE_PATH, true);
             PrintWriter out = new PrintWriter(fw)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String now = LocalDateTime.now().format(dtf);
            out.println("[" + now + "] " + account + " : " + pass);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}