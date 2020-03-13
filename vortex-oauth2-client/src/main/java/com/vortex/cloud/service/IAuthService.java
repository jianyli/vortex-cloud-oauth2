package com.vortex.cloud.service;

import com.vortex.cloud.vfs.data.dto.RestResultDto;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface IAuthService {
    RestResultDto getAccessToken(Map<String, String> params);
    RestResultDto getAccessToken(Map<String, String> params, HttpServletResponse response);
    RestResultDto checkToken(String accessToken);
    RestResultDto logout(String accessToken, HttpServletResponse response);
    RestResultDto getUser(String accessToken);
}
