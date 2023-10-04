package api.error;

public class InvalidConfirmationDataException extends RuntimeException {

    public InvalidConfirmationDataException(String message) {
        super(message);
    }
}