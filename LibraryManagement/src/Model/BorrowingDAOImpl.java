package Model;
import DBConnection.DBConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Calendar;
public class BorrowingDAOImpl implements BorrowingDAO{
	 private DBConnect db;
	 private UserDAO userdao;
	 private BookDAO bookdao;
	public BorrowingDAOImpl(UserDAO userDAO, BookDAO bookDAO) {
        this.db =DBConnect.getInstance();
        this.bookdao=bookDAO;
        this.userdao=userDAO;
        
    }
	public BorrowingDAOImpl() {};

    public String borrowBook(int bookId, int userId) {
        try {
            
            if (!userdao.doesUserExist(userId)) {
                return "Sorry, first you have to register using addUser().";
            }
            if (!bookdao.doesBookExist(bookId)) {
                return "Book with ID " + bookId + " not found.";
            }
            Book book = bookdao.getBookById(bookId);
            if (book.getTotalBook() <= 0) {
                return "Sorry, the book " + book.getBookName() + " is out of stock.";
            }
            String updateBookQuery = "UPDATE Books SET totalBook = totalBook - 1 WHERE book_id = ?";
            int bookUpdated = db.executeUpdate(updateBookQuery, bookId);
            if (bookUpdated <= 0) {
                return "Failed to update book stock.";
            }

            Date issuedDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(issuedDate);
            cal.add(Calendar.DATE, 14);
            Date returnDate = cal.getTime();

            String insertBorrowQuery = "INSERT INTO BorrowedBooks (user_id, book_id, issued_date, return_date, is_returned) VALUES (?, ?, ?, ?, ?)";
            int borrowInserted = db.executeUpdate(insertBorrowQuery, userId, bookId, issuedDate, returnDate, false);

            if (borrowInserted > 0) {
                return "Book borrowed successfully!";
            } else {
                return "Failed to record borrowing details.";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String returnBook(int bookId, int userId) {
        try {
           
            String checkBorrowQuery = "SELECT return_date, is_returned, fees_amount, isPaid FROM BorrowedBooks WHERE book_id = ? AND user_id = ? AND is_returned = false";
            ResultSet borrowRs = db.executeQuery(checkBorrowQuery, bookId, userId);

            if (borrowRs == null || !borrowRs.next()) {
                return "You did not borrow this book or it has already been returned.";
            }

            Date expectedReturnDate = borrowRs.getDate("return_date");
            Date currentDate = new Date();
            long diffInMillies = currentDate.getTime() - expectedReturnDate.getTime();
            int diffDays = (int) (diffInMillies / (1000 * 60 * 60 * 24));
            int lateFees = (diffDays > 0) ? diffDays * 1 : 0; 
            String updateBorrowQuery = "UPDATE BorrowedBooks SET is_returned = true, return_date = ?, fees_amount = ? WHERE book_id = ? AND user_id = ?";
            int updateBorrowed = db.executeUpdate(updateBorrowQuery, currentDate, lateFees, bookId, userId);

            if (updateBorrowed <= 0) {
                return "Failed to update return status.";
            }
            String updateBookQuery = "UPDATE Books SET totalBook = totalBook + 1 WHERE book_id = ?";
            db.executeUpdate(updateBookQuery, bookId);

            return "Book returned successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String viewBorrowedBooks() {
        try {
            String query = "SELECT user_id, book_id, issued_date, return_date, fees_amount,is_returned, isPaid FROM BorrowedBooks";
            ResultSet rs = db.executeQuery(query);
            StringBuilder result = new StringBuilder("Borrowed Books List \n");
            while (rs.next()) {
                result.append("User ID: ").append(rs.getInt("user_id"))
                      .append(", Book ID: ").append(rs.getInt("book_id"))
                      .append(", Issued Date: ").append(rs.getDate("issued_date"))
                      .append(", Due Date: ").append(rs.getDate("return_date"))
                      .append(", Fees: ₹").append(rs.getInt("fees_amount"))
                      .append(",isReturned: ").append(rs.getBoolean("is_returned"))
                      .append(", Paid: ").append(rs.getBoolean("isPaid") ? "Yes" : "No")
                      .append("\n");
            }
            return result.toString();
        } catch (SQLException e) {
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
}
