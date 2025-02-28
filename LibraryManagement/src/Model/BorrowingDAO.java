package Model;

public interface BorrowingDAO {
	String borrowBook(int bookId, int userId);
    String returnBook(int bookId, int userId);
    String viewBorrowedBooks();
    String checkPendingFees(int userId, int bookId);
    String payFees(int userId, int bookId, int amount);
}
