package edu.hlju.csti.web.sq.exception;

import edu.hlju.csti.web.sq.enums.StatusCode;

import static edu.hlju.csti.web.sq.enums.StatusCode.UNKNOWN_ERROR;

/**
 * 开发者:李嘉鼎
 * 开发时间:16/7/11
 * 描述:
 */
public class SystemException extends Throwable {
    private String message;
    private StatusCode code;

    public SystemException(){
        super("Unknown Error");
        this.code = UNKNOWN_ERROR;
    }
    public SystemException(String message){
        super(message);
        this.message = message;
        this.code = UNKNOWN_ERROR;
    }

    public SystemException(String message,StatusCode code){
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public StatusCode getEnumCode(){
        return this.code;
    }

    public long getCodeNum(){
        return code == null?UNKNOWN_ERROR.getCode():code.getCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
