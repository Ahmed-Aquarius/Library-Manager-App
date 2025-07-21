import java.util.LinkedHashSet;
import java.util.Set;



enum UserRole {
    ADMIN,
    REGULAR_USER;
}


abstract class User {
    private static long noUsers = 1;

    private String id;
    public String name;
    private Set<String> borrowedBooks = new LinkedHashSet<>(); //used LinkedHashSet in case it's required to keep trach of the order at which the user borrowed their books



    public User (String name) {
        this.id = idGenerator();
        this.name = name;
        borrowedBooks = null;

        noUsers++;
    }



    private static String idGenerator () {
        return String.valueOf(noUsers);
    }
}
