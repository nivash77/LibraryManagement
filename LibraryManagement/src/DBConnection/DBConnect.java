package DBConnection;

import java.sql.*;

public class DBConnect {
    private String url = "jdbc:mysql://127.0.0.1:3306/library?serverTimezone=UTC";
    private String user = "root";
    private String password = "Nivash@#2k4";
    private Connection con;
    private static DBConnect instance;
    public DBConnect() {
        try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Database Connected Successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Not Found: " + e.getMessage());
        } catch (SQLException error) {
            System.out.println("Connection Error: " + error.getMessage());
        }
    }
    public static DBConnect getInstance() {
        if (instance == null) {
            instance = new DBConnect();
        }
        return instance;
    }
    public Connection getConnection() {
        return con;
    }

    public ResultSet executeQuery(String sql) {
        try {
            Statement st = con.createStatement();
            return st.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Query Execution Error: " + e.getMessage());
        }
        return null;
    }

    public boolean insert(String sql) {
        try {
            Statement st = con.createStatement();  
            st.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("Insert Error: " + e.getMessage());
        }
        return false;
    }
    public void createTables() {
        try {
            Statement stmt = con.createStatement();

         
            if (!tableExists("Users")) {
                String createUsersTable = "CREATE TABLE Users ("
                		+ "    user_id INT PRIMARY KEY AUTO_INCREMENT,"
                		+ "    user_name VARCHAR(255) NOT NULL,"
                		+ "    email VARCHAR(255) UNIQUE NOT NULL,"
                		+ "    password_hash VARCHAR(255) NOT NULL,"
                		+ "    role VARCHAR(50) NOT NULL"
                		+ ")";
                stmt.executeUpdate(createUsersTable);
                System.out.println("Table 'Users' Created Successfully!");
            }

           
            if (!tableExists("Books")) {
                String createBooksTable = "CREATE TABLE Books ("
                        + "bookId INT PRIMARY KEY AUTO_INCREMENT,"
                        + "bookname VARCHAR(255) NOT NULL,"
                        + "author VARCHAR(255) NOT NULL,"
                        + "Available BOOLEAN DEFAULT FALSE,"
                        + "totalBook INT DEFAULT 0,"
                        + "publizerId INT NOT NULL,"
                        + "amount INT NOT NULL,"
                        + "userId INT, "
                        + "domainId INT"
                        + ")";
                stmt.executeUpdate(createBooksTable);
                System.out.println(" Table 'Books' Created Successfully!");
            }

          
            if (!tableExists("BorrowedBooks")) {
                String createBorrowedBooksTable = "CREATE TABLE BorrowedBooks ("
                        + "userId INT NOT NULL,"
                        + "bookId INT NOT NULL,"
                        + "issuedDate DATE NOT NULL,"
                        + "returnDate DATE,"
                        + "isReturned BOOLEAN DEFAULT FALSE,"
                        + "feesAmount INT DEFAULT 0,"
                        + "FOREIGN KEY (userId) REFERENCES Users(userId) ON DELETE CASCADE,"
                        + "FOREIGN KEY (bookId) REFERENCES Books(bookId) ON DELETE CASCADE)";
                stmt.executeUpdate(createBorrowedBooksTable);
                System.out.println(" Table 'BorrowedBooks' Created Successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Table Creation Error: " + e.getMessage());
        }
    }

    private boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData dbm = con.getMetaData();
        ResultSet tables = dbm.getTables(null, null, tableName, null);
        return tables.next();
    }
    public ResultSet executeQuery(String query, Object... params) {
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeQuery();  
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public int executeUpdate(String query, Object... params) {
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            setParameters(stmt, params);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update Execution Error: " + e.getMessage());
            return 0;
        }
    }
    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }
    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Database Connection Closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error Closing Connection: " + e.getMessage());
        }
    }
}
