import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Singleton design pattern
class DB_Manager {
    private static DB_Manager instance = null;

    private final String username = "Ahmed";
    private final String password = "1234";
    private final String db_name = "library_manager";

    private String url;

    private Connection conn;



    public static DB_Manager getInstance () {
        if (instance == null)
        {
            try {
                instance = new DB_Manager();
            } catch (DB_ConnectionErrorException ex) {
                instance = null;
            }
        }

        return instance;
    }

    private DB_Manager() throws DB_ConnectionErrorException {
        this.url = "jdbc:mysql://localhost:3306/" + this.db_name;

        try {
            this.conn = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException ex) {
            throw new DB_ConnectionErrorException("error connecting to the database");
        }
    }



    public boolean createBook (Book newBook) {
        String query = """
                        INSERT INTO books (id, title, author, genre, no_available_copies)
                        VALUES (?, ?, ?, ?, ?);
                        """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, newBook.getId());        
            stmt.setString(2, newBook.title);
            stmt.setString(3, newBook.author);
            stmt.setString(4, newBook.genre.toString());
            stmt.setInt(5, newBook.getNoAvailableCopies());

            stmt.executeUpdate();
        }
        catch (SQLException ex) {
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
                case GENRE -> stmt.setString(1, ((Genre) newValue).toString());
                case NO_AVAILABLE_COPIES -> stmt.setInt(1, (Integer) newValue);
                default -> stmt.setString(1, (String) newValue);
            }

            stmt.setString(2, bookID);        

            stmt.executeUpdate();

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

            stmt.executeUpdate();

        }
        catch (SQLException ex) {
            return false;
        }

        return true;
    }

    public Book retreiveBook (String bookID) {
        String query = """
                        SELECT *
                        FROM books
                        WHERE id = ?
                        """;

        Book retreivedBook = null;


        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, bookID);        

            ResultSet rs = stmt.executeQuery();


            if (rs.next())
            {
                retreivedBook = new Book(
                    bookID,
                    rs.getString("title"),
                    rs.getString("author"),
                    Genre.valueOf(rs.getString("genre")),
                    rs.getInt("no_available_copies"),
                    rs.getTimestamp("added_at").toLocalDateTime(),
                    rs.getTimestamp("deleted_at").toLocalDateTime()
                );
            }    
        }
        catch (SQLException ex) {
            return null;
        }

        return retreivedBook;
    }



    public boolean createUser (User newUser) {
        String query = """
                        INSERT INTO users (id, password, name)
                        VALUES (?, ?, ?);
                        """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, newUser.id);        
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, newUser.name);

            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            return false;
        }

        return true;
    }

}



class DB_ConnectionErrorException extends Exception {
    public DB_ConnectionErrorException(String message) {
        super(message);
    }
}
