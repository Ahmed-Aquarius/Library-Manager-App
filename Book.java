import java.time.LocalDateTime;


enum Genre {
    NOVEL_ROMANTIC("novel: romantic"),
    NOVEL_ACTION("novel: action"),
    NOVEL_FANTASY("novel: fantasy"),
    EDUCATIONAL_COMPUTER_SCIENCE("educational: computer_science"),
    EDUCATIONAL_MEDICINE("educational: medicine"),
    EDUCATIONAL_BUSINESS("educational: business"),
    OTHER("other");


    
    private final String genreName;

    Genre (String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return genreName;
    }
}


enum ModifiableBookAttribute {
    ID ("id"),
    TITLE ("title"),
    AUTHOR ("author"),
    GENRE ("genre"),
    NO_AVAILABLE_COPIES ("no_available_copies");

    private final String db_colName;

    ModifiableBookAttribute (String db_colName) {
        this.db_colName = db_colName;
    }

    @Override
    public String toString() {
        return db_colName;
    }
}


class Book {
    private static long noBooks = 1;

    private String id;
    public String title;
    public String author;
    public Genre genre;
    private int no_availableCopies;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;



    public Book(String id, String title, String author, Genre genre, int no_availableCopies, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.no_availableCopies = no_availableCopies;

        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Book(String id, String title, String author, Genre genre, int no_availableCopies) {
        this(id, title, author, genre, no_availableCopies, LocalDateTime.now(), null);
    }

    public Book(String title, String author, Genre genre, int no_availableCopies) {
        this(generateID(), title, author, genre, no_availableCopies);
    }



    public String getId() {
        return id;
    }

    public int getNoAvailableCopies() {
        return no_availableCopies;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }



    public void setId(String newValue) {
        this.id = newValue;
    }

    public void setNoAvailableCopies(int newValue) {
        this.no_availableCopies = newValue;
    }



    private static String generateID () {
        return String.valueOf(noBooks);
    }

} 