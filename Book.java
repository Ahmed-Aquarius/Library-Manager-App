enum Genre {
    NOVEL_ROMANTIC("Novel: Romantic"),
    NOVEL_FICTION("Novel: Fiction"),
    NOVEL_FANTASY("Novel: Fantasy"),
    EDUCATIONAL_COMPUTER_SCIENCE("Educational: Computer Science");

    private final String genreName;

    Genre (String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return genreName;
    }
}


enum BookAttribute {
    ID,
    TITLE,
    AUTHOR,
    GENRE,
    NO_AVAILABLE_COPIES;
}


class Book {
    private String id;
    public String title;
    public String author;
    public Genre genre;
    private int no_availableCopies;



    //to be removed
    public Book () {}

    public Book(String id, String title, String author, Genre genre, int no_availableCopies) {
        this(title, author, genre, no_availableCopies);
        this.id = id;
    }

    public Book(String title, String author, Genre genre, int no_availableCopies) {
        this.author = author;
        this.genre = genre;
        this.no_availableCopies = no_availableCopies;
        this.title = title;

        //connect to DB and insert the book??
    }



    public String getId() {
        return id;
    }

    public int getNoAvailableCopies() {
        return no_availableCopies;
    }


    
    public void setId(String newValue) {
        this.id = newValue;
    }

    public void setNoAvailableCopies(int newValue) {
        this.no_availableCopies = newValue;
    }
} 