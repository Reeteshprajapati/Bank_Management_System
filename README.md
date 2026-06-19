# Bank Management System

A simple, robust, console-based Java application implementing core banking operations. It is designed to demonstrate basic OOP concepts, control flow, user input handling, and clean code principles in Java.

---

## 🛠 Features

1. **Create Account**: Initialise a new bank account with the customer's name, account number, and an initial opening balance.
2. **Deposit Money**: Deposit any positive amount to increase the account balance.
3. **Withdraw Money**: Withdraw funds from the account (validates sufficient balance and positive amount).
4. **Check Balance**: View the current active balance.
5. **Display Account Details**: Show a summarized card containing the account holder's name, account number, and current balance.
6. **Exit System**: Safely terminates the console loop.

---

## 💻 Tech Stack & Concepts Used

*   **Language**: Java (JDK 8 or above)
*   **OOP Concepts**: Encapsulation (private fields, public methods), Classes, and Objects.
*   **Scanner Class**: Captures and processes command-line inputs safely.
*   **Input Validation**: Safe string and decimal parsing using Exception Handling (`NumberFormatException`) to prevent application crashes when invalid inputs are provided.

---

## 📂 Project Structure

```text
Back_Management_System/
│
├── BankAccount.java   # Contains the account fields, getters, deposits/withdrawals, and verification logic
├── BankSystem.java    # Handles the user menu loop, switch-case commands, and Console Scanner inputs
└── README.md          # Project documentation and run guide
```

---

## 🚀 How to Run

Follow these steps to compile and run the application from your terminal:

### 1. Open Terminal
Navigate to the project root directory:
```bash
cd c:\Users\HP\Desktop\Back_Management_System
```

### 2. Compile Java Files
Compile both `.java` source files using `javac`:
```bash
javac BankAccount.java BankSystem.java
```

### 3. Run the Application
Start the compiled bytecode file using the `java` runner:
```bash
java BankSystem
```

---

## 🔮 Future Scope
*   Adding support for multiple user accounts using `ArrayList`.
*   Implementing account security with a secure PIN.
*   Keeping track of transaction history.
*   Writing customer data to a file for persistent storage.
