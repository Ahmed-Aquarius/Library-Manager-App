class RegularUser extends User
                  implements Borrowable {
    
    private static int no_regularUsers = 1;



    public RegularUser (String password, String name) {
        super(password, name);
        this.id = generateID();

        no_regularUsers++;
    }
                


    @Override
    protected String generateID () {
        return "RU" + String.valueOf(no_regularUsers);
    }



    @Override
    public void borrowBook(String bookId) {
        if (borrowedBooksIDs.contains(bookId))
        {
            throw new BookAlreadyBorrowedException("this book is already borrowed");
        }

        Book targetBook = db.retreiveBook(bookId);
        if (targetBook.getNoAvailableCopies() <= 0)
        {
            throw new NoAvailableCopiesException("no available copies left");
        }

        reflectBorrow(targetBook);
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
        borrowedBooksIDs.add(targetBook.getId());

        db.editBook(targetBook.getId(), ModifiableBookAttribute.NO_AVAILABLE_COPIES, targetBook.getNoAvailableCopies() - 1);
    }

    private void reflectReturn (Book targetBook) {
        borrowedBooksIDs.remove(targetBook.getId());

        db.editBook(targetBook.getId(), ModifiableBookAttribute.NO_AVAILABLE_COPIES, targetBook.getNoAvailableCopies() + 1);
    }

}



class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}


class NoAvailableCopiesException extends RuntimeException {
    public NoAvailableCopiesException(String message) {
        super(message);
    }
}


class ReturnUnborrowedBookException extends RuntimeException {
    public ReturnUnborrowedBookException(String message) {
        super(message);
    }
}
