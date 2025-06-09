import model.*;
import service.*;
import Exception.*;

public class Main {
    public static void main(String[] args) {
        LibraryService lib = new LibraryService();
        OverdueMonitor monitor = new OverdueMonitor(lib);
        monitor.setDaemon(true);
        monitor.start();

        lib.addBook(new Book("B1", "1984", "shaswat sharma"));
        lib.addBook(new Book("B2", "your life", "divyansh"));
        lib.addMember(new Member("M1", "sanskar", "sanskar@example.com"));

        try {
            lib.issueBook("B1", "M1");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }

        try {
            lib.issueBook("B1", "M1");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }

        lib.getSortedBooks().forEach(System.out::println);
    }
}
