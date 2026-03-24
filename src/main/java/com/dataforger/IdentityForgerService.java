package com.dataforger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.time.LocalDate;

/**
 * ╔══════════════════════════════════════════╗
 * ║  TITAN IDENTITY // GHOST PROFILE ENGINE  ║
 * ╚══════════════════════════════════════════╝
 *
 * Forges randomised ghost identities from a pool of 250+ diverse names.
 * Uses SecureRandom for unpredictability.
 */
public class IdentityForgerService {

    private static final SecureRandom RNG = new SecureRandom();
    private static final String FILE_PATH = "titan_identities.txt";

    // ══════════════════════════════════════════════════════════════
    //  FIRST NAME DATA BANK  (254 entries, globally diverse)
    // ══════════════════════════════════════════════════════════════
    private static final String[] FIRST_NAMES = {
        // ── Neutral / Gender-Neutral ──
        "Alex", "Jordan", "Casey", "Morgan", "Riley", "Avery", "Quinn", "Reese",
        "Finley", "Sage", "Rowan", "Blake", "Cameron", "Drew", "Emery", "Harley",
        "Jamie", "Kendall", "Logan", "Marlowe", "Nova", "Oakley", "Parker", "Remy",
        "Skyler", "Taylor", "Zion", "Ash", "Bay", "Corey",

        // ── Western / Anglo-Saxon ──
        "James", "Sarah", "Michael", "Emma", "David", "Olivia", "William", "Sophia",
        "Benjamin", "Charlotte", "Liam", "Amelia", "Noah", "Isabella", "Ethan", "Mia",
        "Lucas", "Harper", "Henry", "Evelyn", "Mason", "Abigail", "Elijah", "Emily",
        "Oliver", "Elizabeth", "Sebastian", "Scarlett", "Jack", "Victoria", "Daniel",
        "Grace", "Matthew", "Chloe", "Aiden", "Penelope", "Owen", "Lily", "Samuel",

        // ── Russian / Slavic ──
        "Dimitri", "Viktor", "Anastasia", "Elena", "Nikolai", "Natasha", "Ivan",
        "Oksana", "Alexei", "Tatiana", "Pavel", "Irina", "Sergei", "Olga", "Andrei",
        "Katerina", "Boris", "Svetlana", "Mikhail", "Veronika", "Yaroslav", "Daria",
        "Ruslan", "Ludmila", "Vladislav", "Alina", "Artem", "Polina", "Denys", "Kira",

        // ── Japanese ──
        "Kenji", "Yuki", "Ren", "Sakura", "Hiro", "Aiko", "Takeshi", "Haruki",
        "Yuna", "Kazuma", "Misaki", "Ryuu", "Nami", "Sora", "Kira", "Tsubasa",
        "Mei", "Kaito", "Ayane", "Riku", "Koharu", "Shinji", "Asuka", "Naoki",
        "Yoshi", "Hinata", "Daisuke", "Akira", "Izumi", "Taichi",

        // ── Korean ──
        "Jiwoo", "Minho", "Soyeon", "Junhyeok", "Eunbi", "Taeyeon", "Jaemin",
        "Chaeyoung", "Dongwoo", "Yujin", "Seongmin", "Nayeon", "Jihoon", "Minji",
        "Yunseo", "Hyunsik", "Bora", "Jinhyuk", "Seulgi", "Kyungwon",

        // ── Chinese ──
        "Wei", "Fang", "Jing", "Yang", "Mei", "Lei", "Xiao", "Chen", "Lin", "Zhi",
        "Hui", "Ling", "Hao", "Yu", "Yan", "Jun", "Xin", "Qian", "Bo", "Tao",

        // ── Indian / South Asian ──
        "Arjun", "Rohan", "Priya", "Sneha", "Vikram", "Deepa", "Siddharth", "Kavya",
        "Rahul", "Pooja", "Aditya", "Ananya", "Kiran", "Ishaan", "Divya", "Nikhil",
        "Shreya", "Aarav", "Riya", "Yash", "Tanvi", "Akash", "Nisha", "Varun",
        "Meera", "Dhruv", "Swati", "Aman", "Lavanya", "Kartik",

        // ── Arabic / Middle Eastern ──
        "Omar", "Layla", "Khalid", "Fatima", "Hassan", "Nour", "Youssef", "Amira",
        "Tariq", "Samira", "Zaid", "Rania", "Faris", "Yasmin", "Adnan", "Hana",
        "Bilal", "Lina", "Kareem", "Mariam",

        // ── African / Sub-Saharan ──
        "Kwame", "Amara", "Chidi", "Zainab", "Kofi", "Afia", "Emeka", "Chioma",
        "Seun", "Nkechi", "Tunde", "Adaeze", "Babatunde", "Yewande", "Femi",
        "Onyeka", "Tobi", "Ngozi", "Dele", "Sade",

        // ── Latin / Hispanic ──
        "Diego", "Valentina", "Mateo", "Isabella", "Santiago", "Camila", "Sebastián",
        "Luciana", "Alejandro", "Gabriela", "Andrés", "Daniela", "Emilio", "Natalia",
        "Carlos", "Sofía", "Miguel", "Catalina", "Rafael", "Mariana",

        // ── Nordic / Scandinavian ──
        "Erik", "Astrid", "Bjorn", "Ingrid", "Sven", "Freya", "Lars", "Sigrid",
        "Gunnar", "Hilde", "Leif", "Ragnhild", "Arne", "Solveig", "Thorstin",

        // ── Cyber / "Ghost" Handles (hacker dark-fantasy flavour) ──
        "Cipher", "Ghost", "Nexus", "Phantom", "Vector", "Rogue", "Specter", "Null",
        "Void", "Zero", "Hex", "Nyx", "Glitch", "Daemon", "Wraith"
    };

