package com.agsft.shield.security.requestPath;

import com.agsft.shield.security.requestPath.constant.RoleAuthSet;
import com.agsft.shield.security.requestPath.model.AuthPath;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AdminAuthPath {

    public static List<AuthPath> getAdminAuthPath(){
        List<AuthPath> authPathList = new ArrayList<>();
        authPathList.add(new AuthPath(HttpMethod.POST, "/shield/avengers", RoleAuthSet.ADMIN_USER));
        authPathList.add(new AuthPath(HttpMethod.GET, "/shield/avengers", RoleAuthSet.ADMIN_USER));
        authPathList.add(new AuthPath(HttpMethod.POST, "/shield/avengers/{avengerId}/active/{notificationStatus}/service/{contact}/{mail}", RoleAuthSet.ADMIN_USER));
        authPathList.add(new AuthPath(HttpMethod.POST, "/shield/mission", RoleAuthSet.ADMIN_USER));
        authPathList.add(new AuthPath(HttpMethod.POST, "/shield/mission/assignment/{missionId}/assign/{avengerId}", RoleAuthSet.ADMIN_USER));
        authPathList.add(new AuthPath(HttpMethod.POST, "/shield/mission/assignment/find/avengers/{missionId}", RoleAuthSet.ADMIN_USER));
        authPathList.add(new AuthPath(HttpMethod.POST, "/shield/user/add-new-user", RoleAuthSet.ADMIN_USER));

        return authPathList;
    }

}
