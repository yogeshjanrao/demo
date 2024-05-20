package com.agsft.shield.security.requestPath.model;

import com.agsft.shield.security.requestPath.constant.RoleAuthSet;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

public class AuthPath {

    private HttpMethod httpMethod;

    private final String path;
    private final RoleAuthSet roleAuthSet;

    public AuthPath(HttpMethod httpMethod, String path, RoleAuthSet roleAuthSet) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.roleAuthSet = roleAuthSet;
    }

    public AuthPath(String path, RoleAuthSet roleAuthSet) {
        this.path = path;
        this.roleAuthSet = roleAuthSet;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public RoleAuthSet getRoleAuthSet() {
        return roleAuthSet;
    }
}
