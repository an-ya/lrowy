package com.lrowy.pojo.common.enums;

public enum SystemConstant {
    SYSTEM_SUCCESS("000", "处理成功"),
    SYSTEM_ERROR("001", "未定义错误"),
    HTTPCLIENT_ERROR("002", "HttpClient请求错误"),
    NETWORK_ERROR("003", "服务器网络异常"),
    PARAMS_ERROR("004", "请求参数有误"),
    PARAMS_INVALID("005", "请求无效"),
    MKDIR_ERROR("101", "文件夹创建失败"),
    DELETEFILE_ERROR("102", "删除文件失败"),
    JWT_ERRCODE_EXPIRE("201", "token失效"),
    JWT_ERRCODE_FAIL("202", "token验证失败");

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
