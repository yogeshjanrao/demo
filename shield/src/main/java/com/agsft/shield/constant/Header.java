package com.agsft.shield.constant;

public enum Header {

    AUTHORIZATION("security");

    private String value;

    Header(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
