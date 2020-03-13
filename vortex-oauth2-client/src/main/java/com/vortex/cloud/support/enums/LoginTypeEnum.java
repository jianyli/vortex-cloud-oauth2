package com.vortex.cloud.support.enums;


import org.apache.commons.lang.StringUtils;

/**
 * 登录类型
 *
 * @author XY
 */
public enum LoginTypeEnum {
    VORTEX_USER_VCODE("VORTEX_USER_VCODE", "伏泰用户携带验证码"),
    VORTEX_USER("VORTEX_USER", "伏泰用户"),
    THIRD_PARTY_APP("THIRD_PARTY_APP", "第三方app"),
    PORTAL_USER("PORTAL_USER", "门户用户"),
    THIRD_PARTY_APP_USER("THIRD_PARTY_APP_USER", "三方app借用我方user登录"),
    Dt_USER("Dt_USER", "钉钉用户"),
    VORTEX_USERNAME("VORTEX_USERNAME", "直接使用伏泰用户名登录"),
    VORTEX_USER_TOKEN("VORTEX_USER_TOKEN", "伏泰用户安全令牌");

    private final String key;
    private final String value;

    private LoginTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValueByKey(String key) {
        for (LoginTypeEnum e : LoginTypeEnum.values()) {
            if (e.getKey().equals(key)) {
                return e.getValue();
            }
        }
        return null;
    }

    public static String getKeyByValue(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (LoginTypeEnum e : LoginTypeEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e.getKey();
                }
            }
        }
        return null;
    }
}
