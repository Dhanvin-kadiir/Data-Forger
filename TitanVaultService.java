import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DATA-FORGER // GHOST SYSTEM v1.0
 * TitanVault -- adjustable-length (10-32) cryptographic password forge.
 */
public class TitanVaultService {

    private static final String LOWER   = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS  = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String ALL     = LOWER + UPPER + DIGITS + SPECIAL;

    public static final int MIN_LENGTH = 10;
    public static final int MAX_LENGTH = 32;

    private static final SecureRandom RNG       = new SecureRandom();
    private static final String       FILE_PATH = "titan_keys.txt";

    /**
     * Generates a cryptographically-secure password.
     * Guarantees at least one char from each class, then Fisher-Yates shuffle.
     *
     * @param length must be between MIN_LENGTH and MAX_LENGTH (caller enforces)
     */
    public String generatePassword(int length) {
        StringBuilder buf = new StringBuilder(length);
        buf.append(LOWER  .charAt(RNG.nextInt(LOWER.length())));
        buf.append(UPPER  .charAt(RNG.nextInt(UPPER.length())));
        buf.append(DIGITS .charAt(RNG.nextInt(DIGITS.length())));
        buf.append(SPECIAL.charAt(RNG.nextInt(SPECIAL.length())));
        for (int i = 4; i < length; i++) buf.append(ALL.charAt(RNG.nextInt(ALL.length())));
        return shuffle(buf.toString());
    }

    /** Appends account+password to titan_keys.txt with a timestamp. */
    public void saveToFile(String account, String password) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            out.println("[" + now + "] " + account + " : " + password);
            System.out.println("[VAULT] Key committed -> " + account);
        } catch (IOException e) {
            System.err.println("[ERROR] Could not write to vault: " + e.getMessage());
        }
    }

    private String shuffle(String input) {
        char[] a = input.toCharArray();
        for (int i = a.length - 1; i > 0; i--) {
            int j = RNG.nextInt(i + 1);
            char t = a[i]; a[i] = a[j]; a[j] = t;
        }
        return new String(a);
    }
}
