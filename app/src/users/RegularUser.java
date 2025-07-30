package users;

import books.Book;
import books.ModifiableBookAttribute;
import java.util.Set;
import users.exceptions.*;

public class RegularUser extends User
                  implements Borrowable {

    //this constructor is only used when a user is retreived from the DB only, so it doesn't increase the actual number of users in the DB
    public RegularUser (String id, String password, String name, Set<String> borrowedBooksIDs) {
        super(id, password, name, borrowedBooksIDs);
    }

    public RegularUser (String password, String name) {
        super(password, name);
        this.id = generateID();
    }
    


    @Override
    public void borrowBook(String bookId) throws BookAlreadyBorrowedException, NoSuchBookException, NoAvailableCopiesException {
        if (borrowedBooksIDs.contains(bookId))
        {
            throw new BookAlreadyBorrowedException("this book is already borrowed");
        }

        Book retreivedBook = db.retreiveBook(bookId);

        if (retreivedBook == null)
        {
            throw new NoSuchBookException("no book with this ID");
        } else {
            if (retreivedBook.getNoAvailableCopies() <= 0)
            {
                throw new NoAvailableCopiesException("no available copies left");
            }
        }

        reflectBorrow(retreivedBook);
    }

    @Override
    public void returnBook(String bookId) {
        if (!borrowedBooksIDs.contains(bookId))
        {
            throw new ReturnUnborrowedBookException("this book wasn't borrowed already");
        }

        Book targetBook = db.retreiveBook(bookId);

        reflectReturn(targetBook);
    }



    private void reflectBorrow (Book targetBook) {
        borrowedBooksIDs.add(targetBook.id);

        db.editBook(targetBook.id, ModifiableBookAttribute.NO_AVAILABLE_COPIES, targetBook.getNoAvailableCopies() - 1);
        db.addBorrowedBook(this.id, targetBook.id);
    }

    private void reflectReturn (Book targetBook) {
        borrowedBooksIDs.remove(targetBook.id);

        db.editBook(targetBook.id, ModifiableBookAttribute.NO_AVAILABLE_COPIES, targetBook.getNoAvailableCopies() + 1);
        db.deleteBorrowedBook(this.id, targetBook.id);
    }
}
