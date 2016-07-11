package edu.hlju.csti.web.sq.exception;

/**
 * 开发者:李嘉鼎
 * 开发时间:16/7/11
 * 描述:
 */
public enum StatusCode {
    NULL_ERROR(0),
    SUCCESS(200),
    REQUEST_ERROR(400),
    SERVER_ERROR(500);


    private long code;

    StatusCode(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }

    public static StatusCode getEnum(long code) {
        for (StatusCode statusCode : StatusCode.values()) {
            if (statusCode != null && code == statusCode.getCode()) {
                return statusCode;
            }
        }
        return NULL_ERROR;
    }
}
