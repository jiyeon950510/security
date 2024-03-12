package com.jycoding.login.service;

import com.jycoding.login.config.auth.PrincipalDetails;
import com.jycoding.login.config.jwt.MyJwtProvider;
import com.jycoding.login.dto.JoinReqDto;
import com.jycoding.login.dto.LoginReqDto;
import com.jycoding.login.handler.ex.CustomException;
import com.jycoding.login.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public String 로그인(LoginReqDto loginReqDto){

        log.info("서비스 확인");

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginReqDto.getUsername(), loginReqDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("서비스 확인");
            return MyJwtProvider.create(principalDetails.getUser());
        }catch (Exception e){
            throw new CustomException("인증 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void 회원가입(JoinReqDto joinReqDto) {
        int result = userRepository.insert(joinReqDto);
        if (result != 1) {
            throw new CustomException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
