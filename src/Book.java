//CLASS: Book
public class Book {
    //private fields
    private String isbn; //unique id
    private String title;
    private String author;
    private int publicationYear;
    private boolean availability; //true-> available; false-> borrowed
    private String category;

    //constructor
    public Book(String isbn, String title, String author, int publicationYear, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.availability = true; //default->true
        this.category = category;
    }

    //getters(other class use to read these values)
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public boolean checkAvailability() { return availability; }
    public String getCategory() { return category; }

    //setter(used to change the values)
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
    public void setAvailability(boolean availability) { this.availability = availability; }

    //print all these infos
    public void displayBook() {
        System.out.println("ISBN: " + isbn);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Year: " + publicationYear);
        System.out.println("Category: " + category);
        System.out.println("Available: " + availability);
    }
}