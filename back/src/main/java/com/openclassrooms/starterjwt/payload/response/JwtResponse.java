package com.openclassrooms.starterjwt.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String firstName;
    private String lastName;

    private Boolean admin;

    public JwtResponse(String accessToken, Long id, String username, String firstName, String lastName, Boolean admin) {
        this.token = accessToken;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtResponse that = (JwtResponse) o;
        return java.util.Objects.equals(token, that.token)
                && java.util.Objects.equals(type, that.type)
                && java.util.Objects.equals(id, that.id)
                && java.util.Objects.equals(username, that.username)
                && java.util.Objects.equals(firstName, that.firstName)
                && java.util.Objects.equals(lastName, that.lastName)
                && java.util.Objects.equals(admin, that.admin);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(token, type, id, username, firstName, lastName, admin);
    }
}
