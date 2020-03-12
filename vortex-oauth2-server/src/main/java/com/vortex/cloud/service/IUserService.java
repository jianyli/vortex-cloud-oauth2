package com.vortex.cloud.service;

import com.vortex.cloud.domain.UserInfo;

public interface IUserService {
    UserInfo findUserByUsername(String username);
    void save(String username, String password);
}
