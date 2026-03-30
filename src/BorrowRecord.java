//CLASS: BorrowRecord
import java.util.Date;

public class BorrowRecord {
    //private fields
    private String recordId;
    private Date borrowDate;
    private Date dueDate;//deadline date
    private Date returnDate;//when actually they returned
    private String status;//borrow status(active or closed)
    private String bookIsbn;

    //constructor
    public BorrowRecord(String recordId, Date dueDate, String bookIsbn) {
        this.recordId = recordId;
        this.borrowDate = new Date();
        this.dueDate = dueDate;
        this.returnDate = null;
        this.status = "ACTIVE";
        this.bookIsbn = bookIsbn;
    }

    public String getRecordId() { return recordId; }
    public Date getBorrowDate() { return borrowDate; }
    public Date getDueDate() { return dueDate; }
    public Date getReturnDate() { return returnDate; }
    public String getStatus() { return status; }
    public String getBookIsbn() { return bookIsbn; }

    //active confirmation(borrow)
    public void createRecord() {
        status = "ACTIVE";
        System.out.println("Borrow record created. Record ID: " + recordId);
        System.out.println("Borrow Date: " + borrowDate);
        System.out.println("Due Date: " + dueDate);
    }

    //closed confirmation(return)
    public void closeRecord() {
        if (status.equals("CLOSED")) {
            System.out.println("Record already closed.");
            return;
        }
        status = "CLOSED";
        returnDate = new Date();
        System.out.println("Record closed. Book returned on: " + returnDate);
    }

    //check the borrow status
    public String checkStatus() {
        return status;
    }

    public void displayRecord() {
        System.out.println("Record ID: " + recordId);
        System.out.println("Book ISBN: " + bookIsbn);
        System.out.println("Borrow Date: " + borrowDate);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Return Date: " + returnDate);
        System.out.println("Status: " + status);
    }
}