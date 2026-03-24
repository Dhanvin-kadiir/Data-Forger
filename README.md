<div align="center">

<img src="logo.png" alt="DATA-FORGER Logo" width="220px"/>

# DATA-FORGER // GHOST SYSTEM

**A cybersecurity-themed synthetic data toolkit**  
*Generate ghost identities. Forge cryptographic keys. Own the dark.*

[![Java](https://img.shields.io/badge/Java-17%2B-3cf91a?style=flat-square&logo=openjdk&logoColor=black&labelColor=000)](https://adoptium.net/)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-3cf91a?style=flat-square&logo=apachemaven&logoColor=black&labelColor=000)](https://maven.apache.org/)
[![Javalin](https://img.shields.io/badge/Javalin-6.3-3cf91a?style=flat-square&labelColor=000)](https://javalin.io/)
[![License](https://img.shields.io/badge/License-Educational-3cf91a?style=flat-square&labelColor=000)](LICENSE)

</div>

---

```
 ____   __  ____   __       ____  __  ____   __  ____  ____ 
(    \ (  )(  __) /  \     (  __)(  )(  _ \ /  \(  _ \(  _ \
 ) D (  )(  ) _) (  O )___  ) _)  )(  )   /(  O ))   / )   /
(____/ (__)(____) \__/(___)(__)  (__)(__\_) \__/(__\_)(__\_)
```

## What Is This?

**DATA-FORGER** is a Java 17 cybersecurity toolkit with a full web UI served by an embedded [Javalin](https://javalin.io/) server. It exposes two core modules:

- **🎭 Identity Forger** — Generates realistic ghost personas from a pool of **254 unique, globally-diverse names**
- **🔐 TitanVault** — Forges cryptographically-secure passwords with an adjustable length from **10 to 32 characters**

All UI interactions are wired to a live Java REST backend — no frameworks, no Node, no nonsense.

---

## Features

### 🎭 Identity Forger (`TitanIdentity`)

| Feature | Detail |
|---------|--------|
| Name Pool | **254 unique first names** across 12 cultural groups |
| Cultures Covered | Western, Russian, Japanese, Korean, Chinese, Indian, Arabic, African, Latin, Nordic, Gender-Neutral, Cyber Handles |
| Randomisation | `SecureRandom` — cryptographically unpredictable |
| Output | Alias, Date of Birth, Age, Global Origin |
| Persistence | Save with a custom tag → `titan_identities.txt` |

### 🔐 TitanVault (`TitanVault`)

| Feature | Detail |
|---------|--------|
| Length Range | **10 – 32 characters** (enforced server-side) |
| Character Classes | Lowercase + Uppercase + Digits + Special (90-char pool) |
| Minimum Coverage | ≥1 char guaranteed from every class |
| Shuffle | Fisher-Yates algorithm for true randomness |
| Entropy | ~130 bits at 20 chars — overkill in the best way |
| Persistence | Auto-saved with timestamp → `titan_keys.txt` |

### 🖥️ Web UI

- Dark hacker/CRT aesthetic — scanlines, glitch animations, phosphor glow
- Single-page app with 5 panels: Main Console, Identity Forger, TitanVault, Schema Editor, Output Buffer
- Live entropy display, copy-to-clipboard, real-time backend status dot

---

## Getting Started

### Prerequisites

- [Java 17+](https://adoptium.net/)
- [Apache Maven 3.8+](https://maven.apache.org/download.cgi)

### Clone & Run

```bash
git clone https://github.com/Dhanvin-kadiir/Data-Forger.git
cd Data-Forger
```

**Windows:**
```batch
run.bat
```

**Unix / macOS:**
```bash
bash run.sh
```

Then open **[http://localhost:7070](http://localhost:7070)** in your browser.

> The launcher builds a fat JAR with Maven, then starts the server automatically.

---

## REST API

The Javalin server exposes a simple JSON API that the UI calls via `fetch()`:

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/status` | Health check |
| `POST` | `/api/identity/forge` | Generate a ghost identity |
| `POST` | `/api/identity/save` | Commit identity to vault file |
| `POST` | `/api/vault/generate` | Generate + save a password |

**Example — Forge a password:**
```bash
curl -X POST http://localhost:7070/api/vault/generate \
  -H "Content-Type: application/json" \
  -d '{"accountName": "Gmail Main", "length": 24}'
```

```json
{
  "success": true,
  "status": "KEY_FORGED",
  "data": {
    "accountName": "Gmail Main",
    "password": "mK#9@vLzQ2!nPxR3$dWsY7&f",
    "length": 24
  }
}
```

---

## Project Structure

```
Data-Forger/
│
├── src/
│   └── main/
│       ├── java/com/dataforger/
│       │   ├── DataForgerServer.java       ← Javalin HTTP server + REST routes
│       │   ├── IdentityForgerService.java  ← Ghost identity engine (254 names)
│       │   └── TitanVaultService.java      ← Password forge (length 10–32)
│       │
│       └── resources/static/
│           └── index.html                  ← Unified CRT web UI (all 5 panels)
│
├── TitanIdentity.java                      ← Standalone CLI version
├── TitanVault.java                         ← Standalone CLI version
├── pom.xml                                 ← Maven build (Javalin + Jackson)
├── run.bat                                 ← Windows launcher
├── run.sh                                  ← Unix/Mac launcher
├── logo.png
└── README.md
```

### Output Files *(git-ignored — never committed)*

| File | Contents |
|------|----------|
| `titan_identities.txt` | Saved ghost profiles with tags |
| `titan_keys.txt` | Timestamped account:password pairs |

> [!CAUTION]
> These files contain sensitive generated data. They are excluded from git via `.gitignore`. **Never commit them.**

---

## Standalone CLI Mode

You can also run each module directly without Maven or the web server:

```bash
# Compile
javac TitanIdentity.java
javac TitanVault.java

# Run
java TitanIdentity
java TitanVault
```

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| HTTP Server | Javalin 6.3 (embedded Jetty) |
| JSON | Jackson Databind 2.17 |
| Build | Apache Maven 3.8+ |
| Frontend | Vanilla HTML/CSS/JS (no framework) |
| Fonts | VT323, Fira Code, Space Grotesk |
| Randomness | `java.security.SecureRandom` |

---

## Security & Ethics

> [!WARNING]
> This tool is for **educational, ethical testing, and privacy-aware development** purposes only.
> - Do **not** use generated identities to deceive or impersonate real individuals
> - Do **not** use this tool for unauthorized account creation
> - Always follow your country's privacy and cybersecurity laws

---

<div align="center">

**// GHOST SYSTEM ONLINE //**  
`root@forger:~# _`

</div>
