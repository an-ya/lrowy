package com.lrowy.controller;

import com.lrowy.pojo.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class BaseController {
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
}
