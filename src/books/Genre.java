package books;

public enum Genre {
    NOVEL_ROMANTIC("novel: romantic"),
    NOVEL_ACTION("novel: action"),
    NOVEL_FANTASY("novel: fantasy"),
    EDUCATIONAL_COMPUTER_SCIENCE("educational: computer_science"),
    EDUCATIONAL_MEDICINE("educational: medicine"),
    EDUCATIONAL_BUSINESS("educational: business"),
    OTHER("other");


    
    private final String db_enum;

    Genre (String db_name) {
        this.db_enum = db_name;
    }

    @Override
    public String toString() {
        return db_enum;
    }

    public static Genre stringToGenre (String s) {
        for (Genre genre : Genre.values())
        {
            if (genre.db_enum == s)
            {
                return genre;
            }
        }
        return null;
    }
}
