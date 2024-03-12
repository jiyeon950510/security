package com.jycoding.login.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jycoding.login.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyJwtProvider {

    //jwt 주제
    private static final String SUBJECT = "jwtstudy";
    //jwt 만료시간 (1시간)
    private static final int EXP = 1000 * 60 * 60;
    // 헤더 포함 접두사
    public static final String TOKEN_PREFIX = "JwtStudy "; // 스페이스 필요함
    // 헤더 이름
    public static final String HEADER = "Authorization";
    //비밀 키
    private static final String SECRET = "jiyeon";



    public static String create(User user) {
        log.info("토큰 만들 재료"+user);
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + jwt;
    }

    // jwt 유효성 검증
    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
        return decodedJWT;
    }



}
