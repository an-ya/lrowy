package com.lrowy.controller;

import com.lrowy.pojo.User;

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

    protected String createRandom(boolean numberFlag, int length) {
        StringBuilder retStr = new StringBuilder();
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = new StringBuilder();
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) count++;
                retStr.append(strTable.charAt(intR));
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr.toString();
    }
}
