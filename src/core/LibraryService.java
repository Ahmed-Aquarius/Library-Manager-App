package core;

import books.*;
import db_management.DB_ConnectionErrorException;
import db_management.DB_Manager;
import java.util.Set;
import users.User;

class LibraryService {

    private DB_Manager db;



    public LibraryService() {
        try {
            db = new DB_Manager();
        } catch (DB_ConnectionErrorException e) {
            e.printStackTrace();
        }
    }



    public User login (String userID, String password) throws InvalidIDException, WrongPasswordException {
        User loggedInUser = this.db.retreiveUser(userID);
        if (loggedInUser == null)
        {
            throw new InvalidIDException("no user with this id");
        } else {
            if (password.equals(loggedInUser.getPassword()))
            {
                return loggedInUser;
            } else {
                throw new WrongPasswordException("wrong password");
            }
        }
    }

    public Set<Book> getBookCatalog () {
        return db.retreiveAllBooks();
    }

}



class InvalidIDException extends Exception {
    public InvalidIDException(String message) {
        super(message);
    }
}

class WrongPasswordException extends Exception {
    public WrongPasswordException(String message) {
        super(message);
    }
}
