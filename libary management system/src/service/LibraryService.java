package service;

import model.*;
import repository.Repository;
import Exception.*;

import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private Repository<Book> bookRepo = new Repository<>();
    private Repository<Member> memberRepo = new Repository<>();
    private List<LendingRecord> records = new ArrayList<>();

    public void addBook(Book book) {
        bookRepo.add(book.getBookId(), book);
    }

    public void addMember(Member member) {
        memberRepo.add(member.getMemberId(), member);
    }

    public void issueBook(String bookId, String memberId) throws Exception {
        Book book = bookRepo.get(bookId);
        Member member = memberRepo.get(memberId);

        if (member == null)
            throw new MemberNotFoundException("Member ID not found: " + memberId);

        boolean hasOverdue = records.stream()
                .filter(r -> r.getMember().getMemberId().equals(memberId))
                .anyMatch(LendingRecord::isOverdue);

        if (hasOverdue)
            throw new OverdueBookException("Member has overdue books");

        book.issue(); // throws exception if not available

        LendingRecord record = new LendingRecord(book, member);
        records.add(record);
        System.out.println("Issued: " + record);
    }

    public void returnBook(String bookId) {
        for (LendingRecord r : records) {
            if (r.getBook().getBookId().equals(bookId) && r.getReturnDate() == null) {
                r.returnBook();
                System.out.println("Returned: " + r);
                return;
            }
        }
        System.out.println("Record not found or already returned");
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepo.getAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        b.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Member> searchMembers(String keyword) {
        return memberRepo.getAll().stream()
                .filter(m -> m.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        m.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> getSortedBooks() {
        return bookRepo.getAll().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Member> getActiveBorrowers() {
        return records.stream()
                .filter(r -> r.getReturnDate() == null)
                .map(LendingRecord::getMember)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<LendingRecord> getOverdueRecords() {
        return records.stream()
                .filter(LendingRecord::isOverdue)
                .collect(Collectors.toList());
    }
}
