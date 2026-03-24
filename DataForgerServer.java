import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.concurrent.*;

/**
 * DATA-FORGER // GHOST SYSTEM v1.0
 * Zero-dependency HTTP server using Java 17 standard library only.
 *
 * Compile: javac -encoding UTF-8 DataForgerServer.java IdentityForgerService.java TitanVaultService.java
 * Run:     java DataForgerServer
 * Browser: http://localhost:7070
 */
public class DataForgerServer {

    static final int    PORT       = 7070;
    static final String STATIC_DIR = "static";

    public static void main(String[] args) throws Exception {
        printBanner();

        IdentityForgerService identityService = new IdentityForgerService();
        TitanVaultService     vaultService    = new TitanVaultService();

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 50);

        // -- Static file server ------------------------------------------------
        server.createContext("/", exchange -> {
            String method = exchange.getRequestMethod();
            if ("OPTIONS".equals(method)) {
                addCors(exchange);
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            String reqPath = exchange.getRequestURI().getPath();
            if (reqPath.equals("/") || reqPath.isEmpty()) reqPath = "/index.html";

            File base = new File(STATIC_DIR).getCanonicalFile();
            File file = new File(base, reqPath).getCanonicalFile();
            if (!file.getPath().startsWith(base.getPath())) {
                respond(exchange, 403, "text/plain", "403 Forbidden");
                return;
            }
            if (file.exists() && file.isFile()) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                exchange.getResponseHeaders().set("Content-Type", mime(reqPath));
                addCors(exchange);
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
            } else {
                respond(exchange, 404, "text/plain", "404 Not Found: " + reqPath);
            }
        });

        // -- GET /api/status ---------------------------------------------------
        server.createContext("/api/status", exchange -> {
            addCors(exchange);
            respondJson(exchange, 200, "{\"success\":true,\"status\":\"GHOST_SYSTEM_ONLINE\",\"data\":null}");
        });

        // -- POST /api/identity/forge ------------------------------------------
        server.createContext("/api/identity/forge", exchange -> {
            addCors(exchange);
            if (!"POST".equals(exchange.getRequestMethod())) { exchange.sendResponseHeaders(405, -1); return; }
            IdentityForgerService.GhostIdentity id = identityService.forgeIdentity();
            String json = String.format(
                "{\"success\":true,\"status\":\"IDENTITY_FORGED\",\"data\":{\"name\":\"%s\",\"dob\":\"%s\",\"age\":%d,\"location\":\"%s\"}}",
                esc(id.name()), id.dob(), id.age(), esc(id.location())
            );
            respondJson(exchange, 200, json);
        });

        // -- POST /api/identity/save -------------------------------------------
        server.createContext("/api/identity/save", exchange -> {
            addCors(exchange);
            if (!"POST".equals(exchange.getRequestMethod())) { exchange.sendResponseHeaders(405, -1); return; }
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            identityService.saveIdentity(
                parseStr(body, "tag"), parseStr(body, "name"),
                parseStr(body, "dob"), parseStr(body, "location")
            );
            respondJson(exchange, 200, "{\"success\":true,\"status\":\"IDENTITY_COMMITTED_TO_VAULT\",\"data\":null}");
        });

