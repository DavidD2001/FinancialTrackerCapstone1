import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;

public class TransactionApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice = "";

        while (!choice.equalsIgnoreCase("X")) {
            System.out.println("\n==== Home Menu ====");
            System.out.println("(D) Add Deposit");
            System.out.println("(P) Make Payment");
            System.out.println("(L) View Ledger");
            System.out.println("(X) Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextLine().toUpperCase();

            if (choice.equals("D")) {
                addTransaction(scanner, true);
            } else if (choice.equals("P")) {
                addTransaction(scanner, false);
            } else if (choice.equals("L")) {
                showLedger(scanner);
            } else if (!choice.equals("X")) {
                System.out.println("Invalid option. Try again.");
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    public static void addTransaction(Scanner scanner, boolean isDeposit) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (!isDeposit) {
            amount *= -1;
        }

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        Transaction t = new Transaction(date, time, description, vendor, amount);
        TransactionFileHandler.writeTransaction(t);
        System.out.println("Transaction saved.");
    }

    public static void showLedger(Scanner scanner) {
        ArrayList<Transaction> transactions = TransactionFileHandler.readTransactions();
        sortTransactionsByNewest(transactions);

        System.out.println("\n==== Ledger Menu ====");
        System.out.println("(A) All Transactions");
        System.out.println("(D) Deposits Only");
        System.out.println("(P) Payments Only");
        System.out.println("(R) Reports");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine().toUpperCase();

        if (choice.equals("A")) {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        } else if (choice.equals("D")) {
            for (Transaction t : transactions) {
                if (t.isDeposit()) {
                    System.out.println(t);
                }
            }
        } else if (choice.equals("P")) {
            for (Transaction t : transactions) {
                if (t.isPayment()) {
                    System.out.println(t);
                }
            }
        } else if (choice.equals("R")) {
            showReportsMenu(scanner, transactions);
        } else {
            System.out.println("Invalid option.");
        }
    }

    public static void showReportsMenu(Scanner scanner, ArrayList<Transaction> transactions) {
        String choice = "";

        while (!choice.equals("0")) {
            System.out.println("\n==== Reports Menu ====");
            System.out.println("(1) Month to Date");
            System.out.println("(2) Previous Month");
            System.out.println("(3) Year to Date");
            System.out.println("(4) Previous Year");
            System.out.println("(5) Search by Vendor");
            System.out.println("(6) Custom Search");
            System.out.println("(0) Back");
            System.out.print("Choose an option: ");
            choice = scanner.nextLine();

            if (choice.equals("1")) {
                printTransactionsInRange(transactions, getStartOfCurrentMonth(), new Date());
            } else if (choice.equals("2")) {
                printTransactionsInRange(transactions, getStartOfPreviousMonth(), getEndOfPreviousMonth());
            } else if (choice.equals("3")) {
                printTransactionsInRange(transactions, getStartOfCurrentYear(), new Date());
            } else if (choice.equals("4")) {
                printTransactionsInRange(transactions, getStartOfPreviousYear(), getEndOfPreviousYear());
            } else if (choice.equals("5")) {
                System.out.print("Enter vendor name: ");
                String vendor = scanner.nextLine().toLowerCase();
                for (Transaction t : transactions) {
                    if (t.getVendor().toLowerCase().contains(vendor)) {
                        System.out.println(t);
                    }
                }
            } else if (choice.equals("6")) {
                runCustomSearch(scanner, transactions);
            } else if (!choice.equals("0")) {
                System.out.println("Invalid option.");
            }
        }
    }

    public static void runCustomSearch(Scanner scanner, ArrayList<Transaction> transactions) {
        System.out.print("Enter start date (YYYY-MM-DD) or leave blank: ");
        String startInput = scanner.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD) or leave blank: ");
        String endInput = scanner.nextLine();
        System.out.print("Enter description keyword or leave blank: ");
        String descInput = scanner.nextLine().toLowerCase();
        System.out.print("Enter vendor keyword or leave blank: ");
        String vendorInput = scanner.nextLine().toLowerCase();
        System.out.print("Enter amount (exact match) or leave blank: ");
        String amountInput = scanner.nextLine();

        Date startDate = null, endDate = null;
        Double amount = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            if (!startInput.isEmpty()) {
                startDate = format.parse(startInput + " 00:00:00");
            }
            if (!endInput.isEmpty()) {
                endDate = format.parse(endInput + " 23:59:59");
            }
            if (!amountInput.isEmpty()) {
                amount = Double.parseDouble(amountInput);
            }
        } catch (ParseException | NumberFormatException e) {
            System.out.println("Invalid input. Returning to reports menu.");
            return;
        }

        System.out.println("\nCustom Search Results:");
        for (Transaction t : transactions) {
            try {
                Date tDate = format.parse(t.getDate() + " " + t.getTime());
                boolean dateInRange = (startDate == null || !tDate.before(startDate)) && (endDate == null || !tDate.after(endDate));
                boolean matchesDesc = descInput.isEmpty() || t.getDescription().toLowerCase().contains(descInput);
                boolean matchesVendor = vendorInput.isEmpty() || t.getVendor().toLowerCase().contains(vendorInput);
                boolean matchesAmount = (amount == null || t.getAmount() == amount);

                if (dateInRange && matchesDesc && matchesVendor && matchesAmount) {
                    System.out.println(t);
                }
            } catch (ParseException e) {
                System.out.println("Skipping malformed date: " + t.getDate());
            }
        }
    }

    public static void sortTransactionsByNewest(ArrayList<Transaction> transactions) {
        Collections.sort(transactions, new Comparator<Transaction>() {
            public int compare(Transaction t1, Transaction t2) {
                try {
                    String format = "yyyy-MM-dd HH:mm:ss";
                    Date d1 = new SimpleDateFormat(format).parse(t1.getDate() + " " + t1.getTime());
                    Date d2 = new SimpleDateFormat(format).parse(t2.getDate() + " " + t2.getTime());
                    return d2.compareTo(d1); // newest first
                } catch (ParseException e) {
                    return 0;
                }
            }
        });
    }

    public static void printTransactionsInRange(ArrayList<Transaction> transactions, Date start, Date end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Transaction t : transactions) {
            try {
                Date tDate = format.parse(t.getDate() + " " + t.getTime());
                if (!tDate.before(start) && !tDate.after(end)) {
                    System.out.println(t);
                }
            } catch (ParseException e) {
                System.out.println("Skipping malformed date: " + t.getDate());
            }
        }
    }

    public static Date getStartOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getStartOfPreviousMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getEndOfPreviousMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date getStartOfCurrentYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getStartOfPreviousYear() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getEndOfPreviousYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
