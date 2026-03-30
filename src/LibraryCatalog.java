//CLASS: LibraryCatalog
import java.util.ArrayList;
import java.util.List;
public class LibraryCatalog {

    //single list holds all the books
    private ArrayList<Book> bookList;

    public LibraryCatalog() {
        this.bookList = new ArrayList<>();
    }

    public ArrayList<Book> getBookList() { return bookList; }

    //add book->straightforward; remove book->done with isbn
    public void addBook(Book book) {
        bookList.add(book);
        System.out.println("Book added to catalog: " + book.getTitle());
    }

    public void removeBook(String isbn) {
        Book toRemove = null;
        for (Book book : bookList) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                toRemove = book;
                break;
            }
        }
        if (toRemove != null) {
            bookList.remove(toRemove);
            System.out.println("Book removed: " + toRemove.getTitle());
        } else {
            System.out.println("Book not found with ISBN: " + isbn);
        }
    }

    public void updateBook(String isbn, String newTitle, String newAuthor, String newCategory) {
        Book book = findByIsbn(isbn);
        if (book == null) { System.out.println("Book not found with ISBN: " + isbn); return; }
        if (!newTitle.isEmpty()) book.setTitle(newTitle);
        if (!newAuthor.isEmpty()) book.setAuthor(newAuthor);
        if (!newCategory.isEmpty()) book.setCategory(newCategory);
        System.out.println("Book updated successfully.");
        book.displayBook();
    }

    public List<Book> searchByTitle(String keyword) {
        List<Book> results = new ArrayList<>();
        String[] words = keyword.toLowerCase().split(" ");
        for (Book book : bookList) {
            String title = book.getTitle().toLowerCase();
            for (String word : words) {
                if (title.contains(word)) {
                    if (!results.contains(book)) results.add(book);
                    break;
                }
            }
        }
        return results;
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    public List<Book> searchByCategory(String category) {
        List<Book> results = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getCategory().toLowerCase().contains(category.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    public void displayAsTable(List<Book> books) {
        if (books.isEmpty()) { System.out.println("No books found."); return; }
        System.out.println("+------------+------------------------------+----------------------+------+------------------+-------------+");
        System.out.println("| ISBN       | Title                        | Author               | Year | Category         | Available   |");
        System.out.println("+------------+------------------------------+----------------------+------+------------------+-------------+");
        for (Book b : books) {
            System.out.printf("| %-10s | %-28s | %-20s | %-4d | %-16s | %-11s |%n",
                b.getIsbn(),
                b.getTitle(),
                b.getAuthor(),
                b.getPublicationYear(),
                b.getCategory(),
                b.checkAvailability() ? "Yes" : "No"
            );
        }
        System.out.println("+------------+------------------------------+----------------------+------+------------------+-------------+");
    }

    public Book findByIsbn(String isbn) {
        for (Book book : bookList) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) return book;
        }
        return null;
    }

    public void filterAvailability() {
        List<Book> available = new ArrayList<>();
        for (Book book : bookList) {
            if (book.checkAvailability()) available.add(book);
        }
        if (available.isEmpty()) { System.out.println("No books available right now."); return; }
        System.out.println("--- Available Books ---");
        displayAsTable(available);
    }
}