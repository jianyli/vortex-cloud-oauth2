package com.vortex.cloud.config;

import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

public class MyAuthenticationKeyGenerator extends DefaultAuthenticationKeyGenerator {
    private static final String SCOPE = "scope";
    private static final String USERNAME = "username";
    private static final String PLATFORM = "platform";

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        Map<String,String> values = new LinkedHashMap<>();
        OAuth2Request oAuth2Request = authentication.getOAuth2Request();
        if (!authentication.isClientOnly()) {
            values.put(USERNAME, authentication.getName());
        }
        values.put("client_id", oAuth2Request.getClientId());
        if (oAuth2Request.getScope() != null) {
            values.put(SCOPE, OAuth2Utils.formatParameterList(new TreeSet<String>(oAuth2Request.getScope())));
        }
        if (oAuth2Request.getRequestParameters() != null) {
            String platform = oAuth2Request.getRequestParameters().get("additional_information");
            if ("app".equals(platform) || "pc".equals(platform)) {
                values.put(PLATFORM, platform);
            }
        }
        System.out.println(generateKey(values));
        return generateKey(values);
    }
}
