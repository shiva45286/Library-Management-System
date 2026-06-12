import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Book {
    private int id;
    private String title;
    private String author;
    private String category;
    private boolean issued;
    private String issuedTo;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public Book(int id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.issued = false;
        this.issuedTo = "";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIssued() {
        return issued;
    }

    public void issueBook(String studentName) {
        if (!issued) {
            issued = true;
            issuedTo = studentName;
            issueDate = LocalDate.now();
            dueDate = issueDate.plusDays(14);

            System.out.println("Book Issued Successfully!");
            System.out.println("Due Date: " + dueDate);
        } else {
            System.out.println("Book already issued.");
        }
    }

    public void returnBook() {
        if (issued) {
            long lateDays = ChronoUnit.DAYS.between(dueDate, LocalDate.now());

            if (lateDays > 0) {
                double fine = lateDays * 5;
                System.out.println("Late Return Fine = ₹" + fine);
            }

            issued = false;
            issuedTo = "";
            issueDate = null;
            dueDate = null;

            System.out.println("Book Returned Successfully!");
        } else {
            System.out.println("Book was not issued.");
        }
    }

    public void displayBook() {
        System.out.println("--------------------------------");
        System.out.println("Book ID   : " + id);
        System.out.println("Title     : " + title);
        System.out.println("Author    : " + author);
        System.out.println("Category  : " + category);
        System.out.println("Status    : " + (issued ? "Issued" : "Available"));

        if (issued) {
            System.out.println("Issued To : " + issuedTo);
            System.out.println("Due Date  : " + dueDate);
        }
    }
}

class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book Added Successfully!");
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No Books Available.");
            return;
        }

        for (Book book : books) {
            book.displayBook();
        }
    }

    public Book searchBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public void deleteBook(int id) {
        Book book = searchBook(id);

        if (book != null) {
            books.remove(book);
            System.out.println("Book Deleted Successfully!");
        } else {
            System.out.println("Book Not Found!");
        }
    }

    public void statistics() {
        int available = 0;
        int issued = 0;

        for (Book book : books) {
            if (book.isIssued())
                issued++;
            else
                available++;
        }

        System.out.println("\n===== Library Statistics =====");
        System.out.println("Total Books     : " + books.size());
        System.out.println("Available Books : " + available);
        System.out.println("Issued Books    : " + issued);
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        while (true) {

            System.out.println("\n========== LIBRARY MANAGEMENT SYSTEM ==========");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Search Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Library Statistics");
            System.out.println("8. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();

                    System.out.print("Enter Category: ");
                    String category = sc.nextLine();

                    library.addBook(new Book(id, title, author, category));
                    break;

                case 2:
                    library.displayBooks();
                    break;

                case 3:
                    System.out.print("Enter Book ID: ");
                    int searchId = sc.nextInt();

                    Book found = library.searchBook(searchId);

                    if (found != null)
                        found.displayBook();
                    else
                        System.out.println("Book Not Found!");
                    break;

                case 4:
                    System.out.print("Enter Book ID: ");
                    int issueId = sc.nextInt();
                    sc.nextLine();

                    Book issueBook = library.searchBook(issueId);

                    if (issueBook != null) {
                        System.out.print("Enter Student Name: ");
                        String studentName = sc.nextLine();

                        issueBook.issueBook(studentName);
                    } else {
                        System.out.println("Book Not Found!");
                    }
                    break;

                case 5:
                    System.out.print("Enter Book ID: ");
                    int returnId = sc.nextInt();

                    Book returnBook = library.searchBook(returnId);

                    if (returnBook != null)
                        returnBook.returnBook();
                    else
                        System.out.println("Book Not Found!");
                    break;

                case 6:
                    System.out.print("Enter Book ID: ");
                    int deleteId = sc.nextInt();

                    library.deleteBook(deleteId);
                    break;

                case 7:
                    library.statistics();
                    break;

                case 8:
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}