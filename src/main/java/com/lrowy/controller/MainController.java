package com.lrowy.controller;

import com.lrowy.dao.CaptchaDao;
import com.lrowy.dao.UserDao;
import com.lrowy.pojo.captcha.Captcha;
import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.pojo.user.User;
import com.lrowy.pojo.common.response.BaseResponse;

import com.lrowy.service.CaptchaService;
import com.lrowy.service.OAuthService;
import com.lrowy.utils.IpUtil;
import com.lrowy.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MainController extends BaseController {
    @Value("${google.reCaptcha.id}")
    private String reCaptchaId;


    @Autowired
    private UserDao userDao;
    @Autowired
    private CaptchaDao captchaDao;
    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private CaptchaService captchaService;

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
        model.addAttribute("reCaptchaId", reCaptchaId);
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<User> login(String email, String password) {
        BaseResponse<User> br = new BaseResponse<>();

        String regex = "^[A-Za-z0-9]+([-_.][A-Za-z0-9]+)*@([A-Za-z0-9]+[-.])+[A-Za-z0-9]{2,5}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            br.setInfo(SystemConstant.EMAIL_FORMAT_ERROR);
            return br;
        }

        User user = userDao.findUserByEmail(email);
        if (user == null) {
            br.setInfo(SystemConstant.USER_EMAIL_NOT_FIND);
            br.setMsg(br.getMsg() + "：" + email);
        } else {
            try {
                if (user.getPassword().equals(MD5Util.encrypt(password))) {
                    userLogin(user);
                } else {
                    br.setInfo(SystemConstant.USER_PASSWORD_ERROR);
                }
            } catch (NoSuchAlgorithmException e) {
                br.setInfo(SystemConstant.ALGORITHM_ERROR);
                e.printStackTrace();
            }
        }

        return br;
    }

    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> logon(String email, String password, String code, String name, MultipartFile avatar) {
        BaseResponse<String> br = new BaseResponse<>();

        String regex = "^[A-Za-z0-9]+([-_.][A-Za-z0-9]+)*@([A-Za-z0-9]+[-.])+[A-Za-z0-9]{2,5}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            br.setInfo(SystemConstant.EMAIL_FORMAT_ERROR);
            return br;
        }

        User user = userDao.findUserByEmail(email);
        if (user != null) {
            br.setInfo(SystemConstant.EMAIL_REGISTERED);
            br.setMsg(br.getMsg() + "：" + email + "，请登录");
            return br;
        }

        Captcha captcha = captchaDao.findCaptchaByTarget(email);
        if (captcha == null) {
            br.setInfo(SystemConstant.CAPTCHA_NOT_FIND);
        } else {
            if (captcha.isExpired()) {
                br.setInfo(SystemConstant.CAPTCHA_EXPIRE);
            } else {
                if (captcha.getCode().equals(code)) {
                    try {
                        String ePassword = MD5Util.encrypt(password);
                        user = new User();
                        user.setType("User");
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(ePassword);
                        user.setOrigin("Own");
                        userDao.saveUser(user);
                        userLogin(user);
                    } catch (NoSuchAlgorithmException e) {
                        br.setInfo(SystemConstant.ALGORITHM_ERROR);
                        e.printStackTrace();
                    }
                } else {
                    br.setInfo(SystemConstant.CAPTCHA_ERROR);
                }
            }
        }
        return br;
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
        String ip = IpUtil.getIpAddr(request);
        return captchaService.sendCaptcha(email, ip, token);
    }
}
