class Admin extends User {

    private static int no_admins = 1;


    
    public Admin (String password, String name) {
        super(password, name);
        this.id = generateID();

        no_admins++;
    }



    @Override
    protected String generateID () {
        return "AD" + String.valueOf(no_admins);
    }



    public boolean addBook (String id, String title, String author, Genre genre, int noAvailableCopies) {
        Book newBook = new Book(id, title, author, genre, noAvailableCopies);
        return db.createBook(newBook);
    }

    public boolean addBook (String title, String author, Genre genre, int noAvailableCopies) {
        Book newBook = new Book(title, author, genre, noAvailableCopies); 
        return db.createBook(newBook);
    }

    public <T> boolean editBook (String bookID, ModifiableBookAttribute attribute, T newValue) {
        return db.editBook(bookID, attribute, newValue);
    }

    public boolean deleteBook (String bookID) {
        return db.deleteBook(bookID);
    }



    public boolean registerUser (String name, String password, UserRole role) {
        User newUser = null;

        switch (role)
        {
            case ADMIN -> newUser = new Admin(name, password);
            case REGULAR_USER -> newUser = new RegularUser(name, password);
        }

        return db.createUser(newUser);
    }
}
