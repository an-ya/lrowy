package com.lrowy.controller;

import com.lrowy.dao.CaptchaDao;
import com.lrowy.dao.UserDao;
import com.lrowy.pojo.captcha.Captcha;
import com.lrowy.pojo.captcha.ReCaptchaResponse;
import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.pojo.user.User;
import com.lrowy.pojo.common.response.BaseResponse;

import com.lrowy.service.EmailService;
import com.lrowy.service.ReCaptchaService;
import com.lrowy.service.OAuthService;
import com.lrowy.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController extends BaseController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CaptchaDao captchaDao;
    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ReCaptchaService reCaptchaService;

    @RequestMapping("/")
    public String i() {
        return "forward:/index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);
        return "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("type", "login");
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<User> login() {
        BaseResponse<User> br = new BaseResponse<>();
        User user = new User();
        user.setName("AnyaZZ");
        userLogin(user);
        br.setResult(user);
        return br;
    }

    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> logon(String email) {
        Context context = new Context();
        context.setVariable("title", "From www.Lrowy.com To" + email);
        context.setVariable("content", "nezkixooldtw");
        try {
            emailService.sendTemplateMail(email, "我的激活码", "/email", context);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new BaseResponse<>();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> logout() {
        BaseResponse<String> br = new BaseResponse<>();
        userLogout();
        return br;
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<User> init() {
        BaseResponse<User> br = new BaseResponse<>();
        User user = isLogin() ? getUser() : null;
        br.setResult(user);
        return br;
    }

    @RequestMapping(value = "/oauth", method = RequestMethod.GET)
    public String oauth(String code) {
        oAuthService.getUserInfo(code);
        return "redirect:/";
    }

    @RequestMapping(value = "/getUserByEmail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<User> getUserByEmail(String email) {
        BaseResponse<User> br = new BaseResponse<>();
        if (email != null) {
            User user = userDao.findUserByEmail(email);
            br.setResult(user);
        }
        return br;
    }

    @RequestMapping(value = "/getCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Captcha> getCaptcha(HttpServletRequest request, String token, String email) {
        BaseResponse<Captcha> br = new BaseResponse<>();
        String ip = IpUtil.getIpAddr(request);
        try {
            String content = reCaptchaService.verify(token, ip);
            ReCaptchaResponse reCaptchaResponse = reCaptchaService.deserialize(content);
            if (reCaptchaResponse.getScore() >= 0.5) {
                Captcha captcha = new Captcha();
                captcha.setIp(ip);
                captcha.setCode(getCaptcha());
                captcha.setSendId(1);
                captcha.setSendMode("Email");
                captcha.setSendTarget(email);
                captchaDao.saveCaptcha(captcha);
                br.setResult(captcha);
            } else {
                br.setInfo(SystemConstant.RECAPTCHA_AUTHENTICATION_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return br;
    }
}
