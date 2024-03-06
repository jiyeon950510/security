package com.jycoding.login.controller;

import com.jycoding.login.dto.JoinReqDto;
import com.jycoding.login.dto.LoginReqDto;
import com.jycoding.login.handler.ex.CustomException;
import com.jycoding.login.model.User;
import com.jycoding.login.model.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.jycoding.login.service.UserService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto) {

        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
       User principal = userRepository.findByUsernameAndPassword(loginReqDto);

        if (principal == null) {
            throw new CustomException("아이디 혹은 비번이 틀렸습니다",
                    HttpStatus.BAD_REQUEST);
        }
        session.setAttribute("principal", principal);

        return "redirect:/home";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {

        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("username 을 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password 를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (joinReqDto.getEmail() == null || joinReqDto.getEmail().isEmpty()) {
            throw new CustomException("email 를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        userService.회원가입(joinReqDto);

        return "redirect:/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

}
