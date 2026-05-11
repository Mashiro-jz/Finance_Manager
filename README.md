# Finance Manager CLI 💰

A simple console application written in Java for managing a personal budget. It allows tracking income and expenses, analyzing costs by category, and persistently saving data to a JSON file. The project was built using a layered architecture (similar to Clean Architecture).

## 🚀 Features

* **Add transactions:** Record income and expenses by specifying the amount, description, type, and category (e.g., food, transport, debt).
* **Remove transactions:** Delete a specific transaction using its unique UUID.
* **View history:** A clear table displaying all saved transactions.
* **Calculate balance:** Quick overview of the current account balance (income minus expenses).
* **Expense analysis:**
  * Summing up expenses by category.
  * Finding the highest expense in a given month and year.
* **Data persistence:** All transactions are automatically saved in the `transaction.json` file.

## 🛠️ Technologies

* **Language:** Java
* **Libraries:** Jackson (`jackson-databind`, `jackson-datatype-jsr310`) for JSON data serialization and deserialization.
* **Architecture:** Layered (Domain, Application, Infrastructure, Presentation).

## 📁 Project Structure

* `domain` - Data models (Transaction, Categories, Types) and repository interfaces.
* `application` - Business logic (`TransactionService`).
* `infrastructure` - Data access implementation (read/write from/to a JSON file).
* `presentation` - User interface (Console CLI).

## ⚙️ Getting Started

1. Clone the repository.
2. Make sure you have added the Jackson dependencies in your configuration file (e.g., `pom.xml` for Maven).
3. **Important:** The `JsonTransactionRepository` class contains the save file path. Update the `filePath` variable to match your environment (e.g., a relative path like `transaction.json`) for saving to work correctly.
4. Run the `Main.java` class.

## 📌 Planned Improvements (To-Do)

* Add Custom Exceptions for better input error handling.
* Write unit tests using JUnit.
