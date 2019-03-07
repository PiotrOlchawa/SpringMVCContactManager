package pl.somehost.contactmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@PropertySource(value = "classpath:contactmanager.properties")  // Set external properties in Enviroment
@EnableScheduling
public class ApplicationConfiguration {

    @Bean
    JavaMailSender mailSenderImpl(){
        return new JavaMailSenderImpl();
    }

}
