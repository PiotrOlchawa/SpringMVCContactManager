package pl.somehost.contactmanager.exception;

public class SmsException extends RuntimeException {
    public SmsException(String message) {
        super(message);
    }
}
