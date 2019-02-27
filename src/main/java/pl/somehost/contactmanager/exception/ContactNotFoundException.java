package pl.somehost.contactmanager.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(Integer id, String message) {
        super("Contact id: " + id + " " + message);
    }
}
