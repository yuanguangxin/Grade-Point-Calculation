package edu.hlju.csti.web.sq.controller;

import edu.hlju.csti.web.sq.io.LoginInput;
import edu.hlju.csti.web.sq.io.OutputSimple;
import edu.hlju.csti.web.sq.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;

/**
 * 开发者:李嘉鼎
 * 开发时间:16/7/13
 * 描述:
 */
@Controller
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/getCaptcha")
    @ResponseBody
    public OutputSimple getCaptcha() {
        return loginService.getCaptcha();
    }

    @RequestMapping("/login-dev")
    public ModelAndView getLoginDev() {
        return new ModelAndView("login-test");
    }

    @RequestMapping("/getPublicKey")
    @ResponseBody
    public OutputSimple getPublicKey(String schoolNum) throws UnsupportedEncodingException {
        return loginService.requestPublicKey(schoolNum);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public OutputSimple login(LoginInput input) {
        return loginService.executeLogin(input);
    }
}