        // -- POST /api/vault/generate ------------------------------------------
        server.createContext("/api/vault/generate", exchange -> {
            addCors(exchange);
            if (!"POST".equals(exchange.getRequestMethod())) { exchange.sendResponseHeaders(405, -1); return; }
            String body    = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String account = parseStr(body, "accountName");
            int    length  = Math.max(TitanVaultService.MIN_LENGTH,
                             Math.min(TitanVaultService.MAX_LENGTH, parseInt(body, "length", 20)));
            String password = vaultService.generatePassword(length);
            vaultService.saveToFile(account, password);
            String json = String.format(
                "{\"success\":true,\"status\":\"KEY_FORGED\",\"data\":{\"accountName\":\"%s\",\"password\":\"%s\",\"length\":%d}}",
                esc(account), esc(password), length
            );
            respondJson(exchange, 200, json);
        });

        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
        System.out.println("[ONLINE] Ghost System active -> http://localhost:" + PORT);
        System.out.println("[ONLINE] Open your browser. Press Ctrl+C to stop.\n");
    }

    // -- Helpers ---------------------------------------------------------------

    static void respondJson(HttpExchange e, int code, String json) throws IOException {
        byte[] b = json.getBytes(StandardCharsets.UTF_8);
        e.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        e.sendResponseHeaders(code, b.length);
        try (OutputStream os = e.getResponseBody()) { os.write(b); }
    }

    static void respond(HttpExchange e, int code, String ct, String body) throws IOException {
        byte[] b = body.getBytes(StandardCharsets.UTF_8);
        e.getResponseHeaders().set("Content-Type", ct);
        e.sendResponseHeaders(code, b.length);
        try (OutputStream os = e.getResponseBody()) { os.write(b); }
    }

    static void addCors(HttpExchange e) {
        e.getResponseHeaders().set("Access-Control-Allow-Origin",  "*");
        e.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        e.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }

    static String mime(String path) {
        if (path.endsWith(".html")) return "text/html; charset=UTF-8";
        if (path.endsWith(".css"))  return "text/css; charset=UTF-8";
        if (path.endsWith(".js"))   return "application/javascript; charset=UTF-8";
        if (path.endsWith(".png"))  return "image/png";
        if (path.endsWith(".ico"))  return "image/x-icon";
        return "application/octet-stream";
    }

    static String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r");
    }

    /** Minimal JSON string extractor for our fixed-schema payloads. */
    static String parseStr(String json, String key) {
        int i = json.indexOf("\"" + key + "\"");
        if (i < 0) return "";
        i = json.indexOf(":", i) + 1;
        while (i < json.length() && " \t".indexOf(json.charAt(i)) >= 0) i++;
        if (i >= json.length()) return "";
        if (json.charAt(i) == '"') {
            int s = i + 1, e = s;
            while (e < json.length() && !(json.charAt(e) == '"' && json.charAt(e-1) != '\\')) e++;
            return json.substring(s, e);
        }
        int end = json.length();
        for (char d : new char[]{',', '}', ']'}) {
            int p = json.indexOf(d, i);
            if (p >= 0 && p < end) end = p;
        }
        return json.substring(i, end).trim();
    }

    static int parseInt(String json, String key, int def) {
        try { return Integer.parseInt(parseStr(json, key)); } catch (Exception e) { return def; }
    }

    static void printBanner() {
        System.out.println("\n'########:::::'###::::'########::::'###:::::::'########::'#######::'########:::'######:::'########:");
        System.out.println(" ##.... ##:::'## ##:::... ##..::::'## ##:::::: ##.....::'##.... ##: ##.... ##:'##... ##:: ##.....:: ");
        System.out.println(" ##:::: ##::'##:. ##::::: ##:::::'##:. ##::::: ##::::::: ##:::: ##: ##:::: ##: ##:::..::: ##:::::::  ");
        System.out.println(" ##:::: ##:'##:::. ##:::: ##::::'##:::. ##:::: ######::: ##:::: ##: ########:: ##::'####: ######:::  ");
        System.out.println(" ##:::: ##: #########:::: ##:::: #########:::: ##...:::: ##:::: ##: ##.. ##::: ##::: ##:: ##...::::  ");
        System.out.println(" ##:::: ##: ##.... ##:::: ##:::: ##.... ##:::: ##::::::: ##:::: ##: ##::. ##:: ##::: ##:: ##:::::::  ");
        System.out.println(" ########:: ##:::: ##:::: ##:::: ##:::: ##:::: ##:::::::. #######:: ##:::. ##:. ######::: ########: ");
        System.out.println("........:::..:::::..:::::..:::::..:::::..:::::..:::::::::.......:::..:::::..:::......::::........:: \n");
        System.out.println("[BOOT] Initialising Ghost System...");
        System.out.println("[BOOT] Loading modules: TitanIdentity, TitanVault");
    }
}
