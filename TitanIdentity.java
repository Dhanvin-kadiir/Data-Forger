/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  TITAN IDENTITY — STANDALONE LEGACY ENTRY POINT     ║
 * ║                                                      ║
 * ║  This file is the original standalone CLI script.   ║
 * ║  The full integrated version (with UI) lives in:    ║
 * ║  src/main/java/com/dataforger/IdentityForgerService.java ║
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
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Standalone CLI version of TitanIdentity.
 * Now uses 254 unique names from a globally-diverse pool.
 */
public class TitanIdentity {

    private static final SecureRandom RANDOM = new SecureRandom();

    // ══════════════════════════════════════════════════════
    //  FIRST NAME DATA BANK  (254 entries)
    // ══════════════════════════════════════════════════════
    private static final String[] FIRST_NAMES = {
        // Neutral
        "Alex","Jordan","Casey","Morgan","Riley","Avery","Quinn","Reese",
        "Finley","Sage","Rowan","Blake","Cameron","Drew","Emery","Harley",
        "Jamie","Kendall","Logan","Marlowe","Nova","Oakley","Parker","Remy",
        "Skyler","Taylor","Zion","Ash","Bay","Corey",
        // Western
        "James","Sarah","Michael","Emma","David","Olivia","William","Sophia",
        "Benjamin","Charlotte","Liam","Amelia","Noah","Isabella","Ethan","Mia",
        "Lucas","Harper","Henry","Evelyn","Mason","Abigail","Elijah","Emily",
        "Oliver","Elizabeth","Sebastian","Scarlett","Jack","Victoria","Daniel",
        "Grace","Matthew","Chloe","Aiden","Penelope","Owen","Lily","Samuel",
        // Russian
        "Dimitri","Viktor","Anastasia","Elena","Nikolai","Natasha","Ivan",
        "Oksana","Alexei","Tatiana","Pavel","Irina","Sergei","Olga","Andrei",
        "Katerina","Boris","Svetlana","Mikhail","Veronika","Yaroslav","Daria",
        "Ruslan","Ludmila","Vladislav","Alina","Artem","Polina","Denys","Kira",
        // Japanese
        "Kenji","Yuki","Ren","Sakura","Hiro","Aiko","Takeshi","Haruki",
        "Yuna","Kazuma","Misaki","Ryuu","Nami","Sora","Tsubasa",
        "Mei","Kaito","Ayane","Riku","Koharu","Shinji","Asuka","Naoki",
        "Yoshi","Hinata","Daisuke","Akira","Izumi","Taichi",
        // Korean
        "Jiwoo","Minho","Soyeon","Junhyeok","Eunbi","Taeyeon","Jaemin",
        "Chaeyoung","Dongwoo","Yujin","Seongmin","Nayeon","Jihoon","Minji",
        "Yunseo","Hyunsik","Bora","Jinhyuk","Seulgi","Kyungwon",
        // Chinese
        "Wei","Fang","Jing","Yang","Lei","Xiao","Chen","Lin","Zhi",
        "Hui","Ling","Hao","Yu","Yan","Jun","Xin","Qian","Bo","Tao",
        // Indian
        "Arjun","Rohan","Priya","Sneha","Vikram","Deepa","Siddharth","Kavya",
        "Rahul","Pooja","Aditya","Ananya","Kiran","Ishaan","Divya","Nikhil",
        "Shreya","Aarav","Riya","Yash","Tanvi","Akash","Nisha","Varun",
        "Meera","Dhruv","Swati","Aman","Lavanya","Kartik",
        // Arabic
        "Omar","Layla","Khalid","Fatima","Hassan","Nour","Youssef","Amira",
        "Tariq","Samira","Zaid","Rania","Faris","Yasmin","Adnan","Hana",
        "Bilal","Lina","Kareem","Mariam",
        // African
        "Kwame","Amara","Chidi","Zainab","Kofi","Afia","Emeka","Chioma",
        "Seun","Nkechi","Tunde","Adaeze","Babatunde","Yewande","Femi",
        "Onyeka","Tobi","Ngozi","Dele","Sade",
        // Latin
        "Diego","Valentina","Mateo","Santiago","Camila","Sebastián",
        "Luciana","Alejandro","Gabriela","Andrés","Daniela","Emilio","Natalia",
        "Carlos","Sofía","Miguel","Catalina","Rafael","Mariana",
        // Nordic
        "Erik","Astrid","Bjorn","Ingrid","Sven","Freya","Lars","Sigrid",
        "Gunnar","Hilde","Leif","Ragnhild","Arne","Solveig","Thorstin",
        // Cyber handles
        "Cipher","Ghost","Nexus","Phantom","Vector","Rogue","Specter","Null",
        "Void","Zero","Hex","Nyx","Glitch","Daemon","Wraith"
    };

