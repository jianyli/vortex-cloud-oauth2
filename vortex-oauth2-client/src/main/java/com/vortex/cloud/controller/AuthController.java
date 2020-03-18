package com.vortex.cloud.controller;

import com.google.common.collect.Maps;
import com.vortex.cloud.manage.feign.IOauth2ServerFeignClient;
import com.vortex.cloud.service.IAuthService;
import com.vortex.cloud.support.enums.LoginTypeEnum;
import com.vortex.cloud.support.util.CheckAgentUtil;
import com.vortex.cloud.vfs.common.exception.ServiceException;
import com.vortex.cloud.vfs.data.dto.RestResultDto;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private IOauth2ServerFeignClient feignClient;

    //test
    @RequestMapping("test")
    public String test(@RequestParam("username") String username) {
        return feignClient.test(username);
    }

    //伏泰用户用户名密码登录
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public RestResultDto login(@RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password,
                               HttpServletRequest request) {
        if (StringUtils.isAnyBlank(username, password)) {
            throw new ServiceException("参数不能为空");
        }
        Map<String, String> params = Maps.newHashMap();
        params.put("grant_type", "password");
        params.put("loginType", LoginTypeEnum.VORTEX_USER.getKey());
        params.put("username", username);
        params.put("password", password);

        //判断请求来源
        String ua = request.getHeader("User-Agent");
        if (CheckAgentUtil.checkAgentIsMobile(ua)) {
            params.put("additional_information", "app");
        } else {
            params.put("additional_information", "pc");
        }

        return RestResultDto.newSuccess(authService.getAccessToken(params));
    }

    //伏泰用户刷新token
    @RequestMapping(value = "refreshToken", method = {RequestMethod.GET, RequestMethod.POST})
    public RestResultDto refreshToken(@RequestParam(value = "refreshToken") String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            throw new ServiceException("参数不能为空");
        }
        Map<String, String> params = Maps.newHashMap();
        params.put("grant_type", "refresh_token");
        params.put("loginType", LoginTypeEnum.VORTEX_USER.getKey());
        params.put("refresh_token", refreshToken);
        return RestResultDto.newSuccess(authService.getAccessToken(params));
    }

    //appId，appSecret获取token
    @RequestMapping(value = "/getTokenFromThirdPartyApp", method = {RequestMethod.GET, RequestMethod.POST})
    public RestResultDto<?> getTokenFromThirdPartyApp(@RequestParam("appKey") String appKey, @RequestParam("appSecret") String appSecret) {
        System.out.println("第三方应用授权开始：");
        if (StringUtils.isAnyBlank(appKey, appSecret)) {
            throw new ServiceException("参数不能为空");
        }
        Map<String, String> params = Maps.newHashMap();
        params.put("username", appKey);
        params.put("password", appSecret);
        params.put("grant_type", "client_credentials");
        params.put("loginType", LoginTypeEnum.THIRD_PARTY_APP.getKey());
        return authService.getAccessToken(params);
    }

    //登出
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public RestResultDto logout(@RequestParam("accessToken") String accessToken, HttpServletResponse response) {
        if (StringUtils.isBlank(accessToken)) {
            throw new ServiceException("参数不能为空");
        }
        authService.logout(accessToken, response);
        return RestResultDto.newSuccess();
    }

}
