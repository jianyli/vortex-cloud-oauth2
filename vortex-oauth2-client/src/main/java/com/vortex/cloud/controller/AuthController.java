package com.vortex.cloud.controller;

import com.vortex.cloud.service.IAuthService;
import com.vortex.cloud.support.enums.LoginTypeEnum;
import com.vortex.cloud.vfs.data.dto.RestResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private IAuthService authService;

    //用户名密码登录
    @RequestMapping(name = "login", method = RequestMethod.POST)
    public RestResultDto login(@RequestBody Map<String, String> params) {
        params.put("grant_type", "password");
        params.put("loginType", LoginTypeEnum.VORTEX_USER.getKey());
        return authService.getAccessToken(params);
    }

    //刷新token
    @RequestMapping(name = "refreshToken", method = {RequestMethod.GET, RequestMethod.POST})
    public RestResultDto refreshToken(@RequestParam Map<String, String> parameters, HttpServletResponse response) {
        parameters.put("grant_type", "refresh_token");
        parameters.put("loginType", LoginTypeEnum.VORTEX_USER.getKey());
        return authService.getAccessToken(parameters, response);
    }

    //appId，appSecret获取token
    @RequestMapping(value = "/getTokenFromThirdPartyApp", method = {RequestMethod.GET, RequestMethod.POST})
    public RestResultDto<?> getTokenFromThirdPartyApp(@RequestBody Map<String, String> parameters, HttpServletResponse response) {
        String appKey = parameters.get("appKey");
        String appSecret = parameters.get("appSecret");
        parameters.remove("appKey");
        parameters.remove("appSecret");
        parameters.put("username", appKey);
        parameters.put("password", appSecret);
        parameters.put("grant_type", "password");
        parameters.put("loginType", LoginTypeEnum.THIRD_PARTY_APP.getKey());
        return authService.getAccessToken(parameters);
    }

    //登出
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public RestResultDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        //TODO 登出待完成
        return null;
    }

}
