package Model;
import DBConnection.DBConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class BookDAOImpl implements BookDAO {
	private DBConnect db;

    public BookDAOImpl() {
        db =DBConnect.getInstance();
    }

    public String addBook(Book book) throws SQLException {
        String query = "INSERT INTO Books (bookName, author, available, totalBook, publizerId, amount, domainId) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = db.executeUpdate(query,
		    book.getBookName(),
		    book.getAuthor(),
		    book.getAvailable(),
		    book.getTotalBook(),
		    book.getPublizerId(),
		    book.getAmount(),
		    book.getDomainId()
		);
		return rowsAffected > 0 ? "Book added successfully." : "Failed to add book.";
    }

    public String removeBookById(int bookId) throws SQLException {
        String query = "DELETE FROM Books WHERE book_id = ?";
        int rowsDeleted = db.executeUpdate(query, bookId);
		return rowsDeleted > 0 ? "Book removed successfully." : "Book not found.";
    }
    public boolean doesBookExist(int bookId) {
        String query = "SELECT book_id FROM Books WHERE book_id = ?";
        ResultSet rs = null;
        try {
            rs = db.executeQuery(query, bookId);
            return rs != null && rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.closeResources(rs, null, null);
        }
    }
    public Book getBookById(int bookId) {
        String query = "SELECT * FROM Books WHERE book_id = ?";
        ResultSet rs = null;
        try {
            rs = db.executeQuery(query, bookId);
            if (rs.next()) {
                return new Book(
                    rs.getInt("book_id"),
                    rs.getString("bookName"),
                    rs.getString("author"),
                    rs.getBoolean("available"),
                    rs.getInt("totalBook"),
                    rs.getInt("publizerId"),
                    rs.getInt("amount"),
                    rs.getInt("domainId")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeResources(rs, null, null);
        }
        return null;
    }

    public List<Book> getAllBooks() {
        String query = "SELECT * FROM Books";
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();
        try {
            rs = db.executeQuery(query);
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("book_id"),
                    rs.getString("bookName"),
                    rs.getString("author"),
                    rs.getBoolean("available"),
                    rs.getInt("totalBook"),
                    rs.getInt("publizerId"),
                    rs.getInt("amount"),
                    rs.getInt("domainId")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeResources(rs, null, null);
        }
        return books;
    }
}
