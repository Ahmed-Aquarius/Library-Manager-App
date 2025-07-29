package users;

import db_management.DB_ConnectionErrorException;
import db_management.DB_Manager;
import java.util.LinkedHashSet;
import java.util.Set;



public abstract class User {

    protected static DB_Manager db;
    protected static long no_users = 1;


    public String id;    //also acts as a username
    protected String password;
    public String name;
    protected Set<String> borrowedBooksIDs = new LinkedHashSet<>(); //used LinkedHashSet in case it's required to keep trach of the order at which the user borrowed their books



    static
    {
        try {
            db = new DB_Manager();
        } catch (DB_ConnectionErrorException e) {
            e.printStackTrace();
        }

        no_users = Long.parseLong(db.getLastUserId()) - 1;
    }


    
    //this constructor is only used when a user is retreived from the DB only, so it doesn't increase the actual number of users in the DB
    public User (String id, String password, String name, Set<String> borrowedBooksIDs) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.borrowedBooksIDs = borrowedBooksIDs;

        try {
            db = new DB_Manager();
        } catch (DB_ConnectionErrorException e) {
            e.printStackTrace();
        }
    }

    public User (String password, String name) {
        this(null, password, name, null);
        User.no_users++;
    }



    public static void setNoUsers (long no_users) {
        User.no_users = no_users;
    }

    public static long getNoUsers () {
        return User.no_users;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }



    protected static String generateID () {
        return String.valueOf(User.no_users + 1);
    }
}
