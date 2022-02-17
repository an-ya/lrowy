package com.lrowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController extends BaseController {
    @RequestMapping("/admin/writer")
    public String writer() {
        return "/admin/writer";
    }
}
