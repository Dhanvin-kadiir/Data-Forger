# DataForger 🛠️

<p align="center">
  <img src="logo.png" alt="DataForger Logo" width="300px">
</p>

DataForger is a powerful, lightweight suite of synthetic data generation tools designed for privacy-conscious developers. It simplifies the process of creating "ghost profiles" for testing and generates cryptographically secure credentials.

---

## 🚀 Features

### 👤 Titan Identity
A "Ghost Profile" generator that creates comprehensive synthetic personas.
- **Randomized Alias Generation**: Diverse names from various cultural backgrounds (Western, Russian, Japanese, Indian).
- **Date of Birth Logic**: Synthetic age calculation (19 - 45 years).
- **Global Origin**: Locations from major world cities.
- **Persistent Storage**: Save generated identities with custom tags (e.g., *Steam Alt*, *Burner*).

### 🔐 Titan Vault
A high-security credential generator for robust account management.
- **Secure Randomness**: Uses `SecureRandom` for cryptographic safety.
- **Complexity Enforcement**: Guarantees at least one uppercase, lowercase, digit, and special character.
- **20-Character Default**: Generates long, complex passwords by default.
- **Vault Logging**: Automatically saves credentials with timestamps to an encrypted-ready text vault.

---

## 🛠️ Getting Started

### Prerequisites
- **Java Development Kit (JDK)** 8 or higher.

### Installation
Clone the repository:
```bash
git clone https://github.com/Dhanvin-kadiir/Data-Forger.git
cd dataforger
```

---

## 🚦 Usage

### Running Titan Identity
Generate synthetic identities for testing or burner profiles:
```bash
javac TitanIdentity.java
java TitanIdentity
```

### Running Titan Vault
Generate a secure, randomized password for a new account:
```bash
javac TitanVault.java
java TitanVault
```

---

## 📁 File Storage
The tools automatically manage local data files:
- **`titan_identities.txt`**: Stores saved ghost profiles.
- **`titan_keys.txt`**: Logs secure passwords and account names.

> [!CAUTION]
> These files contain sensitive information. Ensure they are excluded from public repositories. The `.gitignore` provided handles this by default.

---

## 📂 Project Structure
```text
dataforger/
├── TitanIdentity.java     # Identity generator source
├── TitanVault.java        # Password vault source
├── logo.png               # Project imagery
├── .gitignore             # Git exclusion rules
└── README.md              # Project documentation
```

---

## ⚖️ License
This project is for educational and synthetic data generation purposes only. Use responsibly.
