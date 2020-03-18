package com.vortex.cloud.service.impl;

import com.vortex.cloud.config.ClientConfig;
import com.vortex.cloud.manage.feign.IOauth2ServerFeignClient;
import com.vortex.cloud.service.IAuthService;
import com.vortex.cloud.vfs.common.exception.ServiceException;
import com.vortex.cloud.vfs.common.mapper.JsonMapper;
import com.vortex.cloud.vfs.data.dto.RestResultDto;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class AuthServiceImpl implements IAuthService {

    private static JsonMapper mapper = new JsonMapper();

    @Autowired
    private ClientConfig clientConfig;
    @Autowired
    private IOauth2ServerFeignClient feignClient;


    @Override
    public RestResultDto getAccessToken(Map<String, String> params) {
        Map<String, String> tokenMap = getOauth2Token(params);
        if (MapUtils.isEmpty(tokenMap)) {
            throw new ServiceException("获取token失败");
        }
        return RestResultDto.newSuccess(tokenMap);
    }

    @Override
    public RestResultDto getAccessToken(Map<String, String> params, HttpServletResponse response) {
        Map<String, String> tokenMap = getOauth2Token(params);
        if (MapUtils.isEmpty(tokenMap)) {
            throw new ServiceException("获取token失败");
        }
        response.addHeader("ACCESS-TOKEN", mapper.toJson(tokenMap));
        return RestResultDto.newSuccess();
    }

    @Override
    public RestResultDto checkToken(String accessToken) {
        if (StringUtils.isBlank(accessToken)) {
            throw new ServiceException("参数不能为空");
        }
        return feignClient.checkToken("Bearer " + accessToken);
    }

    @Override
    public RestResultDto logout(String accessToken, HttpServletResponse response) {
        if (StringUtils.isBlank(accessToken)) {
            throw new ServiceException("参数不能为空");
        }
        feignClient.logout("Bearer " + accessToken);
        response.setHeader("ACCESS-TOKEN", null);
        return RestResultDto.newSuccess();
    }

    @Override
    public RestResultDto getUser(String accessToken) {
        if (StringUtils.isBlank(accessToken)) {
            throw new ServiceException("参数不能为空");
        }
        return RestResultDto.newSuccess(feignClient.getUserByToken("Bearer " + accessToken));
    }

    public Map<String, String> getOauth2Token(Map<String, String> params) {
        String clientId = clientConfig.getClientId();
        String clientSecret = clientConfig.getClientSecret();
        if (StringUtils.isBlank(params.get("additional_information"))) {

        }
        if (StringUtils.isAnyBlank(clientId, clientSecret)) {
            throw new ServiceException("请配置client信息");
        }
        return feignClient.getAccessToken("Basic ".concat(Base64Utils.encodeToString(clientId.concat(":").concat(clientSecret).getBytes())), params);
    }
}
