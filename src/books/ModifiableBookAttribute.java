package books;

public enum ModifiableBookAttribute {
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
