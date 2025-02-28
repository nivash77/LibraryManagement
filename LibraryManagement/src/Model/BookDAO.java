package Model;
import java.sql.SQLException;
import java.util.List;
public interface BookDAO {
	String addBook(Book book)throws SQLException;
    String removeBookById(int bookId)throws SQLException;
 
    Book getBookById(int bookId);
    boolean doesBookExist(int bookId);
    List<Book> getAllBooks();
}
