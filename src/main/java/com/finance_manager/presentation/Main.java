package com.finance_manager.presentation;

import com.finance_manager.application.service.TransactionService;
import com.finance_manager.domain.model.Transaction;
import com.finance_manager.domain.model.TransactionCategory;
import com.finance_manager.domain.model.TransactionType;
import com.finance_manager.infrastructure.repository.JsonTransactionRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        JsonTransactionRepository repo = new JsonTransactionRepository();
        TransactionService service = new TransactionService(repo);
        Scanner scanner = new Scanner(System.in);

        while(true){
            // TODO: ZROBIĆ WŁASNE BŁĘDY ORAZ WYKORZYSTAĆ JE, ZROBIĆ TESTY Z JUNIT
            System.out.println("\nWybierz operację:");
            System.out.println("1. Dodaj transakcję");
            System.out.println("2. Usuń transakcję");
            System.out.println("3. Pokaż transakcję");
            System.out.println("4. Pokaż balans konta");
            System.out.println("5. Pokaż wydatki według kategorii");
            System.out.println("6. Pokaż największy wydatek w danym miesiącu");
            System.out.println("7. Wyjście");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1: {
                        BigDecimal amount = getTransactionAmount(scanner);
                        String desc = getTransactionDesc(scanner);
                        TransactionType transactionType = getTransactionEnum(scanner, TransactionType.class);
                        TransactionCategory transactionCategory = getTransactionEnum(scanner, TransactionCategory.class);
                        service.addTransaction(amount, desc, transactionType, transactionCategory);
                        break;
                    }
                    case 2: {
                        System.out.println("Podaj ID transakcji:");
                        try{
                            UUID id = UUID.fromString(scanner.nextLine());
                            service.removeTransaction(id);
                            System.out.println("Usunięto transakcję.");
                        } catch (Exception e) {
                            System.out.println("Podano złe ID.");
                        }
                        break;
                    }
                    case 3: {
                        List<Transaction> transactions = service.getSortedByDate();
                        if (transactions.isEmpty()) {
                            System.out.println("Brak transakcji do wyświetlenia.");
                        } else {
                            System.out.printf("%-36s | %-10s | %-20s | %-10s | %-8s | %-10s%n",
                                    "ID", "Data", "Opis", "Kwota", "Typ", "Kategoria");
                            System.out.println("---------------------------------------------------------------------------------------------------");
                            for (Transaction t : transactions) {
                                System.out.printf("%-36s | %-10s | %-20s | %-10s | %-8s | %-10s%n",
                                        t.getId(),
                                        t.getDate(),
                                        t.getDescription(),
                                        t.getAmount(),
                                        formatEnum(t.getTransactionType()),
                                        formatEnum(t.getTransactionCategory()));
                            }
                        }
                        break;
                    }
                    case 4: {
                        System.out.println("Balans konta: " + service.getBalance());
                        break;
                    }
                    case 5: {
                        var outgoes = service.getOutgoesByCategory();
                        if (outgoes.isEmpty()) {
                            System.out.println("Brak wydatków do wyświetlenia.");
                        } else {
                            System.out.println("Wydatki na kategorie:");
                            System.out.printf("%-10s | %-10s%n", "Kategoria", "Kwota");
                            System.out.println("-------------------------------");
                            outgoes.forEach((cat, amount) -> System.out.printf("%-10s | %-10s%n", formatEnum(cat), amount));
                        }
                        break;
                    }
                    case 6: {
                        System.out.println("Podaj miesiąc:");
                        int month = scanner.nextInt();
                        System.out.println("Podaj rok:");
                        int year = scanner.nextInt();
                        scanner.nextLine();

                        if(year > 0 && month >= 1 && month <= 12) {
                            service.getHighestOutgoInCurrentMonth(month, year)
                                    .ifPresentOrElse(
                                            t -> System.out.printf("Największy wydatek: %s | %s | %s%n",
                                                    t.getDate(),
                                                    t.getDescription(),
                                                    t.getAmount()),
                                            () -> System.out.println("Brak wydatków w podanym miesiącu.")
                                    );
                        } else {
                            System.out.println("Podano niepoprawną datę.");
                        }
                        break;
                    }
                    case 7: {
                        scanner.close();
                        return;
                    }
                    default: {
                        System.out.println("Niepoprawny wybór operacji, spróbuj ponownie.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Niepoprawny wybór operacji, spróbuj ponownie.");
                scanner.nextLine();
            }
        }
    }

    private static BigDecimal getTransactionAmount(Scanner scanner) {
        BigDecimal amount = null;
        while(true){
            System.out.println("Podaj kwotę:");
            try {
                amount = scanner.nextBigDecimal();
                scanner.nextLine();
                if(amount.compareTo(BigDecimal.ZERO) <= 0)
                    System.out.println("Kwota musi być większa niż 0.");
                else
                    return amount;
            } catch (Exception e) {
                System.out.println("Podano niewłaściwą kwotę.");
                scanner.nextLine();
            }
        }
    }

    private static String getTransactionDesc(Scanner scanner) {
        System.out.println("Podaj opis:");
        return scanner.nextLine().trim();
    }

    private static <T extends Enum<T>> T getTransactionEnum(Scanner scanner, Class<T> enumClass) {
        int choice;
        T[] values = enumClass.getEnumConstants();

        while (true){
            System.out.println("Wybierz opcję:");
            for (int i = 1; i <= values.length; i++) {
                System.out.println(i + ". " + formatEnum(values[i-1]));
            }
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if(choice >= 1 && choice <= values.length)
                    return values[choice-1];
                else
                    System.out.println("Nieprawidłowy wybór, spróbuj ponownie.");
            } catch (Exception e) {
                System.out.println("Nieprawidłowy wybór, spróbuj ponownie.");
                scanner.nextLine();
            }
        }
    }

    private static String formatEnum(Enum<?> e) {
        String name = e.name().toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}