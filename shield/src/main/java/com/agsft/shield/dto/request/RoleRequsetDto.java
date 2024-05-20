package com.agsft.shield.dto.request;

public class RoleRequsetDto {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleRequsetDto{" +
                "role='" + role + '\'' +
                '}';
    }
}
