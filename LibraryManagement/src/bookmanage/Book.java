package bookmanage;

public class Book {
	private int bookId;
	private String bookname;
	private String author;
	private boolean Available;
	private  int totalBook=0;
	private int publizerId;
	private int amount;
	private int userId;
	private int domainId;
	public Book(int bookId,String bookname,String author,int publizerId,int amount,int userId,int domainId){
		this.bookId=bookId;
		this.bookname=bookname;
		this.author=author;
		this.publizerId=publizerId;
		this.amount=amount;
		this.userId=userId;
		this.domainId=domainId;
	}
	public Book(Book other) {
	    this.bookId = other.bookId;
	    this.bookname = other.bookname;
	    this.author = other.author;
	    this.Available = other.Available;
	    this.totalBook = other.totalBook;
	    this.publizerId = other.publizerId;
	    this.amount = other.amount;
	    this.userId = other.userId;
	    this.domainId = other.domainId;
	}
	 public int getBookId() {
	        return bookId;
	    }

	    public String getBookName() {
	        return bookname;
	    }

	    public String getAuthor() {
	        return author;
	    }

	    
	    public int gettotalBook() {
	    	return totalBook;
	    }
	    public int decrementTotalBook() { 
	        if (totalBook > 0) {
	            totalBook--;
	        }
	        return totalBook;
	    }
	    
	    public boolean isAvailable(int count) {
	    	if(count==0) {
	    		return false;
	    	}
	    	return true;
	    }
	    public boolean getAvailable() {
	    	return Available;
	    }
	    public void setAvailable(boolean available) {
	        this.Available = available;
	    }
	    public void setBookId(int bookId) {
	    	this.bookId=bookId;
	    }
	    public void setBookName(String bookname) {
	    	this.bookname=bookname;
	    }
	    public void setauthor(String author) {
	    	this.author=author;
	    }
	    public void incrementTotalBook() {
	        this.totalBook++;  
	    }
	    public void setpublizerId(int publizedId) {
	    	this.publizerId=publizerId;
	    }
	    public void setAmount(int amount) {
	    	this.amount=amount;
	    }
	    public void setuserId(int userId) {
	    	this.userId=userId;
	    }
	    public void setdomainId(int domainId) {
	    	this.domainId=domainId;
	    }
	
	
}
