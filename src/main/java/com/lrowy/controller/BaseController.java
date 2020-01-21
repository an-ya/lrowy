package com.lrowy.controller;

import com.lrowy.pojo.user.User;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Controller
public class BaseController {
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    private HttpSession getSession() {
        return getRequest().getSession();
    }

    protected User getUser() {
        return (User)getSession().getAttribute("login");
    }

    protected boolean isLogin() {
        return getSession().getAttribute("login") != null;
    }

    protected void userLogin(User user) {
        getSession().setAttribute("login", user);
    }

    protected void userLogout() {
        getSession().removeAttribute("login");
    }

    protected String getCaptcha () {
        String sources = "0123456789";
        Random rand = new Random();
        StringBuilder flag = new StringBuilder();
        for (int j = 0; j < 6; j++)
        {
            flag.append(sources.charAt(rand.nextInt(9)));
        }
        return flag.toString();
    }
}
