package View;
import controllers.LibraryController;
import Model.User;
import Model.UserDAO;

//import Model.BookDAO;
//import Model.BookDAOImpl;
import Model.NormalUser;
//import Model.BorrowingDAOImpl;
//import Model.BorrowingDAO;
import java.util.Scanner;

public class UserView implements LibraryView {
	 private LibraryController controller;
	 //private BookDAO bookDAO=new BookDAOImpl();
	 
	 //private BorrowingDAO borrowingDAO=new BorrowingDAOImpl();
	 private UserDAO userDAO;
	  
	  public UserView(LibraryController libraryController,UserDAO userDAO) {
	        this.controller = libraryController;
	          this. userDAO =userDAO;
	    }
    public void start() {
        Scanner sc = new Scanner(System.in);
        //LibraryController controller = new LibraryController(bookDAO, borrowingDAO );
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

            User newUser = new NormalUser(0, userName, email);

            int userId = userDAO.registerUser(newUser, password);

            if (userId > 0) {
                loggedInUser = new NormalUser(userId, userName, email);
                System.out.println("User registered successfully Welcome, " + loggedInUser.getUserName());
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

            if (loggedInUser == null) {
                System.out.println("Login failed. Check email & password...");
                return ;
            } else {
                System.out.println("Welcome, " + loggedInUser.getUserName());
            }
        }

        else {
            System.out.println("Invalid choice Exiting...");
            return;
        }

        while (!exit) {
            System.out.println("\n USER MENU ");
            System.out.println("1.Borrow a Book");
            System.out.println("2.Return a Book");
            System.out.println("3.Check Pending Fees");
            System.out.println("4.Change password");
            System.out.println("5.shelf number of Book");
            System.out.println("6.Exit");
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
                	System.out.print("Enter a email:");
                	String email=sc.next();
                	sc.nextLine();
                	System.out.print("Enter a old Password:");
                	String oldpassword=sc.nextLine();
                	System.out.print("Enter a new Password:");
                	String newpassword=sc.nextLine();
                	System.out.println(userDAO.ChangePassword(email, oldpassword, newpassword));
                	break;
                	
                case 5:
                	sc.nextLine();
                	System.out.print("Enter a book Name: ");
                	String bookName=sc.nextLine();
                	int k=userDAO.getSelfNoByBookName(bookName);
                	if(k!=-1) {
                		 System.out.println("The shelf number for '" + bookName + "' is: " +k);
                	}
                	else {
                		System.out.println("Book not found!");
                	}
                	break;

                case 6:
                    exit = true;
                    System.out.println("Exiting Library System...");
                    break;

                default:
                    System.out.println("Invalid choice Try again...");
            }
        }
        sc.close();
    }
}
