package pl.somehost.contactmanager.domain.dto;

public class ContactDto {
    private int id;
    private String firstName;
    private String lastName;
    private String streetAdress;
    private String zipCode;
    private String aptNumber;
    private String telephone;
    private String email;

    public ContactDto() {
    }

    public ContactDto(int id, String firstName, String lastName, String privatestreetAdress,
                      String zipCode, String aptNumber, String telephone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAdress = streetAdress;
        this.zipCode = zipCode;
        this.aptNumber = aptNumber;
        this.telephone = telephone;
        this.email = email;
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

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder streetAdress(String streetAdress) {
            this.streetAdress = streetAdress;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder aptNumber(String aptNumber) {
            this.aptNumber = aptNumber;
            return this;
        }

        public Builder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public ContactDto build() {
            return new ContactDto(id,firstName,lastName,streetAdress,zipCode,aptNumber,telephone,email);
        }
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

    @Override
    public String toString() {
        return "ContactDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", streetAdress='" + streetAdress + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", aptNumber='" + aptNumber + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
