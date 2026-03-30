import java.util.*;
public class Main {

    static LibraryCatalog catalog = new LibraryCatalog();
    static ArrayList<Member> members = new ArrayList<>();
    static ArrayList<Librarian> librarians = new ArrayList<>();
    static Admin admin = new Admin("A001", "SuperAdmin", "Admin@123");
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        catalog.addBook(new Book("ISBN001", "Clean Code", "Robert Martin", 2008, "Programming"));
        catalog.addBook(new Book("ISBN002", "Java Programming", "James Gosling", 2010, "Programming"));
        catalog.addBook(new Book("ISBN003", "Design Patterns", "Gang of Four", 1994, "Structural"));
        catalog.addBook(new Book("ISBN004", "Clean Codes Advanced", "Robert Martin", 2015, "Programming"));
        catalog.addBook(new Book("ISBN005", "Clean Code Recipes", "Arthur Burns", 2019, "Programming"));

        //Preloaded Member 1 (paid fine, closed record)
        Member m1 = new Member("M001", "Alice", "9876543210", "alice@gmail.com", "Alice@123", 3);
        BorrowRecord br1 = new BorrowRecord("REC001", new Date(System.currentTimeMillis() - 10L * 24 * 60 * 60 * 1000), "ISBN001");
        br1.createRecord();
        br1.closeRecord();
        Fine f1 = new Fine("F001", 3, 10.0f);
        f1.calculateFine();
        f1.payFine();
        m1.getBorrowHistory().add(br1);
        m1.addFine(f1);
        members.add(m1);
        admin.addMember(m1);

        //Preloaded Member 2 (paid fine, closed record)
        Member m2 = new Member("M002", "Bob", "9123456780", "bob@gmail.com", "Bob@1234", 3);
        BorrowRecord br2 = new BorrowRecord("REC002", new Date(System.currentTimeMillis() - 15L * 24 * 60 * 60 * 1000), "ISBN002");
        br2.createRecord();
        br2.closeRecord();
        Fine f2 = new Fine("F002", 5, 10.0f);
        f2.calculateFine();
        f2.payFine();
        m2.getBorrowHistory().add(br2);
        m2.addFine(f2);
        members.add(m2);
        admin.addMember(m2);

        //Preloaded Member 3 (unpaid fine, ACTIVE record — book not returned, overdue)
        Member m3 = new Member("M003", "Charlie", "9001234567", "charlie@gmail.com", "Charlie@1", 3);
        catalog.findByIsbn("ISBN003").setAvailability(false);
        BorrowRecord br3 = new BorrowRecord("REC003", new Date(System.currentTimeMillis() - 5L * 24 * 60 * 60 * 1000), "ISBN003");
        br3.createRecord();
        Fine f3 = new Fine("F003", 5, 10.0f);
        f3.calculateFine();
        m3.getBorrowHistory().add(br3);
        m3.addFine(f3);
        members.add(m3);
        admin.addMember(m3);

        List<String[]> savedLibrarians = Authentication.loadFromFile("librarians.txt");
        for (String[] r : savedLibrarians) {
            Librarian l = new Librarian(r[0], r[1], r[2], r[3], catalog);
            librarians.add(l);
            admin.addLibrarian(l);
        }

        List<String[]> savedMembers = Authentication.loadFromFile("members.txt");
        for (String[] r : savedMembers) {
            Member m = new Member(r[0], r[1], r[2], r[3], r[4], Integer.parseInt(r[5]));
            members.add(m);
            admin.addMember(m);
        }

