package BookSpehere2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static final String BOOK_FILE = "bookDetails.txt";
    private static final String USER_FILE = "userDetails.txt";
    private static final String ISSUE_FILE = "issueBook.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Book> books = loadBooksFromFile();
        List<User> users = loadUsersFromFile();
        List<IssueBook> issuedBooks = loadIssuedBooksFromFile();
        boolean adminLoggedIn = false;

        while (true) {
            System.out.println("Options:");
            System.out.println("1. Select User Type (Admin/Student)");
            System.out.println("2. Add a book");
            System.out.println("3. Delete a book");
            System.out.println("4. Display all books");
            System.out.println("5. Search for a book by name");
            System.out.println("6. Search for a book by writer");
            System.out.println("7. Issue a book");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            

            switch (choice) {
                case 1:
                    System.out.print("Select User Type (Admin/Student): ");
                    String userType = scanner.nextLine();

                    if (userType.equalsIgnoreCase("Admin")) {
                        adminLoggedIn = true;
                        System.out.println("Admin logged in successfully!");
                    } else if (userType.equalsIgnoreCase("Student")) {
                        adminLoggedIn = false;
                        System.out.println("Student logged in.");
                    } else {
                        System.out.println("Invalid user type. Please choose Admin or Student.");
                    }
                    break;
                case 2:
                    System.out.print("Enter book name: ");
                    String bookName = scanner.nextLine();
                    System.out.print("Enter writer name: ");
                    String writerName = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();

                    // Create a new Book object and add it to the list
                    books.add(new Book(bookName, writerName, price, quantity));
                    System.out.println("Book added successfully!");
                    break;

                case 3:
                    System.out.print("Enter the bookId of the book to delete: ");
                    int bookIdToDelete = scanner.nextInt();

                    // Find the book by bookId and delete it
                    boolean bookDeleted = false;
                    for (Book book : books) {
                        if (book.getBookId() == bookIdToDelete) {
                            books.remove(book);
                            bookDeleted = true;
                            System.out.println("Book deleted successfully!");
                            break;
                        }
                    }

                    if (!bookDeleted) {
                        System.out.println("Book not found with bookId: " + bookIdToDelete);
                    }
                    break;
                case 4:
                	for (Book book : books) {
                        displayBookDetails(book);
                    }
                    break;
                	
                case 5:
                    System.out.print("Enter book name or a subsequence: ");
                    String searchBookName = scanner.nextLine().toLowerCase();

                    boolean foundBook = false;
                    for (Book book : books) {
                        if (book.getBookName().toLowerCase().contains(searchBookName)) {
                            System.out.println("Book Found:");
                            displayBookDetails(book);
                            foundBook = true;
                        }
                    }

                    if (!foundBook) {
                        System.out.println("Book Absent");
                    }
                    break;

                case 6:
                    System.out.print("Enter writer name or a subsequence: ");
                    String searchWriterName = scanner.nextLine().toLowerCase();

                    boolean foundWriter = false;
                    for (Book book : books) {
                        if (book.getWriterName().toLowerCase().contains(searchWriterName)) {
                            System.out.println("Book Found:");
                            displayBookDetails(book);
                            foundWriter = true;
                        }
                    }

                    if (!foundWriter) {
                        System.out.println("Writer Absent");
                    }
                    break;

                case 7:
                    if (adminLoggedIn) {
                        System.out.print("Enter book name: ");
                        String bookNameToIssue = scanner.nextLine();
                        System.out.print("Enter author's name: ");
                        String authorNameToIssue = scanner.nextLine();

                        // Check if the book exists
                        boolean bookExists = false;
                        for (Book book : books) {
                            if (book.getBookName().equalsIgnoreCase(bookNameToIssue) &&
                                book.getWriterName().equalsIgnoreCase(authorNameToIssue)) {
                                bookExists = true;
                                issuedBooks.add(new IssueBook(book.getBookId(), -1));  // -1 for unknown user
                                System.out.println("Book issued successfully!");
                                break;
                            }
                        }

                        if (!bookExists) {
                            System.out.println("Book not found with the specified name and author.");
                        }
                    } else {
                        System.out.println("Admin login required to issue books.");
                    }
                    break;

                case 8:
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            saveDataToFile(books, users, issuedBooks);
        }
        
    }
    
    private static void saveDataToFile(List<Book> books, List<User> users, List<IssueBook> issuedBooks) {
        try {
            ObjectOutputStream bookOutputStream = new ObjectOutputStream(new FileOutputStream(BOOK_FILE));
            bookOutputStream.writeObject(books);
            bookOutputStream.close();

            ObjectOutputStream userOutputStream = new ObjectOutputStream(new FileOutputStream(USER_FILE));
            userOutputStream.writeObject(users);
            userOutputStream.close();

            ObjectOutputStream issueOutputStream = new ObjectOutputStream(new FileOutputStream(ISSUE_FILE));
            issueOutputStream.writeObject(issuedBooks);
            issueOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper methods to load data from files using deserialization
    private static List<Book> loadBooksFromFile() {
        List<Book> books = new ArrayList<>();
        try {
            ObjectInputStream bookInputStream = new ObjectInputStream(new FileInputStream(BOOK_FILE));
            books = (List<Book>) bookInputStream.readObject();
            bookInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            // Handle the case when the file doesn't exist or deserialization fails
        }
        return books;
    }

    private static List<User> loadUsersFromFile() {
        List<User> users = new ArrayList<>();
        try {
            ObjectInputStream userInputStream = new ObjectInputStream(new FileInputStream(USER_FILE));
            users = (List<User>) userInputStream.readObject();
            userInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            // Handle the case when the file doesn't exist or deserialization fails
            // Return an empty list if no data can be loaded
        }
        return users; // Return the loaded or empty list
    }

    private static List<IssueBook> loadIssuedBooksFromFile() {
        List<IssueBook> issuedBooks = new ArrayList<>();
        try {
            ObjectInputStream issueInputStream = new ObjectInputStream(new FileInputStream(ISSUE_FILE));
            issuedBooks = (List<IssueBook>) issueInputStream.readObject();
            issueInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            // Handle the case when the file doesn't exist or deserialization fails
            // Return an empty list if no data can be loaded
        }
        return issuedBooks; // Return the loaded or empty list
    }


    // Helper method to display book details
    private static void displayBookDetails(Book book) {
        System.out.println("Book ID: " + book.getBookId());
        System.out.println("Book Name: " + book.getBookName());
        System.out.println("Writer Name: " + book.getWriterName());
        System.out.println("Price: $" + book.getPrice());
        System.out.println("Quantity: " + book.getQuantity());
    }
}
