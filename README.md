Library Management System (Console-Based Java Project)

This is a console-based Java application implementing a Library Management System using OOPs principles, MySQL database, DAO and MVC design patterns. The system allows normal users to borrow and return books with fee calculations, and librarians to manage the book collection.

Features

 Normal User
-  View available books
-  Borrow a book (with issue date & due date)
-  Return a book
-  View fine amount if return is delayed

 Librarian
-  Add new books to the library
-  Update book details
-  View all borrowed books and users

 Concepts Used
- Object-Oriented Programming (OOP):
  - Encapsulation
  - Inheritance
  - Polymorphism
  - Abstraction
- MVC Architecture (Model-View-Controller)
- DAO Pattern (Data Access Object)
- MySQL Database Integration using JDBC
- Modular Code Structure
  
 Database: library_db
- Tables:
  - books - stores book details
  - users - stores user and librarian data
  - borrowed_books- tracks book issues and returns
 
How to Run:
 -Clone the repo
 -Set up MySQL database  
      -Import the SQL file (if provided)
      -Update DB credentials in the DB connection class
 -Compile and run
 -Compile using javac
 -javac Main.java

