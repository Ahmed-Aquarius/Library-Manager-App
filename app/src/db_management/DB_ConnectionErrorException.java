package db_management;

public class DB_ConnectionErrorException extends Exception {
    public DB_ConnectionErrorException(String message) {
        super(message);
    }
}
