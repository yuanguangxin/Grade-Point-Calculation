package edu.hlju.csti.web.sq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 开发者:李嘉鼎
 * 开发时间:16/7/13
 * 描述:
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index", "/home"})
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
