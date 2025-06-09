package model;
import Exception.BookNotAvailableException;

public class Book implements Comparable<Book> {
    private String bookId;
    private String title;
    private String author;
    private BookStatus status;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.status = BookStatus.AVAILABLE;
    }

    public void issue() throws BookNotAvailableException {
        if (status == BookStatus.ISSUED)
            throw new BookNotAvailableException("Book already issued: " + title);
        this.status = BookStatus.ISSUED;
    }

    public void returnBook() {
        this.status = BookStatus.AVAILABLE;
    }

    // Getters, setters, and compareTo
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public BookStatus getStatus() { return status; }

    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public String toString() {
        return "[" + bookId + "] " + title + " by " + author + " - " + status;
    }
}
