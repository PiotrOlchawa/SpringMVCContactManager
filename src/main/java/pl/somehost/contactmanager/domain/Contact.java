package pl.somehost.contactmanager.domain;

import javax.persistence.*;

@Entity(name = "contact")
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "streetAdress")
    private String streetAdress;
    @Column(name = "zipCode")
    private String zipCode;
    @Column(name = "aptNumber")
    private String aptNumber;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "adressbook_id")
    private AdressBook adressBook;

    public Contact(){
    }

    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private String streetAdress;
        private String zipCode;
        private String aptNumber;
        private String telephone;
        private String email;
        private AdressBook adressBook;

        public Builder(){
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setStreetAdress(String streetAdress) {
            this.streetAdress = streetAdress;
            return this;
        }

        public Builder setZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder setAptNumber(String aptNumber) {
            this.aptNumber = aptNumber;
            return this;
        }

        public Builder setTelephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setAdressBook(AdressBook adressBook) {
            this.adressBook = adressBook;
            return this;
        }

        public Contact build(){
            return new Contact(id,firstName,lastName,streetAdress,zipCode,aptNumber,telephone,email);
        }
    }

    private Contact(int id, String firstName, String lastName, String streetAdress,
                   String zipCode, String aptNumber, String telephone, String email) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public void setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdressBook getAdressBook() {
        return adressBook;
    }

    public void setAdressBook(AdressBook adressBook) {
        this.adressBook = adressBook;
    }
}