    private static final String[] LAST_NAMES = {
        "Smith","Johnson","Williams","Brown","Jones","Miller","Davis","Garcia",
        "Anderson","Wilson","Taylor","Thomas","Jackson","White","Harris",
        "Martin","Thompson","Moore","Young","Allen",
        "Ivanov","Petrov","Sokolov","Volkov","Kozlov","Morozov","Popov",
        "Lebedev","Novikov","Fedorov","Mikhailov","Belov","Stepanov","Gusev",
        "Sato","Suzuki","Takahashi","Tanaka","Watanabe","Ito","Yamamoto",
        "Nakamura","Kobayashi","Kato","Yoshida","Yamada","Sasaki","Matsumoto",
        "Sharma","Patel","Reddy","Nair","Gupta","Singh","Kumar","Mehta",
        "Joshi","Malhotra","Bose","Chatterjee","Pillai","Iyer","Menon",
        "Kim","Lee","Park","Choi","Jung","Kang","Yoon","Jang","Lim","Han",
        "Al-Rashid","Hassan","Khalil","Mansour","Naser","Qureshi","Rahman",
        "Saleh","Younis","Zayed",
        "Okafor","Mensah","Diallo","Traore","Kamara","Sesay","Toure",
        "Coulibaly","Bah","Sylla",
        "Rodriguez","Lopez","Hernandez","Gonzalez","Martinez","Reyes",
        "Morales","Torres","Vargas","Romero",
        "Andersen","Hansen","Larsen","Nielsen","Eriksen","Berg","Holm",
        "Johansson","Lindqvist","Magnusson",
        "Frost","Steel","Wolf","Shadow","Rider","Cipher","Vex","Null",
        "Ghost","Raven","Storm","Blaze","Edge","Nox","Wraith"
    };

    private static final String[] LOCATIONS = {
        "Tokyo, Japan","Osaka, Japan","Kyoto, Japan","Sapporo, Japan",
        "Moscow, Russia","St. Petersburg, Russia","Novosibirsk, Russia",
        "New York, USA","Austin, Texas, USA","Seattle, USA","Los Angeles, USA",
        "London, UK","Manchester, UK","Edinburgh, UK",
        "Berlin, Germany","Munich, Germany","Hamburg, Germany",
        "Toronto, Canada","Vancouver, Canada","Montreal, Canada",
        "Mumbai, India","Bangalore, India","Delhi, India","Chennai, India",
        "Dubai, UAE","Abu Dhabi, UAE",
        "Singapore","Sydney, Australia","Melbourne, Australia",
        "Seoul, South Korea","Busan, South Korea",
        "Shanghai, China","Beijing, China","Shenzhen, China",
        "São Paulo, Brazil","Buenos Aires, Argentina","Bogotá, Colombia",
        "Lagos, Nigeria","Nairobi, Kenya","Cairo, Egypt",
        "Paris, France","Lyon, France","Amsterdam, Netherlands",
        "Stockholm, Sweden","Oslo, Norway","Copenhagen, Denmark",
        "Unknown","Classified","[REDACTED]","0.0.0.0 // DARK_NET"
    };

    private static final String FILE_PATH = "titan_identities.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(">>> TITAN IDENTITY: GHOST PROFILE GENERATOR <<<");
        System.out.println(">>> TIP: For the full UI experience, run run.bat <<<");
        System.out.println("Press ENTER to generate a new identity (or type 'exit' to quit).");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            String first    = FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
            String last     = LAST_NAMES [RANDOM.nextInt(LAST_NAMES.length)];
            int currentYear = LocalDate.now().getYear();
            int birthYear   = (currentYear - 45) + RANDOM.nextInt(27);
            int dayOfYear   = 1 + RANDOM.nextInt(365);
            LocalDate dob   = LocalDate.ofYearDay(birthYear, dayOfYear);
            String location = LOCATIONS[RANDOM.nextInt(LOCATIONS.length)];

            System.out.println("---------------------------------------------");
            System.out.println("ALIAS:    " + first + " " + last);
            System.out.println("DOB:      " + dob + " (Age: " + (currentYear - birthYear) + ")");
            System.out.println("ORIGIN:   " + location);
            System.out.println("---------------------------------------------");

            System.out.print("Save this identity? (y/n): ");
            String saveChoice = scanner.nextLine();

            if (saveChoice.equalsIgnoreCase("y")) {
                System.out.print("Tag this identity: ");
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
            out.println("[TAG: " + tag + "] NAME: " + name + " | DOB: " + dob + " | LOC: " + loc);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save identity.");
        }
    }
}