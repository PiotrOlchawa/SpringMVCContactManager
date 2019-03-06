package pl.somehost.contactmanager.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageSendException extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSendException.class);

    public MessageSendException(String message) {
        super(message);
        LOGGER.info("MessageSendException was Thrown");
    }
}
