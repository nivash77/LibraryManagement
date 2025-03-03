package Model;
import DBConnection.DBConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private DBConnect db;

    public UserDAOImpl() {
        db =DBConnect.getInstance();
    }
    public User loginUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password_hash = ?";
        ResultSet rs = null;
        try {
            rs = db.executeQuery(query, email, password);
            if (rs != null && rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("username");
                String role = rs.getString("role");
                if ("Librarian".equals(role)) {
                    return new Librarian(userId, userName, email);
                } else {
                    return new NormalUser(userId, userName, email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeResources(rs, null, null);
        }
        return null; 
    }
    
    
    public String addUser(User user, String password) throws SQLException {
        String query = "INSERT INTO users (username, email, password_hash, role) VALUES (?, ?, ?, ?)";
        int rowsAffected = db.executeUpdate(query,
		    user.getUserName(),
		    user.getEmail(),
		    password,
		    user.getRole()
		);
		return rowsAffected > 0 ? "User added successfully." : "Failed to add user.";
    }
    public int registerUser(User user, String password) {
        
        if (doesUserExistByEmail(user.getEmail())) {
            return -2;
        }

        String query = "INSERT INTO users (username, email, password_hash, role) VALUES (?, ?, ?, ?)";
        try {
            int rowsAffected = db.executeUpdate(query,
                user.getUserName(),
                user.getEmail(),
                password,
                user.getRole()
            );

            if (rowsAffected > 0) {
                String fetchIdQuery = "SELECT user_id FROM users WHERE email = ?";
                ResultSet rs = db.executeQuery(fetchIdQuery, user.getEmail());
                if (rs != null && rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }
    
    public int getSelfNoByBookName(String bookName) {
        String query = "SELECT d.selfNo " +
                       "FROM Books b " +
                       "JOIN Domains d ON b.domainId = d.domainId " +
                       "WHERE b.bookName = ?";
        try  {
           
            ResultSet rs = db.executeQuery(query,bookName);
            if (rs.next()) {
                return rs.getInt("selfNo"); 
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        }
    }

    
    public String ChangePassword(String email,String oldpassword,String newpassword) {
    	if(!doesUserExistByEmail(email)) {
    		return "User does Not Exists";
    	}
    	if(CheckPassword(email,oldpassword)) {
    		String query="UPDATE users SET password_hash=? WHERE email=?";
    		db.executeUpdate(query,newpassword,email);
    		return "Password change successfully";
    	}
    	else {
    		return "password is not match";
    	}
    	
    }
     public boolean CheckPassword(String email,String password) {
    	try {
    	
    	String query="SELECT password_hash FROM users WHERE email=?";
    	ResultSet rs=db.executeQuery(query,email);
    	if(rs.next() && rs.getString("password_hash").equals(password)) {
    		return true;
    	}
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());  
    		return false;
    		}
    	return false;
    	
    }
    
    private boolean doesUserExistByEmail(String email) {
        String query = "SELECT user_id FROM users WHERE email = ?";
        ResultSet rs = null;
        try {
            rs = db.executeQuery(query, email);
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.closeResources(rs, null, null);
        }
    }
    
    public User getUserById(int userId) {
    	ResultSet rs = null;
        try {
        	String query = "SELECT * FROM users WHERE user_id = ?";
            rs = db.executeQuery(query, userId);
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeResources(rs, null, null);
        }
        return null;
    }
    public boolean doesUserExist(int userId) {
        String query = "SELECT user_id FROM users WHERE user_id = ?";
        ResultSet rs = null;
        try {
            rs = db.executeQuery(query, userId);
            return rs != null && rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.closeResources(rs, null, null);
        }
    }
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            rs = db.executeQuery(query);
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeResources(rs, null, null);
        }
        return users;
    }
}