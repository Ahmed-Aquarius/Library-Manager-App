class RegularUser extends User
                  implements Borrowable {
    
    public RegularUser (String name) {
        super(name);
    }


    
    public void viewBookCatalog () {

    }

    @Override
    public boolean borrowBook(String bookId) {

    }

    @Override
    public boolean returnBook(String bookId) {

    }
}
