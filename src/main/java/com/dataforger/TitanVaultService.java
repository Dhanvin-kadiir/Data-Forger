package com.dataforger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ╔══════════════════════════════════════════╗
 * ║  TITAN VAULT // SECURE KEY FORGE         ║
 * ╚══════════════════════════════════════════╝
 *
 * Generates cryptographically-secure passwords.
 * Length is adjustable: 10 (min) – 32 (max) characters.
 * Guarantees at least one lowercase, one uppercase,
 * one digit, and one special character in every output.
 */
public class TitanVaultService {

    // ── Character pools ───────────────────────────────────────
    private static final String LOWER   = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS  = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String ALL     = LOWER + UPPER + DIGITS + SPECIAL;

    /** Minimum allowed password length (enforced server-side). */
    public static final int MIN_LENGTH = 10;

    /** Maximum allowed password length (enforced server-side). */
    public static final int MAX_LENGTH = 32;

    private static final SecureRandom RNG      = new SecureRandom();
    private static final String       FILE_PATH = "titan_keys.txt";

    // ══════════════════════════════════════════════════════════
    //  PUBLIC API
    // ══════════════════════════════════════════════════════════

    /**
     * Generates a secure password of the requested length.
     *
     * @param length desired length; caller is responsible for clamping
     *               between {@link #MIN_LENGTH} and {@link #MAX_LENGTH}.
     * @return the generated password string
     */
    public String generatePassword(int length) {
        // Step 1: seed with one guaranteed character from each class
        StringBuilder buf = new StringBuilder(length);
        buf.append(LOWER  .charAt(RNG.nextInt(LOWER.length())));
        buf.append(UPPER  .charAt(RNG.nextInt(UPPER.length())));
        buf.append(DIGITS .charAt(RNG.nextInt(DIGITS.length())));
        buf.append(SPECIAL.charAt(RNG.nextInt(SPECIAL.length())));

        // Step 2: fill remaining characters from the full pool
        for (int i = 4; i < length; i++) {
            buf.append(ALL.charAt(RNG.nextInt(ALL.length())));
        }

        // Step 3: Fisher-Yates shuffle so the guaranteed chars aren't always first
        return shuffle(buf.toString());
    }

    /**
     * Appends the account/password pair to the local vault file with a timestamp.
     *
     * @param account human-readable account label (e.g. "Gmail Main")
     * @param password the generated password
     */
    public void saveToFile(String account, String password) {
        try (FileWriter fw  = new FileWriter(FILE_PATH, true);
             PrintWriter out = new PrintWriter(fw)) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String now = LocalDateTime.now().format(dtf);

            out.println("[" + now + "] " + account + " : " + password);
            System.out.println("\033[32m[VAULT]\033[0m Key committed for account → " + account);

        } catch (IOException e) {
            System.err.println("\033[31m[ERROR]\033[0m Could not write to vault: " + e.getMessage());
        }
    }

    // ── Internals ────────────────────────────────────────────

    /** Fisher-Yates in-place shuffle on a String, returns shuffled String. */
    private String shuffle(String input) {
        char[] a = input.toCharArray();
        for (int i = a.length - 1; i > 0; i--) {
            int j    = RNG.nextInt(i + 1);
            char tmp = a[i];
            a[i]     = a[j];
            a[j]     = tmp;
        }
        return new String(a);
    }
}
