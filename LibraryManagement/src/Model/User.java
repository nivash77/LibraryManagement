package Model;
import java.util.*;

import controllers.Library;

public  class User {
    private int userId;
    private String userName;
    private Library library; 
    private Date issuedDate;
    private Date returnDate;
    private boolean isReturned;
    private int feesAmount;
    private String email;
    private String role;

    // Constructor
//    public User(int userId, String userName) {
//        this.userId = userId;
//        this.userName = userName;
//        this.isReturned = false;
//        this.feesAmount = 0;
//        this.issuedDate = null;
//        this.returnDate = null;
//    }
    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
    public User(int userId, String userName, String email, String role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.role = role;
    }


    // Borrow a Book
//    public String borrowBook(int bookId, int userId) {
//        try {
//            //  Check if user exists
//            if (!library.users.containsKey(userId)) {
//                return "Sorry, first you have to register using addUser().";
//            }
//            User user = library.users.get(userId); // ✅ Get existing user
//            
//            //  Check if book exists
//            if (!library.book.containsKey(bookId)) {
//                return "Book with ID " + bookId + " not found.";
//            }
//            
//            Book bookItem = library.book.get(bookId);
//            if (bookItem.getTotalBook() <= 0) {
//                return "Sorry, the book " + bookItem.getBookName() + " is out of stock.";
//            }
//
//            //  Update Book Details
//            bookItem.decrementTotalBook();
//            bookItem.setuserId(userId);
//            library.book.put(bookId, bookItem);
//
//            //  Set Borrow Date
//            Date issuedDate = new Date();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(issuedDate);
//            cal.add(Calendar.DATE, 14);
//            Date returnDate = cal.getTime();
//
//            //  Update the existing User object (DO NOT create a new User)
//            User updatedUser = library.users.get(userId);
//            updatedUser.setIssueDate(issuedDate);
//            updatedUser.setReturnDate(returnDate);
//            updatedUser.setReturned(false);
//            library.users.put(userId, updatedUser);// ✅ Update user in users map
//
//            return user.getUserName() + " borrowed the book: " + bookItem.getBookName() + "\n" +
//                   "Issued Date: " + issuedDate + "\n" +
//                   "Return Date: " + returnDate;
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }
//
//
//    // Return a Book
//    public String returnBook(int bookId) {
//        try {
//            if (!library.book.containsKey(bookId)) {
//                return "Book with ID " + bookId + " not found.";
//            }
//
//            Book bookItem = library.book.get(bookId);
//            User user = library.users.get(userId);
//   
//            if (bookItem.getUserId() != userId) {
//                return "You did not borrow this book.";
//            }
//
//            if (user.isReturned) {
//                return "The book is already returned.";
//            }
//            Date currentDate = new Date();
//            long diffInMillies = currentDate.getTime() - user.returnDate.getTime();
//            int diffDays = (int) (diffInMillies / (1000 * 60 * 60 * 24));
//
//            if (diffDays > 0) {
//                user.feesAmount += diffDays; 
//            }
//            bookItem.incrementTotalBook();
//            bookItem.setuserId(0);
//            library.book.put(bookId, bookItem);
//
//            // Update User Details in the Map
//            user.isReturned = true;
//            user.returnDate = currentDate;
//            library.users.put(userId, user);
//
//            return userName + " returned the book: " + bookItem.getBookName() + "\n" +
//                   "Returned Date: " + currentDate + "\n" +
//                   "Late Fees: ₹" + user.feesAmount;
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }

    // Get User Details
    public String getUserDetails() {
        return "User ID: " + userId + "\n" +
               "User Name: " + userName + "\n" +
               "Issued Date: " + issuedDate + "\n" +
               "Return Date: " + returnDate + "\n" +
               "Returned: " + isReturned + "\n" +
               "Fees Amount: ₹" + feesAmount;
    }
    public String toString() {
        return "User ID: " + userId + "\n" +
               "User Name: " + userName + "\n" +
               "Issued Date: " + issuedDate + "\n" +
               "Return Date: " + returnDate + "\n" +
               "Returned: " + isReturned + "\n" +
               "Fees Amount: ₹" + feesAmount;
    }
    
    //public abstract String getRole();
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
    public Date getIssuedDate(){
    	return issuedDate; 
    	}
    public Date getReturnDate(){ 
    	return returnDate; 
    	}
    public boolean isReturned(){
    	return isReturned;
    	}
    public int getFeesAmount(){ 
    	return feesAmount; 
    	}

    public void setFeesAmount(int feesAmount){
    	this.feesAmount = feesAmount; 
    	}
    public void setReturnDate(Date returnDate){
    	this.returnDate = returnDate; 
    	}
    public void setReturned(boolean returned){
    	this.isReturned = returned; 
    	}
    public void setIssueDate(Date issueDate){
    	this.issuedDate = issueDate; 
    	}
    
   
}
