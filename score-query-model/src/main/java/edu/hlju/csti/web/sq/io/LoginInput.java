package edu.hlju.csti.web.sq.io;

/**
 * 开发者:李嘉鼎
 * 开发时间:16/7/13
 * 描述:
 */
public class LoginInput {
    private String schoolNum;
    private String password;
    private String captcha;

    public String getSchoolNum() {
        return schoolNum;
    }

    public void setSchoolNum(String schoolNum) {
        this.schoolNum = schoolNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        return "LoginInput{" +
                "schoolNum='" + schoolNum + '\'' +
                ", password='" + password + '\'' +
                ", captcha='" + captcha + '\'' +
                '}';
    }
}
