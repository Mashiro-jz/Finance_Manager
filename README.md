# Finance Manager CLI 💰

Prosta aplikacja konsolowa napisana w Javie, służąca do zarządzania domowym budżetem. Umożliwia śledzenie przychodów i wydatków, analizowanie kosztów według kategorii oraz trwały zapis danych do pliku JSON. Projekt został zrealizowany z podziałem na warstwy (architektura zbliżona do Clean Architecture).

## 🚀 Funkcjonalności

* **Dodawanie transakcji:** Zapisuj przychody i wydatki, określając kwotę, opis, typ oraz kategorię (np. jedzenie, transport, długi).
* **Usuwanie transakcji:** Możliwość usunięcia konkretnej transakcji za pomocą jej unikalnego identyfikatora UUID.
* **Przeglądanie historii:** Przejrzysta tabela ze wszystkimi zapisanymi transakcjami.
* **Obliczanie salda:** Szybki podgląd aktualnego balansu konta (przychody minus wydatki).
* **Analiza wydatków:**
  * Sumowanie wydatków według kategorii.
  * Wyszukiwanie największego wydatku w podanym miesiącu i roku.
* **Trwałość danych:** Wszystkie transakcje są automatycznie zapisywane w pliku `transaction.json`.

## 🛠️ Technologie

* **Język:** Java
* **Biblioteki:** Jackson (`jackson-databind`, `jackson-datatype-jsr310`) do serializacji i deserializacji danych w formacie JSON.
* **Architektura:** Warstwowa (Domain, Application, Infrastructure, Presentation).

## 📁 Struktura projektu

* `domain` - Modele danych (Transakcja, Kategorie, Typy) oraz interfejsy repozytoriów.
* `application` - Logika biznesowa (`TransactionService`).
* `infrastructure` - Implementacja dostępu do danych (zapis/odczyt z pliku JSON).
* `presentation` - Interfejs użytkownika (CLI w konsoli).

## ⚙️ Uruchomienie

1. Sklonuj repozytorium.
2. Upewnij się, że masz dodane zależności Jacksona w swoim pliku konfiguracyjnym (np. `pom.xml` dla Mavena).
3. **Ważne:** W klasie `JsonTransactionRepository` znajduje się ścieżka do pliku zapisu. Zaktualizuj zmienną `filePath` na odpowiednią dla Twojego środowiska (np. ścieżkę względną `transaction.json`), aby zapis działał poprawnie.
4. Uruchom klasę `Main.java`.

## 📌 Planowane udoskonalenia (To-Do)

* Dodanie własnych wyjątków (Custom Exceptions) do lepszej obsługi błędów wejścia.
* Napisanie testów jednostkowych przy użyciu JUnit.
