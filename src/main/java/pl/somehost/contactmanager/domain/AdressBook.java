package pl.somehost.contactmanager.domain;


import javax.persistence.*;
import java.util.List;

@Entity(name = "adressbook")
@Table(name = "adressbook")
public class AdressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(
            targetEntity = Contact.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "adressBook"
    )
    private List<Contact> contactList;

    public AdressBook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

}
