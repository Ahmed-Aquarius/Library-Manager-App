package users.exceptions;

public class ReturnUnborrowedBookException extends RuntimeException {
    public ReturnUnborrowedBookException(String message) {
        super(message);
    }
}
