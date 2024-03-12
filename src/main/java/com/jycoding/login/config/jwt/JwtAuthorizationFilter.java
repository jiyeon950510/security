package com.jycoding.login.config.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jycoding.login.config.auth.PrincipalDetails;
import com.jycoding.login.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 토큰이 있다면 저장하고, 없다면 통과
        String prefixJwt = request.getHeader(MyJwtProvider.HEADER);
        log.info("토큰 확인"+prefixJwt);
        // 토큰 있는지 확인
        if (prefixJwt == null) {
            chain.doFilter(request, response);
            return;
        }
        // 있으면 session에 넣고
        String jwt = prefixJwt.replace(MyJwtProvider.TOKEN_PREFIX, "");

        try {
            log.info("try 문 작동 확인");
            DecodedJWT decodedJWT = MyJwtProvider.verify(jwt);

            int id = decodedJWT.getClaim("id").asInt();
            String username = decodedJWT.getClaim("username").asString();
            String role = decodedJWT.getClaim("role").asString();

            // User user =
            // User.builder().id(id).nickname(nickname).role(role).build();
            User user = User.builder().id(id).username(username).role(role).build();

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails,
                    principalDetails.getPassword(),
                    principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("세션저장 데이터"+authentication);

        } catch (SignatureVerificationException sve) {
            // 없다면 다음 필터로 가게 -> 주소에 대한 권한 처리를 Security가 이후에 처리할 것이기 때문에 괜찮음
            // chain.doFilter(request, response);
            // return;
            log.error("토큰 검증 실패");
        } catch (TokenExpiredException tee) {
            // chain.doFilter(request, response);
            // return;
            log.error("토큰 만료됨");
        } finally {
            chain.doFilter(request, response);
        }
    }
}
