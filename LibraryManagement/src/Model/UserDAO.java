package Model;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    String addUser(User user, String password)throws SQLException;
    User getUserById(int userId) ;
    boolean doesUserExist(int userId);
    int registerUser(User user, String password); 
    public String ChangePassword(String email,String oldpassword,String newpassword);
    boolean CheckPassword(String email,String password);
    User loginUser(String email, String password);
    List<User> getAllUsers() throws Exception;
}
