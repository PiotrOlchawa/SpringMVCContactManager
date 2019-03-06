package pl.somehost.contactmanager.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsExceptions extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsExceptions.class);

    public SmsExceptions(String message) {
        super(message);
        LOGGER.info("SmsExceptions was Thrown");
    }
}
