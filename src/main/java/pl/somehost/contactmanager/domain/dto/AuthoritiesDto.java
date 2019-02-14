package pl.somehost.contactmanager.domain.dto;

import pl.somehost.contactmanager.domain.User;

public class AuthoritiesDto {

    private Long id;
    private String authority;
    private User user;

    public AuthoritiesDto() {
    }

    public AuthoritiesDto(Long id, String authority, User user) {
        this.id = id;
        this.authority = authority;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
