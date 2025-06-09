package model;

import java.time.LocalDate;

public class LendingRecord {
    private static int idCounter = 1;
    private int recordId;
    private Book book;
    private Member member;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public LendingRecord(Book book, Member member) {
        this.recordId = idCounter++;
        this.book = book;
        this.member = member;
        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(15);
        this.returnDate = null;
    }

    public boolean isOverdue() {
        return returnDate == null && dueDate.isBefore(LocalDate.now());
    }

    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.book.returnBook();
    }

    // Getters
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    @Override
    public String toString() {
        return "Record#" + recordId + ": " + book.getTitle() + " issued to " + member.getName() +
                " (Due: " + dueDate + (returnDate == null ? ", Not returned" : ", Returned: " + returnDate) + ")";
    }
}