    // ══════════════════════════════════════════════════════════════
    //  LAST NAME DATA BANK  (globally diverse)
    // ══════════════════════════════════════════════════════════════
    private static final String[] LAST_NAMES = {
        // Western
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis",
        "Garcia", "Anderson", "Wilson", "Taylor", "Thomas", "Jackson", "White",
        "Harris", "Martin", "Thompson", "Moore", "Young", "Allen",
        // Russian
        "Ivanov", "Petrov", "Sokolov", "Volkov", "Kozlov", "Morozov", "Popov",
        "Lebedev", "Novikov", "Fedorov", "Mikhailov", "Belov", "Stepanov", "Gusev",
        // Japanese
        "Sato", "Suzuki", "Takahashi", "Tanaka", "Watanabe", "Ito", "Yamamoto",
        "Nakamura", "Kobayashi", "Kato", "Yoshida", "Yamada", "Sasaki", "Matsumoto",
        // Indian
        "Sharma", "Patel", "Reddy", "Nair", "Gupta", "Singh", "Kumar", "Mehta",
        "Joshi", "Malhotra", "Bose", "Chatterjee", "Pillai", "Iyer", "Menon",
        // Korean
        "Kim", "Lee", "Park", "Choi", "Jung", "Kang", "Yoon", "Jang", "Lim", "Han",
        // Arabic
        "Al-Rashid", "Hassan", "Khalil", "Mansour", "Naser", "Qureshi", "Rahman",
        "Saleh", "Younis", "Zayed",
        // African
        "Okafor", "Mensah", "Diallo", "Traore", "Kamara", "Sesay", "Toure",
        "Coulibaly", "Bah", "Sylla",
        // Latin
        "Rodriguez", "Lopez", "Hernandez", "Gonzalez", "Martinez", "Reyes",
        "Morales", "Torres", "Vargas", "Romero",
        // Nordic
        "Andersen", "Hansen", "Larsen", "Nielsen", "Eriksen", "Berg", "Holm",
        "Johansson", "Lindqvist", "Magnusson",
        // Cyber / Ghost Handles
        "Frost", "Steel", "Wolf", "Shadow", "Rider", "Cipher", "Vex", "Null",
        "Ghost", "Raven", "Storm", "Blaze", "Edge", "Nox", "Wraith"
    };

    // ══════════════════════════════════════════════════════════════
    //  LOCATION DATA BANK
    // ══════════════════════════════════════════════════════════════
    private static final String[] LOCATIONS = {
        "Tokyo, Japan", "Osaka, Japan", "Kyoto, Japan", "Sapporo, Japan",
        "Moscow, Russia", "St. Petersburg, Russia", "Novosibirsk, Russia",
        "New York, USA", "Austin, Texas, USA", "Seattle, USA", "Los Angeles, USA",
        "London, UK", "Manchester, UK", "Edinburgh, UK",
        "Berlin, Germany", "Munich, Germany", "Hamburg, Germany",
        "Toronto, Canada", "Vancouver, Canada", "Montreal, Canada",
        "Mumbai, India", "Bangalore, India", "Delhi, India", "Chennai, India",
        "Dubai, UAE", "Abu Dhabi, UAE",
        "Singapore", "Sydney, Australia", "Melbourne, Australia",
        "Seoul, South Korea", "Busan, South Korea",
        "Shanghai, China", "Beijing, China", "Shenzhen, China",
        "São Paulo, Brazil", "Buenos Aires, Argentina", "Bogotá, Colombia",
        "Lagos, Nigeria", "Nairobi, Kenya", "Cairo, Egypt",
        "Paris, France", "Lyon, France", "Amsterdam, Netherlands",
        "Stockholm, Sweden", "Oslo, Norway", "Copenhagen, Denmark",
        "Unknown",  "Classified", "[REDACTED]", "0.0.0.0 // DARK_NET"
    };

    // ══════════════════════════════════════════════════════════════
    //  PUBLIC API
    // ══════════════════════════════════════════════════════════════

    /**
     * Forges a new randomised ghost identity.
     * Called each time the user hits [FORGE IDENTITY] in the UI.
     */
    public GhostIdentity forgeIdentity() {
        String first = FIRST_NAMES[RNG.nextInt(FIRST_NAMES.length)];
        String last  = LAST_NAMES[RNG.nextInt(LAST_NAMES.length)];

        // Age range: 19–45 → birth year 1980-2006 (relative to 2025)
        int currentYear = java.time.LocalDate.now().getYear();
        int birthYear   = (currentYear - 45) + RNG.nextInt(27);
        int dayOfYear   = 1 + RNG.nextInt(365);
        LocalDate dob   = LocalDate.ofYearDay(birthYear, dayOfYear);
        int age         = currentYear - birthYear;

        String location = LOCATIONS[RNG.nextInt(LOCATIONS.length)];
        String fullName = first + " " + last;

        return new GhostIdentity(fullName, dob.toString(), age, location);
    }

    /**
     * Persists a ghost identity to the local vault file.
     */
    public void saveIdentity(String tag, String name, String dob, String location) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter out = new PrintWriter(fw)) {
            out.println("[TAG: " + tag + "] NAME: " + name
                    + " | DOB: " + dob + " | LOC: " + location);
            System.out.println("\033[32m[VAULT]\033[0m Identity committed → " + name);
        } catch (IOException e) {
            System.err.println("\033[31m[ERROR]\033[0m Failed to write identity: " + e.getMessage());
        }
    }

    // ── Record returned to UI as JSON ──────────────────────────
    public record GhostIdentity(String name, String dob, int age, String location) {}
}
