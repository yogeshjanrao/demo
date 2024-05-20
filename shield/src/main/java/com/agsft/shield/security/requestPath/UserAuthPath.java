package com.agsft.shield.security.requestPath;

import com.agsft.shield.security.requestPath.constant.RoleAuthSet;
import com.agsft.shield.security.requestPath.model.AuthPath;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserAuthPath {

    public static List<AuthPath> getUserAuthPath(){
        List<AuthPath> authPathList = new ArrayList<>();
        authPathList.add(new AuthPath(HttpMethod.GET, "/shield/mission", RoleAuthSet.USER_ONLY));
        authPathList.add(new AuthPath(HttpMethod.POST, "/shield/mission/complete/{missionId}", RoleAuthSet.USER_ONLY));
        return authPathList;
    }

}
