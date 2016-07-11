package edu.hlju.csti.web.sq.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;

/**
 * 开发者:李嘉鼎
 * 开发时间:16/7/11
 * 描述:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RequestService {
    @Value("#{config['heida.captcha.url']}")
    private String captchaUrl;
    @Value("#{config['heida.login.url']}")
    private String loginUrl;
    @Value("#{config['heida.rate.view.url']}")
    private String rateReviewUrl;

    public void copyCaptcha(OutputStream outputStream) {

    }
}
