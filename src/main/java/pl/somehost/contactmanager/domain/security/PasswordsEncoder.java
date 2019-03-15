package pl.somehost.contactmanager.domain.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordsEncoder {

    @Autowired
    private ApplicationContext appContext;

    public String encode(String password) {
        PasswordEncoder passwordEncoder = (PasswordEncoder)appContext.getBean("passwordEncoder");
        return passwordEncoder.encode(password);
    }
}
