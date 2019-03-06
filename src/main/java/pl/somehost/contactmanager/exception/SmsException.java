package pl.somehost.contactmanager.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsException extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsException.class);

    public SmsException(String message) {
        super(message);
        LOGGER.info("SmsException was Thrown");
    }
}
