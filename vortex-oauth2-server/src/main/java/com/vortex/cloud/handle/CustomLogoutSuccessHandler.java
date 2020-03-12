package com.vortex.cloud.handle;

import com.vortex.cloud.vfs.common.mapper.JsonMapper;
import com.vortex.cloud.vfs.data.dto.RestResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security logout handler
 */
@Component
public class CustomLogoutSuccessHandler
        extends AbstractAuthenticationTargetUrlRequestHandler
        implements LogoutSuccessHandler {
    private static final String BEARER_AUTHENTICATION = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "authorization";

    @Qualifier("consumerTokenServices")
    @Autowired
    private ConsumerTokenServices tokenServices;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {

        String token = request.getHeader(HEADER_AUTHORIZATION);

        if (token != null && StringUtils.startsWithIgnoreCase(token, BEARER_AUTHENTICATION)) {
            String accessToken = token.split(" ")[1];
            tokenServices.revokeToken(accessToken);
        }

        response.setStatus(HttpServletResponse.SC_OK);

        RestResultDto<?> result = RestResultDto.newSuccess("logout success");
        response.setContentType("application/json");
		JsonMapper jsonMapper=new JsonMapper();
        response.getWriter().print(jsonMapper.toJson(result));
        
    }

}
