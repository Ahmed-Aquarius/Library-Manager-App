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

class Book {
    private String id;
    public String title;
    public String author;
    public Genre genre;
    private int noAvailableCopies;



    public Book(String author, Genre genre, String id, int noAvailableCopies, String title) {
        this.author = author;
        this.genre = genre;
        this.id = id;
        this.noAvailableCopies = noAvailableCopies;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public int getNoAvailableCopies() {
        return noAvailableCopies;
    }
} 