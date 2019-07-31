package com.lrowy.pojo.common.http;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

public class HttpResult {
    private int code;
    private String charset;
    private HttpEntity entity;

    public HttpResult(int code, HttpEntity entity, String charset) {
        this.code = code;
        this.entity = entity;
        this.charset = charset;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public HttpEntity getEntity() {
        return entity;
    }

    public void setEntity(HttpEntity entity) {
        this.entity = entity;
    }

    public String getEntityString() throws Exception {
        return EntityUtils.toString(entity, charset);
    }

    public InputStream getEntityContent() throws IOException {
        return entity.getContent();
    }
}
