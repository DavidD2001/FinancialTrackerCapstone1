<<<<<<< HEAD
# Financial Tracker (Java)

This is a simple Java project I built to track money going in and out. You can enter deposits (like getting paid) or payments (like buying something), and it saves everything to a file so you can keep track.

I built this project to practice using basic Java. It helped me understand how to use things like `ArrayList`, loops, methods, and file handling.

---

## What It Can Do

- Add a new transaction (either a deposit or payment)
- Save each transaction to a file called `transactions.csv`
- View all your transactions, or just deposits or just payments
- Run reports like:
  - This month so far
  - Last month
  - This year so far
  - Last year
  - Search by vendor name
  - Custom search (start date, end date, description, vendor, or amount)

---

## How to Run It

1. Open the project in IntelliJ (or any Java IDE)
2. Run the `TransactionApp.java` file
3. Use the terminal to pick options from the menu

---

## Example Code (Adding a Transaction)

Here's the method that adds a new transaction:

```java
public static void addTransaction(Scanner scanner, boolean isDeposit) {
    System.out.print("Enter description: ");
    String description = scanner.nextLine();

    System.out.print("Enter vendor: ");
    String vendor = scanner.nextLine();

    System.out.print("Enter amount: ");
    double amount = Double.parseDouble(scanner.nextLine());

    if (!isDeposit) {
        amount *= -1; // payments are negative numbers
    }

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

    Transaction t = new Transaction(date, time, description, vendor, amount);
    TransactionFileHandler.writeTransaction(t);
    System.out.println("Transaction saved.");
}
```

This code gets input from the user, figures out the current date and time, and then writes the transaction to the file.

---

## How the File Looks

Saved transactions look like this in `transactions.csv`:
```
2025-05-01|14:32:45|Bought groceries|Safeway|-42.99
```

---

## Why I Built This

I wanted to build something simple but useful to get more comfortable with Java. It helped me get better at using methods, working with data, and using real files to store information.

---

## Git History

I committed changes as I worked on different parts, like:
- Starting the project
- Adding deposit/payment options
- Creating the reports menu
- Making the custom search
- Writing the README

The commits show my progress step-by-step.
=======
# FinancialTrackerCapstone1
An Application to keep track of your transactions
>>>>>>> f82ff38238f3e8f2e7116d8b5ec7696804b0f9e3
