package Main;
//import DBConnection.DBConnect;
import View.LibraryView;
import View.LibrarianView;
import View.UserView;
import controllers.LibraryController;

import Model.BookDAO;
import Model.BorrowingDAO;
import Model.UserDAO;
import Model.BookDAOImpl;
import Model.BorrowingDAOImpl;
import Model.UserDAOImpl;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	  Scanner sc = new Scanner(System.in);

          BookDAO bookDAO = new BookDAOImpl();
          UserDAO userDAO = new UserDAOImpl();
          BorrowingDAO borrowingDAO = new BorrowingDAOImpl(userDAO, bookDAO);

          LibraryController libraryController = new LibraryController(bookDAO, borrowingDAO);
          

          System.out.println("\n Welcome to Library Management System");
          System.out.println("1. Login as Librarian");
          System.out.println("2. Login as User");
          System.out.print("Choose your role: ");
          int choice = sc.nextInt();

          LibraryView view = null;
          if (choice == 1) {
              view = new LibrarianView(libraryController);
          } else if (choice == 2) {
              view = new UserView(libraryController,userDAO);
          } else {
              System.out.println("Invalid choice. Exiting...");
              return;
          }

          view.start();
          sc.close();
    }
}





















//package Main;
//import DBConnection.DBConnect;
//import Model.Book;
//import Model.User;
//import controllers.Library;
//
//import java.util.*;
//public class Main {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		 Library library = new Library();
//		
//	        // Add books to the library
//
//	        //Book book1 = new Book(1, "Java Programming", "James Gosling", true,3, 101, 500, 201, 301);
//	        //Book book2 = new Book(2, "Data Structures", "Mark Allen Weiss", true, 5, 102, 450, 202, 302);
//	        //Book book3 = new Book(3, "Cybersecurity Fundamentals", "Chuck Easttom", true, 7, 103, 600, 203, 303);
//	        //Book book1 = new Book(1, "Java Programming", "James Gosling", 101, 500, 0, 1);
////	        Book book2 = new Book(2, "Effective Java", "Joshua Bloch", 102, 600, 0, 1);
//	        //System.out.println(library.addBook(book1));
//	        //System.out.println(library.addBook(book2));
////	        System.out.println(library.addBook(book3));
////	        System.out.println(library.addBook(book1)); 
////	        System.out.println(library.addBook(book2)); 
////	        System.out.println(library.addBook(book1)); 
//	        
////       
////	        System.out.println(library.getBook(1)); 
////	        System.out.println(library.getBook(2));
////	        System.out.println(library.removeBookById(1));
////	        System.out.println(library.getBook(1)); 
////	        System.out.println(library.removeBookById(2));
////	        System.out.println(library.getBook(2));
////	        User user=new User(23,"nivash",library);
////	        System.out.println(library.addUser(user));
////
////	        // Borrow Book
////	        System.out.println(user.borrowBook(2, 23));
////	        for (Map.Entry<Integer, User> entry : library.users.entrySet()) {
////	            System.out.println(entry.getValue()); // Calls the overridden toString() method
////	        }
//	        
//	        //System.out.println(user.borrowBook(2, 23));
//	        
//	        
//	        
//	        //add User in DB
//		 /*Library library = new Library();
//	        User user = new User(1, "Nivash");
//	        
//	        String result = library.addUser(user);
//	        System.out.println(result);*/
//		
//		//remove a book
//		//System.out.println(library.removeBookByName("Java Programming"));
//		 
//		 //barrow book 
//		 //System.out.println(library.borrowBook(2, 2));
//		 
//		 //return book
//		 //System.out.println(library.returnBook(2, 2));
////		 System.out.println(library.checkPendingFees(1, 1));
//		 //System.out.println(library.payFees(3, 2, 0));
//
//	}
//
//}
