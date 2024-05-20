package com.agsft.shield.security.requestPath.constant;

import com.agsft.shield.constant.RoleConstant;

import static com.agsft.shield.constant.RoleConstant.*;

public enum RoleAuthSet {

    ADMIN_USER(ADMIN), USER_ONLY(USER), PERMIT_ALL(ADMIN, USER);

    private final RoleConstant[] roleAuthSets;

    private RoleAuthSet(RoleConstant ... roles){
        this.roleAuthSets = roles;
    }

    public String [] getRoles(){
        String [] roles = new String[roleAuthSets.length];
        for (int i = 0; i< roleAuthSets.length; i++){
            roles[i] = roleAuthSets[i].getRoleValue();
        }
        return roles;
    }
}
