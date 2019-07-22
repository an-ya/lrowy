package com.lrowy.pojo.common.http;

public class HttpResult {
    private int code;
    private String body;
    private String charset;

    public HttpResult(int code, String body, String charset) {
        this.code = code;
        this.body = body;
        this.charset = charset;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
