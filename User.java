import java.util.LinkedHashSet;
import java.util.Set;



enum UserRole {
    ADMIN,
    REGULAR_USER;
}


abstract class User {

    protected static final DB_Manager db;



    static {
        db = DB_Manager.getInstance();
    }



    public String id;    //also acts as a username
    protected String password;
    public String name;
    protected Set<String> borrowedBooksIDs = new LinkedHashSet<>(); //used LinkedHashSet in case it's required to keep trach of the order at which the user borrowed their books



    public User (String password, String name) {
        this.id = null;
        this.password = password;
        this.name = name;
        borrowedBooksIDs = null;
    }



    public String getPassword() {
        return password;
    }



    protected abstract String generateID ();
}
