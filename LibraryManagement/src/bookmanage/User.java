package bookmanage;
import java.util.*;


public class User extends Library {

    private int userId;
    private String userName;
    private Date issuedDate;
    private Date returnDate;
    private boolean isReturned;
    private int feesAmount;
    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.isReturned = false;  
        this.feesAmount = 0;
        this.issuedDate = null;   
        this.returnDate = null;
    }
    public String borrowBook(int bookId) {
        try {
            if (book.containsKey(bookId)) {
                Book bookItem = book.get(bookId);
                if (bookItem.gettotalBook() > 0) {
                    bookItem.decrementTotalBook();
                    issuedDate = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(issuedDate);
                    cal.add(Calendar.DATE, 14);
                    returnDate = cal.getTime();
                    return userName + " borrowed the book: " + bookItem.getBookName() + "\n" +
                            "Issued Date: " + issuedDate + "\n" +
                            "Return Date: " + returnDate;
                } 
                else {
                    return "Sorry, the book " + bookItem.getBookName() + " is out of stock.";
                }
            }
            return "Book with ID " + bookId + " not found.";
        } 
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String returnBook(int bookId) {
        try {
            if (book.containsKey(bookId)) {
                Book bookItem = book.get(bookId);
      
                if (!isReturned) {
                    Date currentDate = new Date();
                    long diffInMillies = currentDate.getTime() - returnDate.getTime();
                    int diffDays = (int)(diffInMillies / (1000 * 60 * 60 * 24));
                    if (diffDays > 0) {
                        feesAmount = diffDays * 1;  
                    }
                    isReturned = true;
                    bookItem.incrementTotalBook();
                    return userName + " returned the book: " + bookItem.getBookName() + "\n" +
                            "Returned Date: " + currentDate + "\n" +
                            "Fees: " + feesAmount;
                } 
                else {
                    return "The book is already returned.";
                }
            }
            return "Book with ID " + bookId + " not found.";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public String getUserDetails() {
        return "User ID: " + userId + "\n" +
                "User Name: " + userName + "\n" +
                "Is Returned: " + isReturned + "\n" +
                "Fees Amount: " + feesAmount;
    }
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public double getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(int feesAmount) {
        this.feesAmount = feesAmount;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