        System.out.println("***** Welcome to Library Management System *****");
        loginMenu();
    }

    static void loginMenu() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Admin Login");
            System.out.println("2. Librarian Login");
            System.out.println("3. Librarian Signup");
            System.out.println("4. Member Login");
            System.out.println("5. Member Signup");
            System.out.println("6. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) {
                System.out.print("Name: "); String name = sc.nextLine();
                System.out.print("Password: "); String pass = sc.nextLine();
                if (name.equals(admin.getName()) && pass.equals(admin.getPassword())) {
                    System.out.println("Hello, Admin!");
                    adminMenu();
                } else System.out.println("Invalid credentials.");

            } else if (choice == 2) {
                Librarian l = Librarian.login(sc, librarians);
                if (l != null) librarianMenu(l);

            } else if (choice == 3) {
                Librarian l = Librarian.signup(sc, catalog);
                if (l != null) { librarians.add(l); admin.addLibrarian(l); }

            } else if (choice == 4) {
                Member m = Member.login(sc, members);
                if (m != null) memberMenu(m);

            } else if (choice == 5) {
                Member m = Member.signup(sc);
                if (m != null) { members.add(m); admin.addMember(m); }

            } else if (choice == 6) {
                System.out.println("Goodbye!"); break;
            } else System.out.println("Invalid choice.");
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View Report");
            System.out.println("2. Set Borrow Limit");
            System.out.println("3. Set Return Days and Fine Amount");
            System.out.println("4. Manage Users");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) {
                admin.generateReport();
            } else if (choice == 2) {
                admin.setBorrowRules(sc);
            } else if (choice == 3) {
                admin.setReturnSettings(sc);
            } else if (choice == 4) {
                admin.manageUsers();
            } else if (choice == 5) {
                System.out.println("Admin logged out."); break;
            } else System.out.println("Invalid choice.");
        }
    }

    static void librarianMenu(Librarian librarian) {
        while (true) {
            System.out.println("\n--- LIBRARIAN MENU ---");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Update Book");
            System.out.println("4. Process Borrow");
            System.out.println("5. Process Return");
            System.out.println("6. View All Books");
            System.out.println("7. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) {
                System.out.print("ISBN: "); String isbn = sc.nextLine();
                System.out.print("Title: "); String title = sc.nextLine();
                System.out.print("Author: "); String author = sc.nextLine();
                System.out.print("Year: "); int year = sc.nextInt(); sc.nextLine();
                System.out.print("Category: "); String category = sc.nextLine();
                librarian.addBook(new Book(isbn, title, author, year, category));

            } else if (choice == 2) {
                System.out.print("ISBN to remove: ");
                librarian.removeBook(sc.nextLine());

            } else if (choice == 3) {
                System.out.print("ISBN of book to update: "); String isbn = sc.nextLine();
                System.out.print("New title (Enter to skip): "); String title = sc.nextLine();
                System.out.print("New author (Enter to skip): "); String author = sc.nextLine();
                System.out.print("New category (Enter to skip): "); String category = sc.nextLine();
                librarian.updateBook(isbn, title, author, category);

            } else if (choice == 4) {
                System.out.print("Member name: ");
                Member m = findMember(sc.nextLine());
                if (m == null) { System.out.println("Member not found."); continue; }
                Book b = searchAndSelectBook();
                if (b == null) continue;
                if (!b.checkAvailability()) { System.out.println("Book not available."); continue; }
                long dueMillis = System.currentTimeMillis() + (long) admin.getReturnDays() * 24 * 60 * 60 * 1000;
                String rid = "REC" + System.currentTimeMillis();
                Date due = new Date(dueMillis);
                librarian.processBorrow(m, b, rid, due);

            } else if (choice == 5) {
                System.out.print("Member name: ");
                Member m = findMember(sc.nextLine());
                if (m == null) { System.out.println("Member not found."); continue; }

                List<BorrowRecord> activeRecords = new ArrayList<>();
                for (BorrowRecord br : m.getBorrowHistory()) {
                    if (br.checkStatus().equals("ACTIVE")) activeRecords.add(br);
                }
                if (activeRecords.isEmpty()) { System.out.println("No active borrows."); continue; }

                System.out.println("+-----+----------------------+------------------------------+------------+");
                System.out.println("| No. | Record ID            | Due Date                     | ISBN       |");
                System.out.println("+-----+----------------------+------------------------------+------------+");
                for (int i = 0; i < activeRecords.size(); i++) {
                    System.out.printf("| %-3d | %-20s | %-28s | %-10s |%n",
                        i, activeRecords.get(i).getRecordId(), activeRecords.get(i).getDueDate(), activeRecords.get(i).getBookIsbn());
                }
                System.out.println("+-----+----------------------+------------------------------+------------+");

                System.out.print("Enter record number: "); int idx = sc.nextInt(); sc.nextLine();
                if (idx < 0 || idx >= activeRecords.size()) { System.out.println("Invalid number."); continue; }
                BorrowRecord record = activeRecords.get(idx);

                Book b = catalog.findByIsbn(record.getBookIsbn());
                if (b == null) { System.out.println("Book not found."); continue; }
                librarian.processReturn(m, b, record);
                autoGenerateFine(m, record);

            } else if (choice == 6) {
                List<Book> available = new ArrayList<>();
                List<Book> unavailable = new ArrayList<>();
                for (Book b : catalog.getBookList()) {
                    if (b.checkAvailability()) available.add(b);
                    else unavailable.add(b);
                }
                System.out.println("\n--- Available Books ---");
                catalog.displayAsTable(available);
                System.out.println("\n--- Unavailable Books (Currently Borrowed) ---");
                catalog.displayAsTable(unavailable);

            } else if (choice == 7) { System.out.println("Logged out."); break; }
            else System.out.println("Invalid choice.");
        }
    }

    static void memberMenu(Member member) {
        while (true) {
            System.out.println("\n--- MEMBER MENU ---");
            System.out.println("1. Search by Title");
            System.out.println("2. Search by Author");
            System.out.println("3. Search by Category");
            System.out.println("4. View Available Books");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. View History");
            System.out.println("8. View Fines");
            System.out.println("9. Pay Fine");
            System.out.println("10. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) {
                System.out.print("Enter title: ");
                catalog.displayAsTable(catalog.searchByTitle(sc.nextLine()));

            } else if (choice == 2) {
                System.out.print("Enter author: ");
                catalog.displayAsTable(catalog.searchByAuthor(sc.nextLine()));

            } else if (choice == 3) {
                System.out.print("Enter category: ");
                catalog.displayAsTable(catalog.searchByCategory(sc.nextLine()));

            } else if (choice == 4) {
                catalog.filterAvailability();

            } else if (choice == 5) {
                Book b = searchAndSelectBook();
                if (b == null) continue;
                if (!b.checkAvailability()) { System.out.println("Book not available."); continue; }
                long dueMillis = System.currentTimeMillis() + (long) admin.getReturnDays() * 24 * 60 * 60 * 1000;
                String rid = "REC" + System.currentTimeMillis();
                Date due = new Date(dueMillis);
                member.borrowBook(b, rid, due);

            } else if (choice == 6) {
                List<BorrowRecord> activeRecords = new ArrayList<>();
                for (BorrowRecord br : member.getBorrowHistory()) {
                    if (br.checkStatus().equals("ACTIVE")) activeRecords.add(br);
                }
                if (activeRecords.isEmpty()) { System.out.println("No active borrows."); continue; }

                System.out.println("+-----+----------------------+------------------------------+------------+");
                System.out.println("| No. | Record ID            | Due Date                     | ISBN       |");
                System.out.println("+-----+----------------------+------------------------------+------------+");
                for (int i = 0; i < activeRecords.size(); i++) {
                    System.out.printf("| %-3d | %-20s | %-28s | %-10s |%n",
                        i, activeRecords.get(i).getRecordId(), activeRecords.get(i).getDueDate(), activeRecords.get(i).getBookIsbn());
                }
                System.out.println("+-----+----------------------+------------------------------+------------+");

                System.out.print("Enter record number: "); int idx = sc.nextInt(); sc.nextLine();
                if (idx < 0 || idx >= activeRecords.size()) { System.out.println("Invalid number."); continue; }
                BorrowRecord record = activeRecords.get(idx);

                Book b = catalog.findByIsbn(record.getBookIsbn());
                if (b == null) { System.out.println("Book not found."); continue; }
                member.returnBook(b, record);
                autoGenerateFine(member, record);

            } else if (choice == 7) {
                member.viewHistory();

            } else if (choice == 8) {
                if (member.getFines().isEmpty()) { System.out.println("No fines."); continue; }
                for (Fine f : member.getFines()) f.displayFine();

            } else if (choice == 9) {
                if (member.getFines().isEmpty()) { System.out.println("No fines."); continue; }
                boolean hasUnpaid = false;
                for (int i = 0; i < member.getFines().size(); i++) {
                    Fine f = member.getFines().get(i);
                    if (f.getStatus().equals("UNPAID")) {
                        System.out.println(i + ". " + f.getFineId() + " | Rs." + f.getTotalFineAmount());
                        hasUnpaid = true;
                    }
                }
                if (!hasUnpaid) { System.out.println("No unpaid fines."); continue; }
                System.out.print("Fine number: "); int idx = sc.nextInt(); sc.nextLine();
                member.getFines().get(idx).payFine();

            } else if (choice == 10) { System.out.println("Logged out."); break; }
            else System.out.println("Invalid choice.");
        }
    }

    static Book searchAndSelectBook() {
        System.out.print("Enter book title to search: ");
        String keyword = sc.nextLine();
        List<Book> results = catalog.searchByTitle(keyword);
        if (results.isEmpty()) { System.out.println("No books found matching: " + keyword); return null; }
        catalog.displayAsTable(results);
        System.out.print("Enter ISBN of the book you want: ");
        String isbn = sc.nextLine();
        Book selected = catalog.findByIsbn(isbn);
        if (selected == null) { System.out.println("No book found with ISBN: " + isbn); return null; }
        return selected;
    }

    static Member findMember(String name) {
        for (Member m : members)
            if (m.getName().equalsIgnoreCase(name)) return m;
        return null;
    }

    static void autoGenerateFine(Member member, BorrowRecord record) {
        Date today = new Date();
        if (today.after(record.getDueDate())) {
            long diff = today.getTime() - record.getDueDate().getTime();
            int days = (int) (diff / (1000 * 60 * 60 * 24));
            if (days == 0) days = 1;
            Fine fine = new Fine("F" + System.currentTimeMillis(), days, admin.getPerDayFine());
            fine.calculateFine();
            member.addFine(fine);
            System.out.println("Overdue by " + days + " day(s). Fine of Rs." + fine.getTotalFineAmount() + " generated!");
        } else {
            System.out.println("Returned on time. No fine.");
        }
    }
}