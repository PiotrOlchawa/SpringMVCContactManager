package pl.somehost.contactmanager.domain.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.AdressBook;
import pl.somehost.contactmanager.domain.Authorities;
import pl.somehost.contactmanager.validator.PasswordConstraint;

import javax.validation.constraints.Size;
import java.util.Set;

@Component
public class UserDto {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDto.class);
    private int id;
    @Size(min = 3)
    private String username;
    @PasswordConstraint // Custom annotation it could be replaced with @Pattern
    private String password;
    // ToDo: Validating by validator Set<Authorities> against ENUM Roles ?
    private Set<Authorities> authorities;
    private AdressBook adresBook;

    public UserDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    public AdressBook getAdresBook() {
        return adresBook;
    }

    public void setAdresBook(AdressBook adresBook) {
        this.adresBook = adresBook;
    }

    @Override
    public String toString() {
        return "UserDto{" + "\n" +
                "id=" + id + "\n" +
                ", username='" + username + '\'' + "\n" +
                ", password='" + password + '\'' + "\n" +
                ", authorities=" + authorities + "\n" +
                ", adresBook=" + adresBook + "\n" +
                '}';
    }
}
