package Main;
import bookmanage.Library;
import bookmanage.Book;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Library library = new Library();

	        // Add books to the library
	        Book book1 = new Book(1, "Java Programming", "James Gosling", 101, 500, 0, 1);
	        Book book2 = new Book(2, "Effective Java", "Joshua Bloch", 102, 600, 0, 1);

	        System.out.println(library.addBook(book1)); 
	        System.out.println(library.addBook(book2)); 
	        System.out.println(library.addBook(book1)); 

	        System.out.println(library.getBook(1)); 
	        System.out.println(library.getBook(2));
	        System.out.println(library.removeBookById(1));
	        System.out.println(library.getBook(1)); 
	        System.out.println(library.removeBookById(2));
	        System.out.println(library.getBook(2));

	}

}
