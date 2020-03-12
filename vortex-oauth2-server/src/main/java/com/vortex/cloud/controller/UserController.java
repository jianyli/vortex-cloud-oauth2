package com.vortex.cloud.controller;

import com.vortex.cloud.domain.UserInfo;
import com.vortex.cloud.service.IUserService;
import com.vortex.cloud.vfs.data.dto.RestResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("findByUsername")
    public UserInfo findByUsername(@RequestParam String username) {
        return userService.findUserByUsername(username);
    }
    @RequestMapping("save")
    public RestResultDto<Boolean> save(@RequestParam(name = "username") String username,
                                       @RequestParam(name = "password") String password) {
        userService.save(username, password);
        return RestResultDto.newSuccess();
    }
}
