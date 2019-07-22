package com.lrowy.pojo.common.enums;

public enum SystemConstant {
    SYSTEM_SUCCESS("000", "处理成功"),
    SYSTEM_ERROR("001", "系统异常"),
    DATA_INTEGRITY_VIOLATION_ERROR("002", "数据幂等异常"),
    BUSINESS_STORE_COMMAND_ERROR("003", "数据库存储失败"),
    DUPLICATE_INVOKE_ERROR("004", "重复调用"),
    SLA_ACCESS_DENIED("005", "请求被限流，拒绝访问"),
    NETWORK_ERROR("101", "网络异常"),
    JWT_SECERT("@#$%^&*()OPG#$%^&*(HG", "token秘钥"),
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
