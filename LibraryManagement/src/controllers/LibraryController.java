package controllers;
import java.sql.SQLException;

import Model.Book;
import Model.BookDAO;
import Model.BorrowingDAO;
import java.util.List;

public class LibraryController {
	private BookDAO bookDAO;
    private BorrowingDAO borrowingDAO;

    public LibraryController(BookDAO bookDAO, BorrowingDAO borrowingDAO) {
        this.bookDAO = bookDAO;
        this.borrowingDAO = borrowingDAO;
    }
  
    public String addBook(Book book) throws SQLException {
        return bookDAO.addBook(book);
    }

    public String removeBookById(int bookId) throws SQLException {
        return bookDAO.removeBookById(bookId);
    }

    public String borrowBook(int bookId, int userId) {
        return borrowingDAO.borrowBook(bookId, userId);
    }

    public String returnBook(int bookId, int userId) {
        return borrowingDAO.returnBook(bookId, userId);
    }

    public String viewBorrowedBooks() {
        return borrowingDAO.viewBorrowedBooks();
    }

    public String checkPendingFees(int userId, int bookId) {
        return borrowingDAO.checkPendingFees(userId, bookId);
    }

    public String payFees(int userId, int bookId, int amount) {
        return borrowingDAO.payFees(userId, bookId, amount);
    }
    public List<Book> getAllBook(){
    	return bookDAO.getAllBooks();
    }
}
