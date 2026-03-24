package com.dataforger;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

/**
 * ╔══════════════════════════════════════════╗
 * ║  DATA-FORGER // GHOST SYSTEM v1.0        ║
 * ║  Primary Server Bootstrap                ║
 * ╚══════════════════════════════════════════╝
 *
 * Launches the embedded Javalin HTTP server on port 7070.
 * Serves the static UI from the classpath /static directory
 * and registers all REST API routes.
 */
public class DataForgerServer {

    static final int PORT = 7070;

    public static void main(String[] args) {

        // ── [BOOT] ─────────────────────────────────────────────
        System.out.println("""
                \033[32m
                ██████╗  █████╗ ████████╗ █████╗       ███████╗ ██████╗ ██████╗  ██████╗ ███████╗██████╗
                ██╔══██╗██╔══██╗╚══██╔══╝██╔══██╗      ██╔════╝██╔═══██╗██╔══██╗██╔════╝ ██╔════╝██╔══██╗
                ██║  ██║███████║   ██║   ███████║█████╗█████╗  ██║   ██║██████╔╝██║  ███╗█████╗  ██████╔╝
                ██║  ██║██╔══██║   ██║   ██╔══██║╚════╝██╔══╝  ██║   ██║██╔══██╗██║   ██║██╔══╝  ██╔══██╗
                ██████╔╝██║  ██║   ██║   ██║  ██║      ██║     ╚██████╔╝██║  ██║╚██████╔╝███████╗██║  ██║
                ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝      ╚═╝      ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝
                \033[0m""");

        System.out.println("\033[32m[BOOT]\033[0m Initialising Ghost System...");
        System.out.println("\033[32m[BOOT]\033[0m Loading modules: TitanIdentity, TitanVault");

        // ── [SERVER] ───────────────────────────────────────────
        Javalin app = Javalin.create(config -> {
            // Serve UI from classpath:/static/ at root "/"
            config.staticFiles.add("/static", Location.CLASSPATH);

            // CORS: allow browser fetch from same origin during dev
            config.bundledPlugins.enableCors(cors -> cors.addRule(it -> {
                it.anyHost();
            }));
        });

        // ── [ROUTES] ───────────────────────────────────────────

        // Health check
        app.get("/api/status", ctx -> {
            ctx.json(new ApiResponse(true, "GHOST_SYSTEM_ONLINE", null));
        });

        // ── Identity Forger Endpoints
        IdentityForgerService identityService = new IdentityForgerService();

        /** POST /api/identity/forge
         *  Body: (none)
         *  Returns a randomly generated ghost identity.
         */
        app.post("/api/identity/forge", ctx -> {
            IdentityForgerService.GhostIdentity identity = identityService.forgeIdentity();
            ctx.json(new ApiResponse(true, "IDENTITY_FORGED", identity));
        });

        /** POST /api/identity/save
         *  Body: { "tag": "Steam Alt", "name": "...", "dob": "...", "location": "..." }
         *  Saves the identity to titan_identities.txt
         */
        app.post("/api/identity/save", ctx -> {
            var req = ctx.bodyAsClass(SaveIdentityRequest.class);
            identityService.saveIdentity(req.tag(), req.name(), req.dob(), req.location());
            ctx.json(new ApiResponse(true, "IDENTITY_COMMITTED_TO_VAULT", null));
        });

        // ── TitanVault Endpoints
        TitanVaultService vaultService = new TitanVaultService();

        /** POST /api/vault/generate
         *  Body: { "accountName": "Gmail Main", "length": 20 }
         *  Returns a generated password.
         *  length must be between 10 and 32 (enforced server-side).
         */
        app.post("/api/vault/generate", ctx -> {
            var req = ctx.bodyAsClass(GeneratePasswordRequest.class);

            // Clamp length between 10 and 32
            int length = Math.max(10, Math.min(32, req.length()));

            String password = vaultService.generatePassword(length);
            vaultService.saveToFile(req.accountName(), password);

            ctx.json(new ApiResponse(true, "KEY_FORGED", new PasswordResult(
                    req.accountName(), password, length
            )));
        });

        // ── [LAUNCH] ───────────────────────────────────────────
        app.start(PORT);

        System.out.println("\033[32m[ONLINE]\033[0m Ghost System active on → http://localhost:" + PORT);
        System.out.println("\033[32m[ONLINE]\033[0m Open your browser to begin operations.");
        System.out.println("\033[32m[ONLINE]\033[0m Press Ctrl+C to terminate.");
    }

    // ── Shared Response / Request Records ─────────────────────

    record ApiResponse(boolean success, String status, Object data) {}

    record SaveIdentityRequest(String tag, String name, String dob, String location) {}

    record GeneratePasswordRequest(String accountName, int length) {}

    record PasswordResult(String accountName, String password, int length) {}
}
