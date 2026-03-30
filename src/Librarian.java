//CLASS: Librarian
import java.util.List;
public class Librarian extends Authentication {

    //private fields
    private String librarianId;
    private String name;
    private String email;
    private LibraryCatalog catalog;
    
    private int booksAdded;
    private int booksRemoved;
    private int booksUpdated;
    private int borrowsProcessed;
    private int returnsProcessed;

    public Librarian(String librarianId, String name, String email, String password, LibraryCatalog catalog) {
        super(name, password);
        this.librarianId = librarianId;
        this.name = name;
        this.email = email;
        this.catalog = catalog;
        this.booksAdded = 0;
        this.booksRemoved = 0;
        this.booksUpdated = 0;
        this.borrowsProcessed = 0;
        this.returnsProcessed = 0;
    }

    public String getLibrarianId() { return librarianId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LibraryCatalog getCatalog() { return catalog; }
    public int getBooksAdded() { return booksAdded; }
    public int getBooksRemoved() { return booksRemoved; }
    public int getBooksUpdated() { return booksUpdated; }
    public int getBorrowsProcessed() { return borrowsProcessed; }
    public int getReturnsProcessed() { return returnsProcessed; }

    public void setEmail(String email) { this.email = email; }

    public static Librarian signup(java.util.Scanner sc, LibraryCatalog catalog) {
        System.out.print("Librarian ID: "); String id = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();

        if (usernameExists("librarians.txt", name)) {
            System.out.println("Username already exists.");
            return null;
        }

        String password;
        while (true) {
            System.out.print("Password: "); password = sc.nextLine();
            if (validatePassword(password)) break;
        }

        Librarian l = new Librarian(id, name, email, password, catalog);
        saveToFile("librarians.txt", id + "," + name + "," + email + "," + password);
        System.out.println("Librarian registered successfully!");
        return l;
    }

    public static Librarian login(java.util.Scanner sc, List<Librarian> librarians) {
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Password: "); String password = sc.nextLine();
        for (Librarian l : librarians) {
            if (l.getName().equalsIgnoreCase(name) && l.getPassword().equals(password)) {
                System.out.println("Welcome, " + name + "!");
                return l;
            }
        }
        System.out.println("Invalid name or password.");
        return null;
    }

    public void addBook(Book book) {
        catalog.addBook(book);
        booksAdded++;
        System.out.println(name + " added: " + book.getTitle());
    }

    public void removeBook(String isbn) {
        catalog.removeBook(isbn);
        booksRemoved++;
    }

    public void updateBook(String isbn, String newTitle, String newAuthor, String newCategory) {
        catalog.updateBook(isbn, newTitle, newAuthor, newCategory);
        booksUpdated++;
    }

    public void addMember(Member member) {
        System.out.println(name + " added member: " + member.getName());
    }

    public void processBorrow(Member member, Book book, String recordId, java.util.Date dueDate) {
        System.out.println(name + " processing borrow for: " + member.getName());
        member.borrowBook(book, recordId, dueDate);
        borrowsProcessed++;
    }

    public void processReturn(Member member, Book book, BorrowRecord record) {
        System.out.println(name + " processing return for: " + member.getName());
        member.returnBook(book, record);
        returnsProcessed++;
    }

    public void displayLibrarian() {
        System.out.println("ID: " + librarianId + " | Name: " + name + " | Email: " + email);
    }

    public void displayActivity() {
        System.out.println("  Books Added    : " + (booksAdded == 0 ? "null" : booksAdded));
        System.out.println("  Books Removed  : " + (booksRemoved == 0 ? "null" : booksRemoved));
        System.out.println("  Books Updated  : " + (booksUpdated == 0 ? "null" : booksUpdated));
        System.out.println("  Borrows Processed : " + (borrowsProcessed == 0 ? "null" : borrowsProcessed));
        System.out.println("  Returns Processed : " + (returnsProcessed == 0 ? "null" : returnsProcessed));
    }

    @Override
    public void showMenu() {}
}