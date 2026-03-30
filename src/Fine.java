import java.util.Date;
public class Fine {
    //private fields
    private String fineId;
    private int overdueDays;
    private float perDayCharge;
    private float totalFineAmount;
    private String status; //paid or not paid
    private Date issuedDate;
    private Date paidDate;

    //constructor
    public Fine(String fineId, int overdueDays, float perDayCharge) {
        this.fineId = fineId;
        this.overdueDays = overdueDays;
        this.perDayCharge = perDayCharge;
        this.totalFineAmount = 0;
        this.status = "UNPAID";
        this.issuedDate = new Date();
        this.paidDate = null;
    }

    //getter
    public String getFineId() { return fineId; }
    public int getOverdueDays() { return overdueDays; }
    public float getPerDayCharge() { return perDayCharge; }
    public float getTotalFineAmount() { return totalFineAmount; }
    public String getStatus() { return status; }
    public Date getIssuedDate() { return issuedDate; }
    public Date getPaidDate() { return paidDate; }

    //total fine
    //overduedays and perdaycharge is set by admine
    public void calculateFine() {
        totalFineAmount = overdueDays * perDayCharge;
        System.out.println("Fine calculated: Rs." + totalFineAmount);
    }
    
    //if already paid-> print. or mark paid
    public void payFine() {
        if (status.equals("PAID")) {
            System.out.println("Fine already paid.");
            return;
        }
        status = "PAID";
        paidDate = new Date();
        System.out.println("Fine paid successfully on: " + paidDate);
    }

    public void displayFine() {
        System.out.println("Fine ID: " + fineId);
        System.out.println("Overdue Days: " + overdueDays);
        System.out.println("Per Day Charge: Rs." + perDayCharge);
        System.out.println("Total Fine: Rs." + totalFineAmount);
        System.out.println("Status: " + status);
        System.out.println("Issued Date: " + issuedDate);
        System.out.println("Paid Date: " + paidDate);
    }
}