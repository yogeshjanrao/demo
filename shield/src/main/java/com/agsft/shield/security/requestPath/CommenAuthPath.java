package com.agsft.shield.security.requestPath;

import com.agsft.shield.security.requestPath.constant.RoleAuthSet;
import com.agsft.shield.security.requestPath.model.AuthPath;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CommenAuthPath {

    public static List<AuthPath> getCommonAuthPath(){
        List<AuthPath> authPathList = new ArrayList<>();
        authPathList.add(new AuthPath(HttpMethod.POST, "/shield/avengers/{avengerId}/mission/{status}", RoleAuthSet.PERMIT_ALL));
        return authPathList;
    }
}
