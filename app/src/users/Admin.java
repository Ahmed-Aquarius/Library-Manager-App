package users;

import books.Book;
import books.Genre;
import books.ModifiableBookAttribute;
import java.util.Set;

public class Admin extends User {

    //this constructor is only used when a user is retreived from the DB only, so it doesn't increase the actual number of users in the DB
    public Admin (String id, String password, String name, Set<String> borrowedBooksIDs) {
        super(id, password, name, borrowedBooksIDs);
    }

    public Admin (String password, String name) {
        super(password, name);
        this.id = generateID();
    }

    

    public boolean addBook (String id, String title, String author, Genre genre, int noAvailableCopies) {
        Book newBook = new Book(id, title, author, genre, noAvailableCopies);
        return db.addBook(newBook);
    }

    public boolean addBook (String title, String author, Genre genre, int noAvailableCopies) {
        Book newBook = new Book(title, author, genre, noAvailableCopies); 
        return db.addBook(newBook);
    }

    public <T> boolean editBook (String bookID, ModifiableBookAttribute attribute, T newValue) {
        return db.editBook(bookID, attribute, newValue);
    }

    public boolean deleteBook (String bookID) {
        return db.deleteBook(bookID);
    }



    public boolean registerUser (String password, String name, UserRole role) {
        User newUser = null;

        switch (role)
        {
            case ADMIN -> newUser = new Admin(password, name);
            case REGULAR_USER -> newUser = new RegularUser(password, name);
        }

        return db.createUser(newUser);
    }
}
