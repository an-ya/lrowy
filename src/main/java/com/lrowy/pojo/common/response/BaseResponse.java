package com.lrowy.pojo.common.response;

import com.lrowy.pojo.common.enums.SystemConstant;
import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 1234567890123456789L;

    //请求成功还是失败
    private boolean success = true;

    //执行结果编码，可以包含失败重试要求
    private String code = "000";

    //描述
    private String msg = "处理成功";

    //结果对象，一般是页面VO
    private T result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setInfo(SystemConstant systemConstant) {
        this.code = systemConstant.getCode();
        this.msg = systemConstant.getDescription();
    }
}
