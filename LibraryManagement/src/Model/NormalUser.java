package Model;

public class NormalUser extends User{
	private boolean hasPendingFees;

    public NormalUser(int userId, String userName, boolean hasPendingFees) {
        super(userId, userName);
        this.hasPendingFees = hasPendingFees;
    }
    public NormalUser(int userId, String userName) {
        super(userId, userName);
    }
 
    public String getRole() {
        return "Library Member";
    }

    public boolean hasPendingFees() {
        return hasPendingFees;
    }

    public void setPendingFees(boolean hasPendingFees) {
        this.hasPendingFees = hasPendingFees;
    }
    public String borrowBook() {
        return "User can borrow books.";
    }
}
