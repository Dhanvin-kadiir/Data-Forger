import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDate;

/**
 * DATA-FORGER // GHOST SYSTEM v1.0
 * Ghost identity engine with 254 diverse first names.
 */
public class IdentityForgerService {

    private static final SecureRandom RNG       = new SecureRandom();
    private static final String       FILE_PATH = "titan_identities.txt";

    // =========================================================
    //  FIRST NAME DATA BANK -- 254 entries, 12 cultural groups
    // =========================================================
    private static final String[] FIRST_NAMES = {
        // Neutral / Gender-Neutral (30)
        "Alex","Jordan","Casey","Morgan","Riley","Avery","Quinn","Reese",
        "Finley","Sage","Rowan","Blake","Cameron","Drew","Emery","Harley",
        "Jamie","Kendall","Logan","Marlowe","Nova","Oakley","Parker","Remy",
        "Skyler","Taylor","Zion","Ash","Bay","Corey",
        // Western (39)
        "James","Sarah","Michael","Emma","David","Olivia","William","Sophia",
        "Benjamin","Charlotte","Liam","Amelia","Noah","Isabella","Ethan","Mia",
        "Lucas","Harper","Henry","Evelyn","Mason","Abigail","Elijah","Emily",
        "Oliver","Elizabeth","Sebastian","Scarlett","Jack","Victoria","Daniel",
        "Grace","Matthew","Chloe","Aiden","Penelope","Owen","Lily","Samuel",
        // Russian / Slavic (30)
        "Dimitri","Viktor","Anastasia","Elena","Nikolai","Natasha","Ivan",
        "Oksana","Alexei","Tatiana","Pavel","Irina","Sergei","Olga","Andrei",
        "Katerina","Boris","Svetlana","Mikhail","Veronika","Yaroslav","Daria",
        "Ruslan","Ludmila","Vladislav","Alina","Artem","Polina","Denys","Kira",
        // Japanese (29)
        "Kenji","Yuki","Ren","Sakura","Hiro","Aiko","Takeshi","Haruki",
        "Yuna","Kazuma","Misaki","Ryuu","Nami","Sora","Tsubasa",
        "Mei","Kaito","Ayane","Riku","Koharu","Shinji","Asuka","Naoki",
        "Yoshi","Hinata","Daisuke","Akira","Izumi","Taichi",
        // Korean (20)
        "Jiwoo","Minho","Soyeon","Junhyeok","Eunbi","Taeyeon","Jaemin",
        "Chaeyoung","Dongwoo","Yujin","Seongmin","Nayeon","Jihoon","Minji",
        "Yunseo","Hyunsik","Bora","Jinhyuk","Seulgi","Kyungwon",
        // Chinese (19)
        "Wei","Fang","Jing","Yang","Lei","Xiao","Chen","Lin","Zhi",
        "Hui","Ling","Hao","Yu","Yan","Jun","Xin","Qian","Bo","Tao",
        // Indian / South Asian (30)
        "Arjun","Rohan","Priya","Sneha","Vikram","Deepa","Siddharth","Kavya",
        "Rahul","Pooja","Aditya","Ananya","Kiran","Ishaan","Divya","Nikhil",
        "Shreya","Aarav","Riya","Yash","Tanvi","Akash","Nisha","Varun",
        "Meera","Dhruv","Swati","Aman","Lavanya","Kartik",
        // Arabic / Middle Eastern (20)
        "Omar","Layla","Khalid","Fatima","Hassan","Nour","Youssef","Amira",
        "Tariq","Samira","Zaid","Rania","Faris","Yasmin","Adnan","Hana",
        "Bilal","Lina","Kareem","Mariam",
        // African / Sub-Saharan (20)
        "Kwame","Amara","Chidi","Zainab","Kofi","Afia","Emeka","Chioma",
        "Seun","Nkechi","Tunde","Adaeze","Babatunde","Yewande","Femi",
        "Onyeka","Tobi","Ngozi","Dele","Sade",
        // Latin / Hispanic (19)
        "Diego","Valentina","Mateo","Santiago","Camila","Luciana","Alejandro",
        "Gabriela","Daniela","Emilio","Natalia","Carlos","Sofia","Miguel",
        "Catalina","Rafael","Mariana","Andres","Sebastian",
        // Nordic / Scandinavian (15)
        "Erik","Astrid","Bjorn","Ingrid","Sven","Freya","Lars","Sigrid",
        "Gunnar","Hilde","Leif","Ragnhild","Arne","Solveig","Thorstin",
        // Cyber / Ghost Handles (15)
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
        "Al-Rashid","Hassan","Khalil","Mansour","Naser","Qureshi","Rahman","Saleh","Younis","Zayed",
        "Okafor","Mensah","Diallo","Traore","Kamara","Sesay","Toure","Coulibaly","Bah","Sylla",
        "Rodriguez","Lopez","Hernandez","Gonzalez","Martinez","Reyes","Morales","Torres","Vargas","Romero",
        "Andersen","Hansen","Larsen","Nielsen","Eriksen","Berg","Holm","Johansson","Lindqvist","Magnusson",
        "Frost","Steel","Wolf","Shadow","Rider","Cipher","Vex","Null","Ghost","Raven","Storm","Blaze","Edge","Nox","Wraith"
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
        "Sao Paulo, Brazil","Buenos Aires, Argentina","Bogota, Colombia",
        "Lagos, Nigeria","Nairobi, Kenya","Cairo, Egypt",
        "Paris, France","Lyon, France","Amsterdam, Netherlands",
        "Stockholm, Sweden","Oslo, Norway","Copenhagen, Denmark",
        "Unknown","Classified","[REDACTED]","0.0.0.0 // DARK_NET"
    };

    public GhostIdentity forgeIdentity() {
        String first  = FIRST_NAMES[RNG.nextInt(FIRST_NAMES.length)];
        String last   = LAST_NAMES [RNG.nextInt(LAST_NAMES.length)];
        int curYear   = LocalDate.now().getYear();
        int birthYear = (curYear - 45) + RNG.nextInt(27);
        int dayOfYear = 1 + RNG.nextInt(365);
        LocalDate dob = LocalDate.ofYearDay(birthYear, dayOfYear);
        String loc    = LOCATIONS[RNG.nextInt(LOCATIONS.length)];
        return new GhostIdentity(first + " " + last, dob.toString(), curYear - birthYear, loc);
    }

    public void saveIdentity(String tag, String name, String dob, String location) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            out.println("[TAG: " + tag + "] NAME: " + name + " | DOB: " + dob + " | LOC: " + location);
            System.out.println("[VAULT] Identity committed -> " + name);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to write identity: " + e.getMessage());
        }
    }

    public record GhostIdentity(String name, String dob, int age, String location) {}
}
