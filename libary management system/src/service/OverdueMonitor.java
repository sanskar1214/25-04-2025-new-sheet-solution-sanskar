package service;

public class OverdueMonitor extends Thread {
    private LibraryService library;

    public OverdueMonitor(LibraryService library) {
        this.library = library;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000); // 1 minute
                library.getOverdueRecords().forEach(record -> {
                    System.out.println("📢 Overdue: " + record);
                });
            } catch (InterruptedException e) {
                System.out.println("Monitor interrupted");
            }
        }
    }
}
