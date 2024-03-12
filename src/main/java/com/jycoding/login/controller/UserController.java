package com.jycoding.login.controller;

import com.jycoding.login.config.jwt.MyJwtProvider;
import com.jycoding.login.dto.JoinReqDto;
import com.jycoding.login.dto.LoginReqDto;
import com.jycoding.login.handler.ex.CustomException;
import com.jycoding.login.model.User;
import com.jycoding.login.model.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.jycoding.login.service.UserService;

import java.util.ArrayList;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(LoginReqDto loginReqDto) {

        log.info("컨트롤러 실행확인");

        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
//        User principal = userRepository.findByUsernameAndPassword(loginReqDto);
//
//        if (principal == null) {
//            throw new CustomException("아이디 혹은 비번이 틀렸습니다",
//                    HttpStatus.BAD_REQUEST);
//        }

        String jwt = userService.로그인(loginReqDto);
        log.info("로그인 성공"+jwt);

        HttpHeaders headers = new HttpHeaders();
        headers.add(MyJwtProvider.HEADER, jwt);
        log.info("로그인 확인"+headers);

        return ResponseEntity.ok().header(MyJwtProvider.HEADER, jwt).body("ok");
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

        joinReqDto.setRole("USER");

        String encPassword = bCryptPasswordEncoder.encode(joinReqDto.getPassword());
        joinReqDto.setPassword(encPassword);

        userService.회원가입(joinReqDto);

        return "redirect:/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

}
