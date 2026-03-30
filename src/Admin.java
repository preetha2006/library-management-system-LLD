//CLASS: Admine(Super)
import java.util.ArrayList;
public class Admin extends Authentication {

    private String adminId;
    private String name;
    private ArrayList<Member> members;
    private ArrayList<Librarian> librarians;
    private int returnDays;
    private float perDayFine;

    public Admin(String adminId, String name, String password) {
        super(name, password);
        this.adminId = adminId;
        this.name = name;
        this.members = new ArrayList<>();
        this.librarians = new ArrayList<>();
        this.returnDays = 7;      
        this.perDayFine = 10.0f;  
    }

    public String getAdminId() { return adminId; }
    public String getName() { return name; }
    public ArrayList<Member> getMembers() { return members; }
    public ArrayList<Librarian> getLibrarians() { return librarians; }
    public int getReturnDays() { return returnDays; }
    public float getPerDayFine() { return perDayFine; }

    public void addMember(Member member) {
        members.add(member);
    }

    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }

    public void manageUsers() {
        System.out.println("\n************ MANAGE USERS ************");

        System.out.println("\n--- Members (" + members.size() + ") ---");
        if (members.isEmpty()) {
            System.out.println("No members registered.");
        } else {
            System.out.println("+----------+----------------------+---------------+----------------------+-------+");
            System.out.println("| ID       | Name                 | Phone         | Email                | Limit |");
            System.out.println("+----------+----------------------+---------------+----------------------+-------+");
            for (Member m : members) {
                System.out.printf("| %-8s | %-20s | %-13s | %-20s | %-5d |%n",
                    m.getMemberId(), m.getName(), m.getPhone(), m.getEmail(), m.getBorrowLimit());
            }
            System.out.println("+----------+----------------------+---------------+----------------------+-------+");
        }

        System.out.println("\n--- Librarians (" + librarians.size() + ") ---");
        if (librarians.isEmpty()) {
            System.out.println("No librarians registered.");
        } else {
            System.out.println("+----------+----------------------+----------------------+");
            System.out.println("| ID       | Name                 | Email                |");
            System.out.println("+----------+----------------------+----------------------+");
            for (Librarian l : librarians) {
                System.out.printf("| %-8s | %-20s | %-20s |%n",
                    l.getLibrarianId(), l.getName(), l.getEmail());
            }
            System.out.println("+----------+----------------------+----------------------+");
        }
        System.out.println("*************************************");
    }

    public void generateReport() {
        System.out.println("\n************ LIBRARY REPORT ************");
        System.out.println("Total Members    : " + members.size());
        System.out.println("Total Librarians : " + librarians.size());
        System.out.println("Return Days      : " + returnDays);
        System.out.println("Per Day Fine     : Rs." + perDayFine);

        System.out.println("\n--- Member Details ---");
        if (members.isEmpty()) {
            System.out.println("No members.");
        } else {
            for (Member m : members) {
                System.out.println("------------------------------------");
                m.displayMember();
                m.viewHistory();
            }
        }

        System.out.println("\n--- Librarian Details ---");
        if (librarians.isEmpty()) {
            System.out.println("No librarians.");
        } else {
            for (Librarian l : librarians) {
                System.out.println("------------------------------------");
                l.displayLibrarian();
                System.out.println("  Activity:");
                l.displayActivity();
            }
        }
        System.out.println("------------------------------------");
        System.out.println("************ END OF REPORT ************");
    }

    public void setBorrowRules(java.util.Scanner sc) {
        System.out.println("Current borrow limit: " + (members.isEmpty() ? "Not set" : members.get(0).getBorrowLimit()));
        System.out.print("Enter new borrow limit: ");
        int limit = sc.nextInt(); sc.nextLine();
        for (Member m : members) m.setBorrowLimit(limit);
        System.out.println("Borrow limit updated to: " + limit + " for all members.");
    }

    public void setReturnSettings(java.util.Scanner sc) {
        System.out.println("Current return days  : " + returnDays);
        System.out.println("Current per day fine : Rs." + perDayFine);
        System.out.print("Enter new return days (press Enter to skip): ");
        String daysInput = sc.nextLine();
        if (!daysInput.isEmpty()) {
            returnDays = Integer.parseInt(daysInput);
            System.out.println("Return days updated to: " + returnDays);
        }
        System.out.print("Enter new per day fine amount (press Enter to skip): ");
        String fineInput = sc.nextLine();
        if (!fineInput.isEmpty()) {
            perDayFine = Float.parseFloat(fineInput);
            System.out.println("Per day fine updated to: Rs." + perDayFine);
        }
    }

    public void displayAdmin() {
        System.out.println("Admin ID: " + adminId + " | Name: " + name);
    }

    @Override
    public void showMenu() {}
}