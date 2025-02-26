package controllers;

import DBConnection.DBConnect;
import Model.User;

import java.sql.*;

public class UserAuthentication {
    DBConnect db = new DBConnect();

    public int registerUser(User user, String password) {
        String query = "INSERT INTO users (username, email, password_hash, role) VALUES (?, ?, ?, ?)";
        String fetchIdQuery = "SELECT user_id FROM users WHERE email = ?"; // To retrieve user_id

        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             PreparedStatement idStmt = conn.prepareStatement(fetchIdQuery)) {

           
            if (user == null || user.getUserName() == null || user.getEmail() == null || user.getRole() == null || password == null) {
                return -1; 
            }

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, password); 
            pstmt.setString(4, user.getRole());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                // Fetch the generated user_id
                idStmt.setString(1, user.getEmail());
                ResultSet rs = idStmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("user_id"); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }

    

    public User loginUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password_hash");

                if (storedPassword.equals(password)) {  
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("role")
                    );
                } else {
                    System.out.println("Incorrect password!");
                }
            } else {
                System.out.println("User not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
