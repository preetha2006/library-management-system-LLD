import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//extends from the authentication.java
public class Member extends Authentication {
    //private fields
    private String memberId;
    private String name;
    private String phone;
    private String email;
    private int borrowLimit;
    private ArrayList<BorrowRecord> borrowHistory;
    private ArrayList<Fine> fines;

    //constructor
    public Member(String memberId, String name, String phone, String email, String password, int borrowLimit) {
        super(name, password);
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.borrowLimit = borrowLimit;
        this.borrowHistory = new ArrayList<>();
        this.fines = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public int getBorrowLimit() { return borrowLimit; }
    public ArrayList<BorrowRecord> getBorrowHistory() { return borrowHistory; }
    public ArrayList<Fine> getFines() { return fines; }

    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setBorrowLimit(int borrowLimit) { this.borrowLimit = borrowLimit; }

    public static Member signup(java.util.Scanner sc) {
        System.out.print("Member ID: "); String id = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Phone: "); String phone = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();

        if (usernameExists("members.txt", name)) {
            System.out.println("Username already exists.");
            return null;
        }

        String password;
        while (true) {
            System.out.print("Password: "); password = sc.nextLine();
            if (validatePassword(password)) break;
        }

        Member m = new Member(id, name, phone, email, password, 3);
        saveToFile("members.txt", id + "," + name + "," + phone + "," + email + "," + password + ",3");
        System.out.println("Member registered successfully!");
        return m;
    }

    public static Member login(java.util.Scanner sc, List<Member> members) {
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Password: "); String password = sc.nextLine();
        for (Member m : members) {
            if (m.getName().equalsIgnoreCase(name) && m.getPassword().equals(password)) {
                System.out.println("Welcome, " + name + "!");
                return m;
            }
        }
        System.out.println("Invalid name or password.");
        return null;
    }

    public void borrowBook(Book book, String recordId, Date dueDate) {
        if (!book.checkAvailability()) { System.out.println("Book not available."); return; }
        if (borrowHistory.size() >= borrowLimit) { System.out.println("Borrow limit reached."); return; }
        book.setAvailability(false);
        BorrowRecord record = new BorrowRecord(recordId, dueDate, book.getIsbn());
        record.createRecord();
        borrowHistory.add(record);
        System.out.println(name + " borrowed: " + book.getTitle());
        System.out.println("Your Record ID: " + recordId + " (Save this for returning)");
    }

    public void returnBook(Book book, BorrowRecord record) {
        book.setAvailability(true);
        record.closeRecord();
        System.out.println(name + " returned: " + book.getTitle());
    }

    public void addFine(Fine fine) {
        fines.add(fine);
        System.out.println("Fine added for: " + name);
    }

    public void viewHistory() {
        System.out.println("--- Borrow History for " + name + " ---");
        if (borrowHistory.isEmpty()) { System.out.println("No history."); return; }
        for (BorrowRecord r : borrowHistory) { r.displayRecord(); System.out.println("---"); }
    }

    public void displayMember() {
        System.out.println("ID: " + memberId + " | Name: " + name + " | Phone: " + phone + " | Email: " + email + " | Limit: " + borrowLimit);
    }

    @Override
    public void showMenu() {}
}