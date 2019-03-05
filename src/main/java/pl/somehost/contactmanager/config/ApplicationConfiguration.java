package pl.somehost.contactmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource(value = "classpath:application.properties")  // Set external properties in Enviroment
public class ApplicationConfiguration {

    @Bean
    JavaMailSender mailSenderImpl(){
        return new JavaMailSenderImpl();
    }

}
