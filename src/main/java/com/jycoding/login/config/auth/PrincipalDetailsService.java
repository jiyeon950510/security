package com.jycoding.login.config.auth;

import com.jycoding.login.model.User;
import com.jycoding.login.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// "/login" 요청이 오면 자동으로 UserDetailsService 타입을 IoC 되어 있는 loadUserByUsername 함수가 실행
@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session 내부(Authentication 내부 (UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);

        //User(id=1, username=ssar, password=$2a$10$pszt9sAg3yXLmy1XxZEjkeXOwJY0hMqhifao3hgwnqZ9XVb/7p.cm, role=ROLE_ADMIN, email=쌀, createdAt=2024-03-08 15:28:20.104208)
        log.info(userEntity.toString());
        if (userEntity != null){
            return  new PrincipalDetails(userEntity);
        }
        return null;
    }
}
