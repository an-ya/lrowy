package com.lrowy.pojo.common.enums;

public enum SystemConstant {
    SYSTEM_SUCCESS("000", "处理成功"),
    SYSTEM_ERROR("001", "未定义错误"),
    HTTPCLIENT_ERROR("002", "HttpClient请求错误"),
    NETWORK_ERROR("003", "服务器网络异常"),
    PARAMS_ERROR("004", "请求参数有误"),
    PARAMS_INVALID("005", "请求无效"),
    USER_EMAIL_NOT_FIND("101", "未找到该邮箱注册用户"),
    USER_PASSWORD_ERROR("102", "密码错误"),
    USER_NOT_LOGIN("103", "用户未登录"),
    JWT_EXPIRE("201", "token失效"),
    JWT_FAIL("202", "token验证失败"),
    CAPTCHA_EMAIL_DAY_LIMIT("301", "邮箱日发送验证码次数受限"),
    CAPTCHA_EMAIL_MINUTE_LIMIT("302", "邮箱每分钟发送验证码次数受限"),
    CAPTCHA_IP_DAY_LIMIT("303", "IP日发送验证码次数受限"),
    CAPTCHA_ERROR("304", "验证码错误"),
    CAPTCHA_EXPIRE("305", "验证码失效"),
    CAPTCHA_NOT_FIND("306", "未发送验证码"),
    RECAPTCHA_AUTHENTICATION_FAIL("307", "reCAPTCHA人机验证失败"),
    EMAIL_SEND_ERROR("401", "邮件发送出错"),
    EMAIL_FORMAT_ERROR("402", "邮箱格式错误"),
    EMAIL_REGISTERED("403", "邮箱已注册"),
    ALGORITHM_ERROR("501", "加密出错"),
    MKDIR_ERROR("601", "文件夹创建失败"),
    DELETE_FILE_ERROR("602", "删除文件失败"),
    IMAGE_TYPE_ERROR("603", "图片类型不支持"),
    IO_ERROR("604", "IO出错");


    /** 枚举编号 */
    private String code;

    /** 枚举详情 */
    private String description;

    /**
     * 构造方法
     *
     * @param code         枚举编号
     * @param description  枚举详情
     */
    private SystemConstant(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
