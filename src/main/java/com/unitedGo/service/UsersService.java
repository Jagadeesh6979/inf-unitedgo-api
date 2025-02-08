package com.unitedGo.service;

import com.unitedGo.entity.Users;
import com.unitedGo.model.Token;
import com.unitedGo.model.UserInfo;

public interface UsersService {
    Users registerUser(UserInfo userInfo);
    Token loginUser(UserInfo userInfo);

}
