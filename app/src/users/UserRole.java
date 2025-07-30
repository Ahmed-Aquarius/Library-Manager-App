package users;

public enum UserRole {
    ADMIN ("admin"),
    REGULAR_USER ("regular_user");

    private final String db_enum;

    UserRole (String db_enum)
    {
        this.db_enum = db_enum;
    }

    @Override
    public String toString() {
        return this.db_enum;
    }

    public static UserRole stringToUserRole (String s) {
        for (UserRole ur : UserRole.values())
        {
            if (ur.db_enum.equals(s))
            {
                return ur;
            }
        }
        return null;
    }
}