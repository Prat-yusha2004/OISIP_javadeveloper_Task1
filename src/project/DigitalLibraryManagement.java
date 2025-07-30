package project;

import java.util.*;

class Book {
    int id;
    String title, author, category;
    boolean isIssued;

    Book(int id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = false;
    }
}

class DigitalLibraryManagement{
    static Scanner sc = new Scanner(System.in);
    static Map<Integer, Book> bookCatalog = new HashMap<>();
    static Map<String, String> users = new HashMap<>();
    static Map<String, String> admins = new HashMap<>();
    static Map<String, List<Integer>> userIssuedBooks = new HashMap<>();

    public static void main(String[] args) {
        admins.put("admin", "admin123");
        users.put("user", "user123");
        userIssuedBooks.put("user", new ArrayList<>());

        while (true) {
            System.out.println("\n==== Digital Library Management ====");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 -> adminLogin();
                case 2 -> userLogin();
                case 3 -> {
                    System.out.println("Exiting system...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void adminLogin() {
        System.out.print("Admin Username: ");
        String username = sc.nextLine();
        System.out.print("Admin Password: ");
        String password = sc.nextLine();

        if (admins.containsKey(username) && admins.get(username).equals(password)) {
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. View All Books");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> deleteBook();
                case 3 -> viewBooks();
                case 4 -> {
                    System.out.println("Admin logged out.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("Category: ");
        String category = sc.nextLine();

        bookCatalog.put(id, new Book(id, title, author, category));
        System.out.println("Book added successfully.");
    }

    static void deleteBook() {
        System.out.print("Enter Book ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (bookCatalog.containsKey(id)) {
            bookCatalog.remove(id);
            System.out.println("Book deleted.");
        } else {
            System.out.println("Book not found.");
        }
    }

    static void viewBooks() {
        if (bookCatalog.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("\n--- Book List ---");
        for (Book book : bookCatalog.values()) {
            System.out.println("ID: " + book.id + " | " + book.title + " | " + book.author +
                    " | " + book.category + " | Issued: " + (book.isIssued ? "Yes" : "No"));
        }
    }

    static void userLogin() {
        System.out.print("User Username: ");
        String username = sc.nextLine();
        System.out.print("User Password: ");
        String password = sc.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            userMenu(username);
        } else {
            System.out.println("Invalid user credentials.");
        }
    }

    static void userMenu(String username) {
        while (true) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. View Books");
            System.out.println("2. Search Book by Title");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Send Email Query");
            System.out.println("6. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewBooks();
                case 2 -> searchBook();
                case 3 -> issueBook(username);
                case 4 -> returnBook(username);
                case 5 -> sendEmailQuery();
                case 6 -> {
                    System.out.println("User logged out.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static void searchBook() {
        System.out.print("Enter title keyword: ");
        String keyword = sc.nextLine().toLowerCase();
        boolean found = false;

        for (Book book : bookCatalog.values()) {
            if (book.title.toLowerCase().contains(keyword)) {
                System.out.println("ID: " + book.id + " | " + book.title + " | Author: " + book.author);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found with that title.");
        }
    }

    static void issueBook(String username) {
        System.out.print("Enter Book ID to issue: ");
        int id = sc.nextInt();
        sc.nextLine();

        Book book = bookCatalog.get(id);
        if (book != null && !book.isIssued) {
            book.isIssued = true;
            userIssuedBooks.get(username).add(id);
            System.out.println("Book issued successfully.");
        } else {
            System.out.println("Book not available.");
        }
    }

    static void returnBook(String username) {
        System.out.print("Enter Book ID to return: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (userIssuedBooks.get(username).remove(Integer.valueOf(id))) {
            Book book = bookCatalog.get(id);
            if (book != null) book.isIssued = false;
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("You have not issued this book.");
        }
    }

    static void sendEmailQuery() {
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter your message: ");
        String message = sc.nextLine();
        System.out.println("Thank you. Your query has been received and will be responded to via email.");
        // Placeholder for real email logic using JavaMail API
    }
}