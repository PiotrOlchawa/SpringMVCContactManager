package pl.somehost.contactmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "User")
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "adressbook_id")
    private AdressBook adressBook;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "user",
            targetEntity = Authorities.class,
            orphanRemoval = true)
    private Set<Authorities> authorities;


    public User() {
    }

    public User(String username, String password, Set<Authorities> authorities, AdressBook adresBook) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.adressBook = adresBook;
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

    public AdressBook getAdressBook() {
        return adressBook;
    }

    public void setAdressBook(AdressBook adressBook) {
        this.adressBook = adressBook;
    }

    public Set<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

/*        @Override
    public String toString() {
        return "User{" + "\n"+
                "id=" + id + "\n"+
                ", username='" + username + '\'' + "\n"+
                ", password='" + password + '\'' + "\n"+
                ", adressBook=" + adressBook + "\n"+
                ", authorities=" + authorities + "\n"+
                '}';
    }*/
}
