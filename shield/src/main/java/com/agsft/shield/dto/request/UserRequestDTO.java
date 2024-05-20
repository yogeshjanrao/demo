package com.agsft.shield.dto.request;

import com.agsft.shield.entitiy.Role;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UserRequestDTO {
    @Valid
    @NotEmpty
    @NotNull
    @NotBlank
    private String firstName;
    @Valid
    @NotEmpty
    @NotNull
    @NotBlank
    private String lastName;
    @Valid
    @NotEmpty
    @NotNull
    @NotBlank
    @Email
    private String email;
    @Valid
    @NotEmpty
    @NotNull
    @NotBlank
    private String password;
    @Valid
    @NotEmpty
    @NotNull
    @NotBlank
    private List<RoleRequsetDto> roles;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleRequsetDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleRequsetDto> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
