package com.jycoding.login.service;

import com.jycoding.login.dto.JoinReqDto;
import com.jycoding.login.dto.LoginReqDto;
import com.jycoding.login.handler.ex.CustomException;
import com.jycoding.login.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(JoinReqDto joinReqDto) {
        int result = userRepository.insert(joinReqDto);
        if (result != 1) {
            throw new CustomException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
