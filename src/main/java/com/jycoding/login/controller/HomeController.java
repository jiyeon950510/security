package com.jycoding.login.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //로그인 한 사람만 들어갈 수 있음
    @GetMapping({"/", "/home"})
    public String home() {
        return "home/home";
    }

    // 어드민 만 들어갈 수 있음
//    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String admin() {
        return "home/admin";
    }

    // 유저만 만 들어갈 수 있음
//    @Secured("ROLE_USER")
    @GetMapping("/user")
    public String user() {
        return "home/user";
    }
}