class RegularUser extends User
                  implements Borrowable {
    
    public RegularUser (String name) {
        super(name);
    }



    //view book catalog (in menu class)



    @Override
    public void borrowBook(String bookId) {
        if (borrowedBooksIDs.contains(bookId))
        {
            throw new BookAlreadyBorrowedException("this book is already borrowed");
        }

        int no_availableCopies = 0; //get it from the DB instead
        if (no_availableCopies <= 0)
        {
            throw new NoAvailableCopiesException("no available copies left");
        }

        borrowedBooksIDs.add(bookId);
        //save the no_availableCopies - 1 in DB
    }

    @Override
    public void returnBook(String bookId) {
        if (!borrowedBooksIDs.contains(bookId))
        {
            throw new ReturnUnborrowedBookException("this book wasn't borrowed already");
        }

        borrowedBooksIDs.remove(bookId);
        //increment the available copies in DB
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
