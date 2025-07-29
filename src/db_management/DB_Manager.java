package db_management;

import books.*;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;
import users.*;



public class DB_Manager {
    private final String username = System.getenv("DB_USER");
    private final String password = System.getenv("DB_PASSWORD");;
    private final String db_name = System.getenv("DB_NAME");
    private final String host = System.getenv("DB_HOST");
    private final String port = System.getenv("DB_PORT");

    private String url;

    private Connection conn;


    public DB_Manager() throws DB_ConnectionErrorException {
        //this.url = "jdbc:mysql://" + host + ":" + port + "/" + db_name;
        this.url = "jdbc:mysql://mysql-db:3306/library_manager";

        try {
            this.conn = DriverManager.getConnection(this.url, "Ahmed", "1234");
        } catch (SQLException e) {
            throw new DB_ConnectionErrorException("error connecting to the database");
        }
    }



    public boolean addBook (Book newBook) {
        String query = """
                        INSERT INTO books (id, title, author, genre, no_available_copies)
                        VALUES (?, ?, ?, ?, ?);
                        """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, newBook.id);        
            stmt.setString(2, newBook.title);
            stmt.setString(3, newBook.author);
            stmt.setString(4, newBook.genre.toString());
            stmt.setInt(5, newBook.getNoAvailableCopies());

            int rowsAffectted = stmt.executeUpdate();
            if (rowsAffectted == 0)
            {
                Book.setNoBooks(Book.getNoBooks() - 1);
                return false;
            }
        }
        catch (SQLException ex) {
            Book.setNoBooks(Book.getNoBooks() - 1);

            return false;
        }

        return true;
    }       

    public <T> boolean editBook (String bookID, ModifiableBookAttribute attribute, T newValue) {
        String columnName = attribute.toString();

        String query = "UPDATE books SET " + columnName + " = ? WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            switch (attribute) {
                case NO_AVAILABLE_COPIES -> stmt.setInt(1, (Integer) newValue);
                default -> stmt.setString(1, (String) newValue);
            }

            stmt.setString(2, bookID);        

            int rowsAffectted = stmt.executeUpdate();
            if (rowsAffectted == 0)
            {
                return false;
            }

        }
        catch (SQLException ex) {
            return false;
        }

        return true;
    }

    public boolean deleteBook (String bookID) {
        String query = """
                        DELETE FROM books
                        WHERE id = ?;
                        """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, bookID);        

            int rowsAffectted = stmt.executeUpdate();
            if (rowsAffectted == 0)
            {
                return false;
            }
        }
        catch (SQLException ex) {
            return false;
        }

        return true;
    }

    public Book retreiveBook (String bookId) {
        String query = """
                        SELECT *
                        FROM books
                        WHERE id = ?;
                    """;

        Book retreivedBook = null;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, bookId);        

            ResultSet rs = stmt.executeQuery();
            rs.next();

            retreivedBook = new Book(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("author"),
                Genre.stringToGenre(rs.getString("genre")),
                rs.getInt("no_available_copies"),
                rs.getTimestamp("added_at").toLocalDateTime()
            );
        }
        catch (SQLException ex) {
            return null;
        }

        return retreivedBook;
    }

    public Set<Book> retreiveAllBooks () {
        String query = """
                        SELECT *
                        FROM books
                        """;

        Set<Book> retreivedBooks = new LinkedHashSet<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);


            while (rs.next())
            {
                retreivedBooks.add(new Book(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    Genre.stringToGenre(rs.getString("genre")),
                    rs.getInt("no_available_copies"),
                    rs.getTimestamp("added_at").toLocalDateTime()
                ));
            }    
        }
        catch (SQLException ex) {
            return null;
        }

        return retreivedBooks;
    }

    public String getLastBookId () {
        String query = """
                        SELECT id
                        FROM books
                        ORDER BY id DESC
                        LIMIT 1
                        """;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return rs.getString("id");
        }
        catch (SQLException ex) {
            return "0";
        }
    }



    public boolean createUser (User newUser) {
        String query = """
                        INSERT INTO users (id, password, name, role)
                        VALUES (?, ?, ?, ?);
                        """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            UserRole role;
            if (newUser instanceof Admin)
            {
                role = UserRole.ADMIN;
            } else if (newUser instanceof RegularUser) {
                role = UserRole.REGULAR_USER;
            } else {
                role = null;
            }

            stmt.setString(1, newUser.id);        
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, newUser.name);
            stmt.setString(4, role.toString());

            int rowsAffectted = stmt.executeUpdate();
            if (rowsAffectted == 0)
            {
                User.setNoUsers(User.getNoUsers() - 1);
                return false;
            }
        }
        catch (SQLException ex) {
            User.setNoUsers(User.getNoUsers() - 1);
            return false;
        }

        return true;
    }

    public User retreiveUser (String userID) {
        String query = """
                        SELECT *
                        FROM users
                        WHERE id = ?
                        """;


        User retreivedUser = null;


        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, userID);        

            ResultSet rs = stmt.executeQuery();
            rs.next();

            UserRole role = UserRole.stringToUserRole(rs.getString("role"));
            
            switch (role)
            {
                case ADMIN:
                    retreivedUser = new Admin(
                        userID,
                        rs.getString("password"),
                        rs.getString("name"),
                        retreiveBorrowedBooks(userID)
                    );
                    break;
                case REGULAR_USER:
                    retreivedUser = new RegularUser(
                        userID,
                        rs.getString("password"),
                        rs.getString("name"),
                        retreiveBorrowedBooks(userID)
                    );
                    break;
            }
        }
        catch (SQLException ex) {
            return null;
        }

        return retreivedUser;
    }

    public String getLastUserId () {
        String query = """
                        SELECT id
                        FROM users
                        ORDER BY id DESC
                        LIMIT 1
                        """;

        try {
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return rs.getString("id");
        }
        catch (SQLException ex) {
            return "0";
        }
    }


    
    public boolean addBorrowedBook (String userId, String bookId)
    {
        String query = """
                        INSERT INTO borrowed_books (user_id, borrowed_book_id)
                        VALUES (?, ?);
                        """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, userId);        
            stmt.setString(2, bookId);

            int rowsAffectted = stmt.executeUpdate();
            if (rowsAffectted == 0)
            {
                return false;
            }
        }
        catch (SQLException ex) {
            return false;
        }

        return true;
    }

    public boolean deleteBorrowedBook (String userId, String bookId)
    {
        String query = """
                        DELETE FROM borrowed_books
                        WHERE user_id = ? AND borrowed_book_id = ?;
                        """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, userId);        
            stmt.setString(2, bookId);

            int rowsAffectted = stmt.executeUpdate();
            if (rowsAffectted == 0)
            {
                return false;
            }
        }
        catch (SQLException ex) {
            return false;
        }

        return true;
    }

    private Set<String> retreiveBorrowedBooks (String userID) {
        String query = """
                        SELECT borrowed_book_id
                        FROM borrowed_books
                        WHERE user_id = ?
                        """;


        Set<String> borrowedBooks = new LinkedHashSet<>();


        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, userID);        

            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                borrowedBooks.add(rs.getString("borrowed_book_id"));
            }
        }
        catch (SQLException ex) {
            return null;
        }

        return borrowedBooks;
    }
}
