package com.agsft.shield.constant;

public enum RoleConstant {

    ADMIN("ADMIN"), USER("USER");

    String roleValue;

    RoleConstant(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getRoleValue() {
        return roleValue;
    }
}
