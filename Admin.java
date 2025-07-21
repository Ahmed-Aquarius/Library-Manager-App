class Admin extends User {

    public Admin (String name) {
        super(name);
    }



    public void addBook (String id, String title, String author, Genre genre, int noAvailableCopies) {
        Book newBook = new Book(id, title, author, genre, noAvailableCopies);
        //add to list of books in the library manager?
    }

    public void addBook (String title, String author, Genre genre, int noAvailableCopies) {
        Book newBook = new Book(title, author, genre, noAvailableCopies); 
        //add to list of books in the library manager?
    }

    public <T> void editBook (String bookID, BookAttribute attribute, T newValue) {
        Book targetBook = new Book(); //instead, do a function that retreives the book with its ID
        
        switch (attribute)
        {
            case ID:
                targetBook.setId((String) newValue);
                break;
            case TITLE:
                targetBook.title = (String) newValue;
                break;
            case AUTHOR:
                targetBook.author = (String) newValue;
                break;
            case GENRE:
                targetBook.genre = (Genre) newValue;
                break;
            case NO_AVAILABLE_COPIES:
                targetBook.setNoAvailableCopies((int) newValue);
                break;
        }

        //save the book in the DB
        //apply any changes in the manager
    }

    public void deleteBook (String bookID) {
        //delete the book from the DB
        //delete it from the manager
    }



    public void registerUser (String name, UserRole role) {
        User newUser;

        switch (role)
        {
            case ADMIN -> newUser = new Admin(name);
            case REGULAR_USER -> newUser = new RegularUser(name);
        }

        //store in DB with the role
    }
}
