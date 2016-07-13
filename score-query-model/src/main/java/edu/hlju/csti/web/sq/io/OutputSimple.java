package edu.hlju.csti.web.sq.io;

import edu.hlju.csti.web.sq.enums.StatusCode;

import java.io.Serializable;

/**
 * 开发者:李嘉鼎
 * 开发时间:16/7/13
 * 描述:
 */
public class OutputSimple implements Serializable {
    private Long code;
    private String message;
    private Object data;

    public OutputSimple() {
        this.code = StatusCode.SUCCESS.getCode();
        this.message = "success";
        this.data = null;
    }

    public OutputSimple(Object data) {
        this.code = StatusCode.SUCCESS.getCode();
        this.message = "success";
        this.data = data;
    }

    public OutputSimple(Long code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public OutputSimple(Long code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
