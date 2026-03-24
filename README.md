<div align="center">

<img src="logo.png" alt="DATA-FORGER Logo" width="200px"/>

# DATA-FORGER // GHOST SYSTEM

**Cybersecurity synthetic data toolkit — forge identities & keys**

[![Java](https://img.shields.io/badge/Java-17%2B-3cf91a?style=flat-square&logo=openjdk&logoColor=black&labelColor=111)](https://adoptium.net/)
[![Zero Dependencies](https://img.shields.io/badge/Dependencies-ZERO-3cf91a?style=flat-square&labelColor=111)](.)
[![License](https://img.shields.io/badge/Use-Educational-3cf91a?style=flat-square&labelColor=111)](.)

</div>

---

```
'########:::::'###::::'########::::'###:::::::'########::'#######::'########:::'######:::'########:
 ##.... ##:::'## ##:::... ##..::::'## ##:::::: ##.....::'##.... ##: ##.... ##:'##... ##:: ##.....::
 ##:::: ##::'##:. ##::::: ##:::::'##:. ##::::: ##::::::: ##:::: ##: ##:::: ##: ##:::..::: ##:::::::
 ##:::: ##:'##:::. ##:::: ##::::'##:::. ##:::: ######::: ##:::: ##: ########:: ##::'####: ######:::
 ##:::: ##: #########:::: ##:::: #########:::: ##...:::: ##:::: ##: ##.. ##::: ##::: ##:: ##...::::
 ##:::: ##: ##.... ##:::: ##:::: ##.... ##:::: ##::::::: ##:::: ##: ##::. ##:: ##::: ##:: ##:::::::
 ########:: ##:::: ##:::: ##:::: ##:::: ##:::: ##:::::::. #######:: ##:::. ##:. ######::: ########:
........:::..:::::..:::::..:::::..:::::..:::::..:::::::::.......:::..:::::..:::......::::........::
```

## Overview

DATA-FORGER is a **Java 17** cybersecurity toolkit with a browser-based CRT-aesthetic UI served by Java's built-in HTTP server — **zero external dependencies, no Maven, no Node.js.**

| Module | What it does |
|--------|-------------|
| **Identity Forger** | Generates ghost personas from 254 globally-diverse names |
| **TitanVault** | Forges cryptographic passwords, length slider 10–32 chars |

---

## Quick Start

### Prerequisites
- **[Java 17+](https://adoptium.net/)** — that's literally it

### Run

```batch
git clone https://github.com/Dhanvin-kadiir/Data-Forger.git
cd Data-Forger
run.bat
```

Then open **http://localhost:7070** in your browser (it opens automatically).

> `run.bat` compiles the 3 Java files with `javac` and starts the server. No build tools needed.

---

## Modules

### Identity Forger
- **254 unique first names** across 12 cultural groups: Western, Russian, Japanese, Korean, Chinese, Indian, Arabic, African, Latin, Nordic, Gender-Neutral, Cyber Handles
- `SecureRandom` — cryptographically unpredictable selection
- Configurable tag-based saving to `titan_identities.txt`

### TitanVault  
- Password length **10 – 32 characters** (slider in UI, clamped server-side)
- Guarantees ≥1 char from every class: lowercase + uppercase + digits + special (90-char pool)
- Fisher-Yates shuffle ensures no predictable ordering
- ~**131 bits of entropy** at 32-char max length
- Auto-saved with timestamp to `titan_keys.txt`

---

## REST API

The Java server exposes a simple JSON API the UI calls via `fetch()`:

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET`  | `/api/status` | Health check |
| `POST` | `/api/identity/forge` | Generate a ghost identity |
| `POST` | `/api/identity/save` | Save identity to vault file |
| `POST` | `/api/vault/generate` | Generate + auto-save password |

**Example:**
```bash
curl -X POST http://localhost:7070/api/vault/generate \
  -H "Content-Type: application/json" \
  -d "{\"accountName\":\"Gmail\",\"length\":24}"
```
```json
{"success":true,"status":"KEY_FORGED","data":{"accountName":"Gmail","password":"mK#9@vQ2!nR3$dWsY7","length":24}}
```

---

## Project Structure

```
Data-Forger/
|
|-- DataForgerServer.java       <- HTTP server + REST routes (built-in HttpServer)
|-- IdentityForgerService.java  <- Ghost identity engine (254 names)
|-- TitanVaultService.java      <- Password forge (length 10-32)
|-- TitanIdentity.java          <- Standalone CLI version (no server needed)
|-- TitanVault.java             <- Standalone CLI version (no server needed)
|
|-- static/
|   `-- index.html              <- Full CRT web UI (5 panels, vanilla JS)
|
|-- run.bat                     <- Windows: compile + launch
|-- logo.png
`-- README.md
```

### Runtime output files *(git-ignored — never committed)*

| File | Contents |
|------|----------|
| `titan_identities.txt` | Saved ghost profiles with tags |
| `titan_keys.txt` | Timestamped account : password pairs |

> [!CAUTION]
> These files contain generated sensitive data. They are excluded by `.gitignore`. **Never commit them.**

---

## Standalone CLI (No Server)

Each module also works as a plain command-line program:

```bash
# Identity Forger
javac -encoding UTF-8 TitanIdentity.java
java TitanIdentity

# TitanVault
javac -encoding UTF-8 TitanVault.java
java TitanVault
```

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| HTTP Server | `com.sun.net.httpserver.HttpServer` (JDK built-in) |
| Randomness | `java.security.SecureRandom` |
| Frontend | Vanilla HTML / CSS / JS |
| Fonts | VT323, Share Tech Mono, Orbitron (Google Fonts) |
| Build | None — plain `javac` |
| Dependencies | **Zero** |

---

> [!WARNING]
> For **educational and ethical testing purposes only.** Do not use generated identities to impersonate real people or create unauthorized accounts.

<div align="center">

`root@forger:~# _`

</div>
