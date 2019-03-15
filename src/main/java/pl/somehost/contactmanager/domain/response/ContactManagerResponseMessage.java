package pl.somehost.contactmanager.domain.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ContactManagerResponseMessage {
    private String message;

    public ContactManagerResponseMessage() {
    }

    public ContactManagerResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
