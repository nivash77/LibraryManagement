package controllers;
import DBConnection.DBConnect;
import Model.Book;
import Model.User;

import java.util.*;
import java.util.Date;
import java.sql.*;
public class Library{
  public  Map<Integer,Book> book;
  public Map<Integer,User> users;
  private DBConnect db;
  public Library(){
	  db = new DBConnect();
//	  book=new HashMap<>();
//	  users=new HashMap<>();
  }
//  public String addBook (Book bookItems) {
//	  try {
//		  if(!book.containsKey(bookItems.getBookId())) {
//			  bookItems.incrementTotalBook();
//			  //bookItems.isAvailable(bookItems.gettotalBook());
//			  bookItems.setAvailable(bookItems.isAvailable(bookItems.getTotalBook()));
//		 book.put(bookItems.getBookId(),bookItems);
//		 //System.out.println(book.get(bookItems.getBookId()).getBookName());
//		  return "Book added successfully.";
//		  }
//		  else {
//              Book existingBook = book.get(bookItems.getBookId());
//              existingBook.incrementTotalBook();
//              book.put(bookItems.getBookId(), existingBook);
//              return "Book already exists with ID: " + bookItems.getBookId() + ". Total count updated.";
//          }
//	  }
//	  catch(Exception e) {
//		  return e.getMessage();
//	  }
//	  
//  }
  public String addBook(Book bookItems) {
	    try {
	       
	        String checkQuery = "SELECT totalBook FROM Books WHERE bookName = ? AND author = ?";
	        ResultSet rs = db.executeQuery(checkQuery, bookItems.getBookName(), bookItems.getAuthor());

	        if (rs != null && rs.next()) {
	           
	            int currentCount = rs.getInt("totalBook");
	            int updatedCount = currentCount + bookItems.getTotalBook();

	            String updateQuery = "UPDATE Books SET totalBook = ? WHERE bookName = ? AND author = ?";
	            int rowsAffected = db.executeUpdate(updateQuery, updatedCount, bookItems.getBookName(), bookItems.getAuthor());

	            if (rowsAffected > 0) {
	                return "Book already exists. Total count updated to " + updatedCount + ".";
	            } else {
	                return "Failed to update book count.";
	            }
	        } else {
	            // Book does not exist, insert new record
	            String insertQuery = "INSERT INTO Books (bookName, author, available, totalBook, publizerId, amount, domainId) " +
	                                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
	            int rowsAffected = db.executeUpdate(insertQuery, 
	                bookItems.getBookName(),
	                bookItems.getAuthor(),
	                bookItems.getAvailable(), 
	                bookItems.getTotalBook(),
	                bookItems.getPublizerId(),
	                bookItems.getAmount(),
	                bookItems.getDomainId()
	            );

	            if (rowsAffected > 0) {
	                return "Book added successfully.";
	            } else {
	                return "Failed to add book.";
	            }
	        }
	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}



  public String getBook(int bookId) {
	    try {
	        ResultSet rs = db.executeQuery("SELECT * FROM Books WHERE book_id = ?", bookId);

	        if (rs != null && rs.next()) {
	            Book book = new Book(
	                rs.getInt("book_id"),
	                rs.getString("bookName"),
	                rs.getString("author"),
	                rs.getBoolean("available"), 
	                rs.getInt("totalBook"),
	                rs.getInt("publizerId"),
	                rs.getInt("amount"),
	                rs.getInt("domainId")
	            );
	            return formatBookDetails(book);
	        } else {
	            return "Book is currently not present in the library.";
	        }
	    } catch (Exception e) {
	        return e.getMessage();
	    }
	}
  public String addUser(User user, String password) {
	    try {
	        // Check if user already exists in the database based on email
	        ResultSet rs = db.executeQuery("SELECT * FROM users WHERE email = ?", user.getEmail());

	        if (rs != null && rs.next()) {
	            return "User with email: " + user.getEmail() + " already exists.";
	        }

	        // Insert user into the database (Skipping user_id because it's AUTO_INCREMENT)
	        String insertQuery = "INSERT INTO users (username, email, password_hash, role) VALUES (?, ?, ?, ?)";
	        int rowsAffected = db.executeUpdate(insertQuery, 
	            user.getUserName(), 
	            user.getEmail(), 
	            password, 
	            user.getRole()
	        );

	        if (rowsAffected > 0) {
	            return "User " + user.getUserName() + " added successfully!";
	        } else {
	            return "Failed to add user.";
	        }
	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}

  //use collection
  /*
  public String removeBookById(int bookId) {
      try {
          if (!book.containsKey(bookId)) {
              return "Book not found with ID: " + bookId;
          }

          Book b = book.get(bookId);
          int updatedTotalBook = b.decrementTotalBook();
          //b.isAvailable(updatedTotalBook);
          b.setAvailable(b.isAvailable(updatedTotalBook));
          if (updatedTotalBook == 0) {
        	  book.put(b.getBookId(), b);
              //book.remove(bookId); 
          }
          return "Book with ID: " + bookId + " removed successfully.";
      } catch (Exception e) {
          return e.getMessage();
      }
  }*/
// using collection
  /*
  public String removeBookByName(String bookName) {
      try {
          for (Map.Entry<Integer, Book> entry : book.entrySet()) {
              Book b = entry.getValue();
              if (b.getBookName().equalsIgnoreCase(bookName)) {
                  int updatedTotalBook = b.decrementTotalBook();
                  b.setAvailable(b.isAvailable(updatedTotalBook));
                  if (updatedTotalBook == 0) {
                	  book.put(b.getBookId(), b);
                      //book.remove(entry.getKey());  
                  }
                  return "Book with name: " + bookName + " removed successfully.";
              }
          }
          return "Book not found with name: " + bookName;
      } catch (Exception e) {
          return e.getMessage();
      }
  }*/
  public String removeBookById(int bookId) {
	    try {
	        // Check if book exists in the database
	        String checkQuery = "SELECT totalBook FROM Books WHERE book_id = ?";
	        ResultSet rs = db.executeQuery(checkQuery, bookId);

	        if (rs == null || !rs.next()) {
	            return "Book not found with ID: " + bookId;
	        }

	        int totalBook = rs.getInt("totalBook");

	        if (totalBook > 1) {
	            // If multiple copies exist, decrement the count
	            String updateQuery = "UPDATE Books SET totalBook = totalBook - 1, available = ? WHERE book_id = ?";
	            boolean isAvailable = (totalBook - 1) > 0;
	            int rowsAffected = db.executeUpdate(updateQuery, isAvailable, bookId);

	            return rowsAffected > 0 ? "Book count updated for ID: " + bookId + ", removed one book" : "Failed to update book count.";
	        } else {
	            // If only one copy exists, delete the book from the database
	            String deleteQuery = "DELETE FROM Books WHERE book_id = ?";
	            int rowsDeleted = db.executeUpdate(deleteQuery, bookId);

	            return rowsDeleted > 0 ? "Book with ID: " + bookId + " removed successfully." : "Failed to delete book.";
	        }
	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}

	public String removeBookByName(String bookName) {
	    try {
	        // Check if book exists in the database
	        String checkQuery = "SELECT book_id, totalBook FROM Books WHERE bookName = ?";
	        ResultSet rs = db.executeQuery(checkQuery, bookName);

	        if (rs == null || !rs.next()) {
	            return "Book not found with name: " + bookName;
	        }

	        int bookId = rs.getInt("book_id");
	        int totalBook = rs.getInt("totalBook");

	        if (totalBook > 1) {
	            // If multiple copies exist, decrement the count
	            String updateQuery = "UPDATE Books SET totalBook = totalBook - 1, available = ? WHERE bookName = ?";
	            boolean isAvailable = (totalBook - 1) > 0;
	            int rowsAffected = db.executeUpdate(updateQuery, isAvailable, bookName);

	            return rowsAffected > 0 ? "Book count updated for name: " + bookName + ", removed one book" : "Failed to update book count.";
	        } else {
	            String deleteQuery = "DELETE FROM Books WHERE bookName = ?";
	            int rowsDeleted = db.executeUpdate(deleteQuery, bookName);

	            return rowsDeleted > 0 ? "Book with name: " + bookName + " removed successfully." : "Failed to delete book.";
	        }
	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}

//using Collection
  /*
  public String borrowBook(int bookId, int userId) {
      try {
          //  Check if user exists
    	  DBConnect db=new DBConnect();
    	  String Userretrive ="SELECT * FROM USERS WHERE userid=?";
    	  ResultSet rs=db.executeQuery(Userretrive,userId);
          if (rs==null ||!rs.next()) {
              return "Sorry, first you have to register using addUser().";
          }
          String username =rs.getString("username"); //  Get existing user
          
          // Check if book exists
          if (!book.containsKey(bookId)) {
              return "Book with ID " + bookId + " not found.";
          }
          
          Book bookItem = book.get(bookId);
          if (bookItem.getTotalBook() <= 0) {
              return "Sorry, the book " + bookItem.getBookName() + " is out of stock.";
          }

          //  Update Book Details
          bookItem.decrementTotalBook();
          bookItem.setuserId(userId);
          book.put(bookId, bookItem);

          //  Set Borrow Date
          Date issuedDate = new Date();
          Calendar cal = Calendar.getInstance();
          cal.setTime(issuedDate);
          cal.add(Calendar.DATE, 14);
          Date returnDate = cal.getTime();

          //  Update the existing User object (DO NOT create a new User)
          User updatedUser = users.get(userId);
          updatedUser.setIssueDate(issuedDate);
          updatedUser.setReturnDate(returnDate);
          updatedUser.setReturned(false);
          users.put(userId, updatedUser);// ✅ Update user in users map

          return user.getUserName() + " borrowed the book: " + bookItem.getBookName() + "\n" +
                 "Issued Date: " + issuedDate + "\n" +
                 "Return Date: " + returnDate;
      } catch (Exception e) {
          return e.getMessage();
      }
  }
*/
  
	public String borrowBook(int bookId, int userId) {
	    try {
	        // Check if user has pending fees
	        String checkFees = "SELECT isPaid, fees_amount FROM BorrowedBooks WHERE user_id = ?";
	        ResultSet userFees = db.executeQuery(checkFees, userId);
	        if (userFees != null) {
	            while (userFees.next()) {
	                if (!userFees.getBoolean("isPaid") && userFees.getInt("fees_amount") > 0) {
	                    return userId + " has pending fees. You cannot borrow another book until you pay.";
	                }
	            }
	        }

	        // Check if user exists
	        String checkUserQuery = "SELECT username FROM Users WHERE user_id = ?";
	        ResultSet userRs = db.executeQuery(checkUserQuery, userId);

	        if (userRs == null || !userRs.next()) {
	            return "Sorry, first you have to register using addUser().";
	        }

	        String userName = userRs.getString("username");

	        // Check if book exists
	        String checkBookQuery = "SELECT bookname, totalBook FROM Books WHERE book_id = ?";
	        ResultSet bookRs = db.executeQuery(checkBookQuery, bookId);

	        if (bookRs == null || !bookRs.next()) {
	            return "Book with ID " + bookId + " not found.";
	        }

	        String bookName = bookRs.getString("bookname");
	        int totalBook = bookRs.getInt("totalBook");

	        if (totalBook <= 0) {
	            return "Sorry, the book " + bookName + " is out of stock.";
	        }

	        // Update book count in Books table
	        String updateBookQuery = "UPDATE Books SET totalBook = totalBook - 1 WHERE book_id = ?";
	        int bookUpdated = db.executeUpdate(updateBookQuery, bookId);

	        if (bookUpdated <= 0) {
	            return "Failed to update book stock.";
	        }

	        // Set Issue and Return Date
	        Date issuedDate = new Date();
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(issuedDate);
	        cal.add(Calendar.DATE, 14);
	        Date returnDate = cal.getTime();

	        // Insert record in BorrowedBooks table
	        String insertBorrowQuery = "INSERT INTO BorrowedBooks (user_id, book_id, issued_date, return_date, is_returned) VALUES (?, ?, ?, ?, ?)";
	        int borrowInserted = db.executeUpdate(insertBorrowQuery, userId, bookId, issuedDate, returnDate, false);

	        if (borrowInserted > 0) {
	            return userName + " borrowed the book: " + bookName + "\n" +
	                   "Issued Date: " + issuedDate + "\n" +
	                   "Return Date: " + returnDate;
	        } else {
	            return "Failed to record borrowing details.";
	        }

	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}
  public String getAllUsers() {
	    StringBuilder result = new StringBuilder();
	    
	    try {
	        String query = "SELECT user_id, username, email, role FROM users";  // Fixed column name "username"
	        ResultSet rs = db.executeQuery(query);
	        
	        if (!rs.isBeforeFirst()) { // Check if result set is empty
	            return "No users found in the database.";
	        }

	        result.append(" All Registered Users:\n");
	        
	        while (rs.next()) {
	            int userId = rs.getInt("user_id");
	            String userName = rs.getString("username");  // Fixed column name
	            String email = rs.getString("email");
	            String role = rs.getString("role");
	      
	            result.append(" ID: ").append(userId)
	                  .append(" | Name: ").append(userName)
	                  .append(" | Email: ").append(email)
	                  .append(" | Role: ").append(role)
	                  .append("\n");
	        }
	        
	    } catch (Exception e) {
	        return "Error retrieving users: " + e.getMessage();
	    }
	    
	    return result.toString();
	}

  public String viewAllUsers() {
	    return getAllUsers();
	}
  public String viewBorrowedBooks() {
	    try {
	        String query = "SELECT user_id, book_id, issued_date, return_date, fees_amount, isPaid FROM BorrowedBooks WHERE is_returned = false";
	        ResultSet rs = db.executeQuery(query);
	        StringBuilder result = new StringBuilder("Borrowed Books List \n");
	        while (rs.next()) {
	            result.append("User ID: ").append(rs.getInt("user_id"))
	                  .append(", Book ID: ").append(rs.getInt("book_id"))
	                  .append(", Issued Date: ").append(rs.getDate("issued_date"))
	                  .append(", Due Date: ").append(rs.getDate("return_date"))
	                  .append(", Fees: ₹").append(rs.getInt("fees_amount"))
	                  .append(", Paid: ").append(rs.getBoolean("isPaid") ? "Yes" : "No")
	                  .append("\n");
	        }
	        return result.toString();
	    } catch (SQLException e) {
	        return "Error: " + e.getMessage();
	    }
	}

  // Return a Book(use the collection)
  /*
  public String returnBook(int bookId,int userId) {
      try {
    	  User useraccess=new User();
          if (!book.containsKey(bookId)) {
              return "Book with ID " + bookId + " not found.";
          }

          Book bookItem = book.get(bookId);
          User user = users.get(useraccess.getUserId());
 
          if (bookItem.getUserId() != useraccess.getUserId()) {
              return "You did not borrow this book.";
          }

          if (user.isReturned()) {
              return "The book is already returned.";
          }
          Date currentDate = new Date();
          long diffInMillies = currentDate.getTime() - user.getReturnDate().getTime();
          int diffDays = (int) (diffInMillies / (1000 * 60 * 60 * 24));

          if (diffDays > 0) {
        	  int amount=user.getFeesAmount()+diffDays;
        	  user.setFeesAmount(amount);
              //user. += diffDays; 
          }
          bookItem.incrementTotalBook();
          bookItem.setuserId(0);
          book.put(bookId, bookItem);

          // Update User Details in the Map
          user.setReturned(true);
          user.setReturnDate(currentDate);
          users.put(user.getUserId(), user);

          return user.getUserName() + " returned the book: " + bookItem.getBookName() + "\n" +
                 "Returned Date: " + currentDate + "\n" +
                 "Late Fees: ₹" + user.getFeesAmount();
      } catch (Exception e) {
          return e.getMessage();
      }
  }*/
  
  public String returnBook(int bookId, int userId) {
	    try {
	        // Check if the user has borrowed this book and get its payment status
	        String checkBorrowQuery = "SELECT return_date, is_returned, fees_amount, isPaid FROM BorrowedBooks WHERE book_id = ? AND user_id = ? AND is_returned = false";
	        ResultSet borrowRs = db.executeQuery(checkBorrowQuery, bookId, userId);

	        if (borrowRs == null || !borrowRs.next()) {
	            return "You did not borrow this book or it has already been returned.";
	        }

	        Date expectedReturnDate = borrowRs.getDate("return_date");
	        boolean isReturned = borrowRs.getBoolean("is_returned");
	        int existingLateFees = borrowRs.getInt("fees_amount");
	        boolean isPaid = borrowRs.getBoolean("isPaid");

	        if (isReturned) {
	            return "The book is already returned.";
	        }

	        // Calculate late fees 
	        Date currentDate = new Date();
	        long diffInMillies = currentDate.getTime() - expectedReturnDate.getTime();
	        int diffDays = (int) (diffInMillies / (1000 * 60 * 60 * 24));

	        int lateFees = (diffDays > 0) ? diffDays * 1 : 0; // ₹1 per late day
	        int totalLateFees = existingLateFees + lateFees;

	        // Prevent returning the book if there are unpaid late fees
	        if (totalLateFees > 0 && !isPaid) {
	            return "You have a pending late fee of ₹" + totalLateFees + ". Please pay before returning the book.";
	        }

	        // Mark book as returned and update fees amount
	        String updateBorrowQuery = "UPDATE BorrowedBooks SET is_returned = true, return_date = ?, fees_amount = ? WHERE book_id = ? AND user_id = ?";
	        int updateBorrowed = db.executeUpdate(updateBorrowQuery, currentDate, totalLateFees, bookId, userId);

	        if (updateBorrowed <= 0) {
	            return "Failed to update return status.";
	        }

	        // Update book stock
	        String updateBookQuery = "UPDATE Books SET totalBook = totalBook + 1 WHERE book_id = ?";
	        db.executeUpdate(updateBookQuery, bookId);

	        
	        if (totalLateFees == 0) {
	            String updatePaidQuery = "UPDATE BorrowedBooks SET isPaid = true WHERE book_id = ? AND user_id = ?";
	            db.executeUpdate(updatePaidQuery, bookId, userId);
	            isPaid = true;
	        }

	        return "Book returned successfully!\n" +
	               "Return Date: " + currentDate + "\n" +
	               "Late Fees: ₹" + totalLateFees + "\n" +
	               "Payment Status: " + (isPaid ? "Paid" : "Pending");

	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}

	public String checkPendingFees(int userId, int bookId) {
	    try {
	        String query = "SELECT fees_amount, isPaid FROM BorrowedBooks WHERE user_id = ? AND book_id = ?";
	        ResultSet rs = db.executeQuery(query, userId, bookId);

	        if (rs == null || !rs.next()) {
	            return "No pending fees found for user ID " + userId + " and book ID " + bookId + ".";
	        }

	        int pendingFees = rs.getInt("fees_amount");
	        boolean isPaid = rs.getBoolean("isPaid");

	        return "Pending fees for Book ID " + bookId + ": ₹" + pendingFees + "\n" +
	               "Payment Status: " + (isPaid ? "Paid" : "Not Paid");
	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}

	public String payFees(int userId, int bookId, int amount) {
	    try {
	        String query = "SELECT fees_amount, isPaid FROM BorrowedBooks WHERE user_id = ? AND book_id = ?";
	        ResultSet rs = db.executeQuery(query, userId, bookId);

	        if (rs == null || !rs.next()) {
	            return "No fees found for Book ID " + bookId + " and User ID " + userId + ".";
	        }

	        int pendingFees = rs.getInt("fees_amount");
	        boolean isPaid = rs.getBoolean("isPaid");

	        if (isPaid) {
	            return "The fees for Book ID " + bookId + " have already been paid.";
	        }

	        if (amount < pendingFees) {
	            return "Insufficient amount! You need to pay ₹" + pendingFees + ", but you paid ₹" + amount + ".";
	        }

	        db.executeUpdate("UPDATE BorrowedBooks SET isPaid = true, fees_amount = 0 WHERE user_id = ? AND book_id = ?", userId, bookId);
	        
	        return "Payment successful! ₹" + pendingFees + " has been paid for Book ID " + bookId;
	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}


 
  private String formatBookDetails(Book book) {
	  return "Book Details:\n" +
              "Book ID: " + book.getBookId() + "\n" +
              "Name: " + book.getBookName() + "\n" +
              "Author: " + book.getAuthor() + "\n" +
              "TotalNumber: " + book.getTotalBook() + "\n" +
              "Available: " + book.getAvailable();
  }
  
  
}
