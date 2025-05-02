<<<<<<< HEAD
# Financial Tracker (Java)

This is the Capstone Java project I built to track money going in and out. You can enter deposits (like getting paid) or payments (like buying something), and it saves everything to a file so you can keep track.

I built this project to test my basic understanding of Java. It helped me understand how to use things like `ArrayList`, loops, methods, and file handling to a better degree than when i started.

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

## Git History

Mon. 04/27
- Set up project folder and initialized Git repository
- Created Transaction class to represent a single financial entry

Tues. 04/28
- Added TransactionFileHandler to handle reading/writing CSV file
- Started TransactionApp with home menu and user input
- Implemented basic deposit and payment entry system

Wed. 04/29
- Added ledger view with options for all, deposits, and payments
- Sorted transactions by newest first in the ledger

Thurs. 04/30
- Built reports menu with month-to-date and previous month filters
- Added year-to-date and previous year report filters
- Implemented vendor search feature in reports
- Added custom search by date, vendor, description, and amount
- Improved error handling for invalid input in custom search
- Moved Java files from Maven structure to project root
- Cleaned up old README and added project description
- Wrote clear, beginner-style README with code example and features
- Final commit: resolved merge conflicts and pushed project to GitHub

**Cool peice of code**
if ((startDate == null || !tDate.before(startDate)) &&
    (endDate == null || !tDate.after(endDate)) &&
    (descInput.isEmpty() || t.getDescription().toLowerCase().contains(descInput)) &&
    (vendorInput.isEmpty() || t.getVendor().toLowerCase().contains(vendorInput)) &&
    (amount == null || t.getAmount() == amount)) {
    System.out.println(t);
}
(One of the cooler parts of this project for me was building the custom search feature. It lets you look through your transactions by setting a start and end date, entering part of a description or vendor name, or even matching a specific amount.

The part I like is how flexible it is — you don’t have to fill out every field. If you leave something blank, the code just skips that filter instead of crashing or giving an error. That made it feel more like a real banking app.)

**Photos**
<img width="651" alt="Screenshot 2025-05-01 at 10 31 57 PM" src="https://github.com/user-attachments/assets/083748bd-2646-4b4b-9d69-03bd9a431507" />
<img width="579" alt="Screenshot 2025-05-01 at 8 59 52 PM" src="https://github.com/user-attachments/assets/24775f30-fbd2-4a90-9cf2-46f160c034fb" />


The commits show my progress step-by-step.
=======
# FinancialTrackerCapstone1
An Application to keep track of your transactions
>>>>>>> f82ff38238f3e8f2e7116d8b5ec7696804b0f9e3
