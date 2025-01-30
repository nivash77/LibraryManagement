package bookmanage;
import java.util.*;
public class Library{
  public  Map<Integer,Book> book;
  public Map<Integer,User> users;
  public Library(){
	  book=new HashMap<>();
	  users=new HashMap<>();
  }
  public String addBook (Book bookItems) {
	  try {
		  if(!book.containsKey(bookItems.getBookId())) {
			  bookItems.incrementTotalBook();
			  //bookItems.isAvailable(bookItems.gettotalBook());
			  bookItems.setAvailable(bookItems.isAvailable(bookItems.gettotalBook()));
		 book.put(bookItems.getBookId(),bookItems);
		 //System.out.println(book.get(bookItems.getBookId()).getBookName());
		  return "Book added successfully.";
		  }
		  else {
              Book existingBook = book.get(bookItems.getBookId());
              existingBook.incrementTotalBook();
              book.put(bookItems.getBookId(), existingBook);
              return "Book already exists with ID: " + bookItems.getBookId() + ". Total count updated.";
          }
	  }
	  catch(Exception e) {
		  return e.getMessage();
	  }
	  
  }
  public String getBook(int bookId) {
	  try {
		  if(!book.containsKey(bookId)) {
			  return "Book is cuurently not present in the library";
		  }
		  Book b=book.get(bookId);
		  return formatBookDetails(b);
		  
	  }
	  catch(Exception e) {
		  return e.getMessage();
	  }
  }
  public String addUser(User user) {
      try {
          if (!users.containsKey(user.getUserId())) {
              users.put(user.getUserId(), user);
              return "User added successfully.";
          }
          return "User with ID: " + user.getUserId() + " already exists.";
      } catch (Exception e) {
          return e.getMessage();
      }
  }
  
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
  }

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
  }
  private String formatBookDetails(Book book) {
	  return "Book Details:\n" +
              "Book ID: " + book.getBookId() + "\n" +
              "Name: " + book.getBookName() + "\n" +
              "Author: " + book.getAuthor() + "\n" +
              "TotalNumber: " + book.gettotalBook() + "\n" +
              "Available: " + book.getAvailable();
  }
  
  
}
