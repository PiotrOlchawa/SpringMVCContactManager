package pl.somehost.contactmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true) // for @Secured roles in methods
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

}
