package com.vortex.cloud.manage.feign.fallback;

import com.google.common.collect.Maps;
import com.vortex.cloud.manage.feign.IOauth2ServerFeignClient;
import com.vortex.cloud.vfs.data.dto.RestResultDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Oauth2ServerFallback implements FallbackFactory<IOauth2ServerFeignClient> {
    @Override
    public IOauth2ServerFeignClient create(Throwable throwable) {
        return new IOauth2ServerFeignClient() {
            @Override
            public Map<String, String> getAccessToken(String clientInfo, Map<String, String> params) {
                return Maps.newHashMap();
            }

            @Override
            public RestResultDto getUserByToken(String accessToken) {
                return RestResultDto.newFalid("根据token获取用户信息失败", throwable.getMessage());
            }

            @Override
            public RestResultDto checkToken(String accessToken) {
                return RestResultDto.newFalid("核查token失败", throwable.getMessage());
            }

            @Override
            public RestResultDto logout(String accessToken) {
                return RestResultDto.newFalid("登出失败", throwable.getMessage());
            }

            @Override
            public RestResultDto removeAllTokenByUsername(String accessToken, String username) {
                return RestResultDto.newFalid("销毁pc端和app端token失败", throwable.getMessage());
            }
        };
    }
}
