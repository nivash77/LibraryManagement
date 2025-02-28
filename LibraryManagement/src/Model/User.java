package Model;


public  class User {
    private int userId;
    private String userName;
    //private Library library; 
    private String email;
    private String role;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
    public User(int userId, String userName, String email, String role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.role = role;
    }


    public String getUserDetails() {
        return "User ID: " + userId + "\n" +
               "User Name: " + userName + "\n" +
               "User email: "+email+"\n"+
               "User Role: "+role+"\n";
        
               
    }
    public String toString() {
        return "User ID: " + userId + "\n" +
               "User Name: " + userName + "\n"+
        		"User email: "+email+"\n"+
                "User Role: "+role+"\n";
    }
               
    
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public  String getRole() {
        return role;
    }

    
   
}
