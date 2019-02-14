package pl.somehost.contactmanager.domain.dto;

public class AdressBookDto {

    private int id;
    private String adressBookName;

    public AdressBookDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdressBookName() {
        return adressBookName;
    }

    public void setAdressBookName(String adressBookName) {
        this.adressBookName = adressBookName;
    }
}
