package View;

import controllers.LibraryController;
import Model.Book;
import Model.Librarian;
import Model.User;
import Model.UserDAO;
import Model.UserDAOImpl;

import java.util.*;

public class LibrarianView implements LibraryView {
	 private LibraryController controller;
	 private UserDAO userDAO=new UserDAOImpl();

	public LibrarianView(LibraryController libraryController) {
        this.controller = libraryController;
   
    }
    public void start() {
        Scanner sc = new Scanner(System.in);
       
        boolean exit = false;

        System.out.println("Welcome to the Library System 'Librarian'!");

        System.out.println("1.Register as a Librarian");
        System.out.println("2. Login with Email & Password");
        System.out.print("Enter your choice: ");
        int initialChoice = sc.nextInt();
        sc.nextLine();

        User loggedInUser = null;
        try {

        if (initialChoice == 1) {
        	 System.out.print("Enter your Name: ");
             String userName = sc.nextLine();
             System.out.print("Enter your Email: ");
             String email = sc.nextLine();
             System.out.print("Enter your Password: ");
             String password = sc.nextLine();

             User newUser = new Librarian(0, userName, email);

             int userId = userDAO.registerUser(newUser, password);

             if (userId > 0) {
                
                 loggedInUser = new Librarian(userId, userName, email);
                 System.out.println("User registered successfully Welcome, " + loggedInUser.getUserName() + "!");
             }
             else if(userId==-2) {
            	 System.out.println("User is already exits");
            	 return;
             }
             else {
                 System.out.println("User registration failed...");
                 return ;
             }
        } 
        
        else if (initialChoice == 2) {
          
            System.out.print("Enter your Email: ");
            String email = sc.nextLine();
            System.out.print("Enter your Password: ");
            String password = sc.nextLine();

            loggedInUser = userDAO.loginUser(email, password);
            if (loggedInUser == null || !loggedInUser.getRole().equals("Librarian")) {
                System.out.println("Login failed. Only librarians can access this section.");
                return;
            } else {
                System.out.println("Welcome, " + loggedInUser.getUserName());
            }
        } 
        else {
            System.out.println("Invalid choice Exiting...");
            return;
        }

        while (!exit) {
            System.out.println("\n LIBRARIAN MENU ");
            System.out.println("1. Add a Book");
            System.out.println("2. Remove a Book");
            System.out.println("3. View Borrowed Books");
            System.out.println("4. Mark Fees as Paid");
            System.out.println("5. View All Users");
            System.out.println("6. Get All Books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Book Name: ");
                    String bookName = sc.nextLine();

                    System.out.print("Enter Author Name: ");
                    String author = sc.nextLine();

                    System.out.print("Enter Publisher ID: ");
                    int publisherId = sc.nextInt();

                    System.out.print("Enter Domain ID: ");
                    int domainId = sc.nextInt();

                    System.out.print("Enter Total Copies: ");
                    int totalCopies = sc.nextInt();

                    System.out.print("Enter Book Price: ");
                    int amount = sc.nextInt();

                    boolean isAvailable = totalCopies > 0; 

                    
                    Book newBook = new Book(bookId, bookName, author, isAvailable, totalCopies, publisherId, amount, domainId);

                 
				
					System.out.println(controller.addBook(newBook));
                    break;

                case 2:
                    System.out.print("Enter Book ID to remove: ");
                    int IdBook = sc.nextInt();
                    System.out.println(controller.removeBookById(IdBook));
                    break;

                case 3:
                    System.out.println(controller.viewBorrowedBooks());
                    break;

                case 4:
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bookFeeId = sc.nextInt();
                    System.out.print("Enter amount: ");
                    int amountPaid = sc.nextInt();
                    System.out.println(controller.payFees(userId, bookFeeId, amountPaid));
                    break;

                case 5:
                    System.out.println(userDAO.getAllUsers());
                    break;
                  
                case 6:
                	System.out.println(controller.getAllBook());
                	break;

                case 7:
                    exit = true;
                    System.out.println("Exiting Librarian System...");
                    break;

                default:
                    System.out.println("Invalid choice Try again...");
            }
        }
        sc.close();
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
        }
    }
}
