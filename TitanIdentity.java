import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class TitanIdentity {

    // --- DATA BANKS ---
    
    private static final String[] FIRST_NAMES = {
        "Alex", "Jordan", "Casey", "Morgan", "Riley", // Neutral
        "Dimitri", "Viktor", "Anastasia", "Elena",    // Russian
        "Kenji", "Yuki", "Ren", "Sakura", "Hiro",     // Japanese
        "James", "Sarah", "Michael", "Emma", "David", // Western
        "Arjun", "Rohan", "Priya", "Sneha", "Vikram"  // Indian
    };

    private static final String[] LAST_NAMES = {
        "Smith", "Johnson", "Williams", "Brown",      // Western
        "Ivanov", "Petrov", "Sokolov", "Volkov",      // Russian
        "Sato", "Suzuki", "Takahashi", "Tanaka",      // Japanese
        "Sharma", "Patel", "Reddy", "Nair", "Gupta",  // Indian
        "Frost", "Steel", "Wolf", "Shadow", "Rider"   // Edgy/Gaming
    };

    private static final String[] LOCATIONS = {
        "Tokyo, Japan", "Osaka, Japan", "Kyoto, Japan",
        "Moscow, Russia", "St. Petersburg, Russia",
        "New York, USA", "Austin, Texas, USA", "Seattle, USA",
        "London, UK", "Berlin, Germany", "Toronto, Canada",
        "Mumbai, India", "Bangalore, India", "Dubai, UAE",
        "Singapore", "Sydney, Australia"
    };

    private static final String FILE_PATH = "titan_identities.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println(">>> TITAN IDENTITY: GHOST PROFILE GENERATOR <<<");
        System.out.println("Press ENTER to generate a new identity (or type 'exit' to quit).");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            // 1. Generate Name
            String first = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String last = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

            // 2. Generate Date of Birth (Age 19 - 45)
            // Current year is 2025, so birth years between 1980 and 2006
            int year = 1980 + random.nextInt(27); 
            int dayOfYear = 1 + random.nextInt(365);
            LocalDate dob = LocalDate.ofYearDay(year, dayOfYear);

            // 3. Generate Location
            String location = LOCATIONS[random.nextInt(LOCATIONS.length)];

            // Display
            System.out.println("---------------------------------------------");
            System.out.println("ALIAS:    " + first + " " + last);
            System.out.println("DOB:      " + dob + " (Age: " + (2025 - year) + ")");
            System.out.println("ORIGIN:   " + location);
            System.out.println("---------------------------------------------");
            
            // Save prompt
            System.out.print("Save this identity? (y/n): ");
            String saveChoice = scanner.nextLine();
            
            if (saveChoice.equalsIgnoreCase("y")) {
                System.out.print("Tag this identity (e.g., Steam Alt, NSFW, Burner): ");
                String tag = scanner.nextLine();
                saveIdentity(tag, first + " " + last, dob.toString(), location);
                System.out.println("[SAVED] Press ENTER for another.");
            } else {
                System.out.println("[DISCARDED] Press ENTER for another.");
            }
        }
        scanner.close();
    }

    private static void saveIdentity(String tag, String name, String dob, String loc) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter out = new PrintWriter(fw)) {
            out.println("TAG: " + tag + " | NAME: " + name + " | DOB: " + dob + " | LOC: " + loc);
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}