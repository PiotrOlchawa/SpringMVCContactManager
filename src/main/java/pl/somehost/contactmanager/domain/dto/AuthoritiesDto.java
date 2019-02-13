package pl.somehost.contactmanager.domain.dto;

public class AuthoritiesDto {

    private Long id;
    private String authority;
    private UserDto user;

    public AuthoritiesDto() {
    }

    public AuthoritiesDto(Long id, String authority, UserDto user) {
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
