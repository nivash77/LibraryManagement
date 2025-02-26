package View;
import Model.User;
import controllers.Library;
import controllers.UserAuthentication;
import java.util.Scanner;

public class UserView implements LibraryView {
    public void start() {
        Scanner sc = new Scanner(System.in);
        Library controller = new Library();
        UserAuthentication authController = new UserAuthentication();
        boolean exit = false;

        System.out.println("Welcome to the Library System!");

        System.out.println("1. Register as a New User");
        System.out.println("2. Login with Email & Password");
        System.out.print("Enter your choice: ");
        int initialChoice = sc.nextInt();
        sc.nextLine();

        User loggedInUser = null;

        if (initialChoice == 1) {
            System.out.print("Enter your Name: ");
            String userName = sc.nextLine();
            System.out.print("Enter your Email: ");
            String email = sc.nextLine();
            System.out.print("Enter your Password: ");
            String password = sc.nextLine();

            User newUser = new User(0, userName, email, "User");

            int userId = authController.registerUser(newUser, password);

            if (userId > 0) {
                loggedInUser = new User(userId, userName, email, "User");
                System.out.println("User registered successfully! Welcome, " + loggedInUser.getUserName() + "!");
            } else {
                System.out.println("User registration failed.");
            }
        }

        else if (initialChoice == 2) {
            System.out.print("Enter your Email: ");
            String email = sc.nextLine();
            System.out.print("Enter your Password: ");
            String password = sc.nextLine();

            loggedInUser = authController.loginUser(email, password);

            if (loggedInUser == null) {
                System.out.println("Login failed. Check email & password.");
            } else {
                System.out.println("Welcome, " + loggedInUser.getUserName() + "!");
            }
        }

        else {
            System.out.println("Invalid choice. Exiting...");
            return;
        }

        while (!exit) {
            System.out.println("\n USER MENU ");
            System.out.println("1. Borrow a Book");
            System.out.println("2. Return a Book");
            System.out.println("3. Check Pending Fees");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID to borrow: ");
                    int bookId = sc.nextInt();
                    System.out.println(controller.borrowBook(bookId, loggedInUser.getUserId()));
                    break;

                case 2:
                    System.out.print("Enter Book ID to return: ");
                    int returnBookId = sc.nextInt();
                    System.out.println(controller.returnBook(returnBookId, loggedInUser.getUserId()));
                    break;

                case 3:
                    System.out.print("Enter Book ID: ");
                    int checkBookId = sc.nextInt();
                    System.out.println(controller.checkPendingFees(loggedInUser.getUserId(), checkBookId));
                    break;

                case 4:
                    exit = true;
                    System.out.println("Exiting Library System...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        sc.close();
    }
}
