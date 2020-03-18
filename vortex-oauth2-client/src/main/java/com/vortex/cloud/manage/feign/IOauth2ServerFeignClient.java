package com.vortex.cloud.manage.feign;

import com.vortex.cloud.manage.feign.fallback.Oauth2ServerFallback;
import com.vortex.cloud.vfs.data.dto.RestResultDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "vortex-oauth2-server", fallbackFactory = Oauth2ServerFallback.class)
public interface IOauth2ServerFeignClient {
    @GetMapping("user/findByUsername")
    public String test(@RequestParam("username") String username);
    //获取token
    @PostMapping("oauth/token")
    Map<String, String> getAccessToken(@RequestHeader("Authorization") String clientInfo, @RequestParam("params") Map<String, String> params);

    //通过token获取用户信息
    @RequestMapping("oauth/user")
    RestResultDto getUserByToken(@RequestHeader("Authorization") String accessToken);

    //核查token
    @RequestMapping("oauth/check_token")
    RestResultDto checkToken(@RequestHeader("Authorization") String accessToken);

    //登出
    @RequestMapping("oauth/logout")
    RestResultDto logout(@RequestHeader("Authorization") String accessToken);

    //通过用户名注销所有可用的token；例如，用户修改密码注销pc端和app端的token
    @RequestMapping(name = "oauth/removeAllTokenByUsername", method = RequestMethod.POST)
    RestResultDto removeAllTokenByUsername(@RequestHeader("Authorization") String accessToken, @RequestParam("username") String username);


}
