package com.agsft.shield.security.requestPath;

import com.agsft.shield.security.requestPath.constant.RoleAuthSet;
import com.agsft.shield.security.requestPath.model.AuthPath;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
public class PathFilter {

    public static void authFilter(HttpSecurity httpSecurity) throws Exception {
        List<AuthPath> authPathList = new ArrayList<>();
        authPathList.addAll(AdminAuthPath.getAdminAuthPath());
        authPathList.addAll(UserAuthPath.getUserAuthPath());
        authPathList.addAll(CommenAuthPath.getCommonAuthPath());

        for (AuthPath authPath : authPathList) {
            if (authPath.getHttpMethod() != null){
                if (authPath.getRoleAuthSet() == RoleAuthSet.PERMIT_ALL){
                    httpSecurity.authorizeRequests().antMatchers(authPath.getHttpMethod(), authPath.getPath()).permitAll();
                } else {
                    httpSecurity.authorizeRequests().antMatchers(authPath.getHttpMethod(), authPath.getPath()).hasAnyAuthority(authPath.getRoleAuthSet().getRoles());
                }
            }
        }
        httpSecurity.authorizeRequests().anyRequest().denyAll();
    }
}
