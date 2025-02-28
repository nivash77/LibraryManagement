package Model;

public class Librarian extends User{
	public Librarian(int userId, String userName,String email) {
        super(userId, userName,email,"User");
    }

    public String getRole() {
        return "Librarian";
    }
    public String manageBooks() {
        return "Librarian can add, remove, and update books.";
    }
}
