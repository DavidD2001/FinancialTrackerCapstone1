public class Transaction {
    private String date;        // Example: 2025-05-01
    private String time;        // Example: 14:23:50
    private String description;
    private String vendor;
    private double amount;

    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isDeposit() {
        return amount > 0;
    }

    public boolean isPayment() {
        return amount < 0;
    }

    public String toCSVString() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

    public String toString() {
        return date + " " + time + " | " + description + " | " + vendor + " | $" + amount;
    }
}
