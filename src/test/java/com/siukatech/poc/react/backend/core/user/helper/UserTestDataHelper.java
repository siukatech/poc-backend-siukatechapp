package com.siukatech.poc.react.backend.core.user.helper;

import com.siukatech.poc.react.backend.core.business.dto.MyKeyDto;
import com.siukatech.poc.react.backend.core.business.dto.UserDto;
import com.siukatech.poc.react.backend.core.business.dto.UserPermissionDto;
import com.siukatech.poc.react.backend.core.business.dto.UserViewDto;
import com.siukatech.poc.react.backend.core.global.helper.AbstractTestDataHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserTestDataHelper extends AbstractTestDataHelper {

    public UserDto prepareUserDto_basic() {
        UserDto userDto = new UserDto();
        userDto.setUserId("app-user-01");
        userDto.setName("App-User-01");
        userDto.setPublicKey("public-key");
//        userDto.setPrivateKey("private-key");
        return userDto;
    }

    public UserViewDto prepareUserViewDto_basic() {
        UserViewDto userViewDto = new UserViewDto();
        userViewDto.setUserId("app-user-01");
        userViewDto.setName("App-User-01");
        userViewDto.setPublicKey("public-key");
//        userDto.setPrivateKey("private-key");
        return userViewDto;
    }

    public MyKeyDto prepareMyKeyDto_basic() {
        MyKeyDto myKeyDto = new MyKeyDto();
        myKeyDto.setUserId("app-user-01");
        myKeyDto.setPublicKey("public-key");
        myKeyDto.setPrivateKey("private-key");
        return myKeyDto;
    }


    public List<UserPermissionDto> prepareUserPermissions_basic() {
        String[][] userPermissionTempsArr = new String[][]{
                new String[]{"app-user-01", "1", "role-users-01", "frontend-app", "menu.home", "view"}
                , new String[]{"app-user-01", "1", "role-users-01", "frontend-app", "menu.items", "*"}
//                , new String[]{"app-user-01", "1", "role-users-01", "frontend-app", "menu.shops", "view"}
                , new String[]{"app-user-01", "1", "role-users-01", "frontend-app", "menu.merchants", "view"}
        };
        List<UserPermissionDto> userPermissionDtoList = new ArrayList<>();
        for (String[] userPermissionTemps : userPermissionTempsArr) {
            userPermissionDtoList.add(new UserPermissionDto());
            userPermissionDtoList.get(userPermissionDtoList.size() - 1).setUserId(userPermissionTemps[0]);
            userPermissionDtoList.get(userPermissionDtoList.size() - 1).setUserId(userPermissionTemps[1]);
            userPermissionDtoList.get(userPermissionDtoList.size() - 1).setUserRoleId(userPermissionTemps[2]);
            userPermissionDtoList.get(userPermissionDtoList.size() - 1).setApplicationId(userPermissionTemps[3]);
            userPermissionDtoList.get(userPermissionDtoList.size() - 1).setAppResourceId(userPermissionTemps[4]);
            userPermissionDtoList.get(userPermissionDtoList.size() - 1).setAccessRight(userPermissionTemps[5]);
        }
        return userPermissionDtoList;
    }

}
