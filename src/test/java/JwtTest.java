import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class JwtTest {
    @Test
    public void createdJwt_test() {
        // given

        // when
        String jwt = JWT.create()
                .withSubject("jwtStudy")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 현재시간 + 일주일 (초, 분, 시,
                // 일, 일주일)
                .withClaim("username", "ssar")
                .withClaim("password", "1234")
                // (대칭키, 공개키 어떤걸 사용할지 생각해보기)
                .sign(Algorithm.HMAC512("jwtStudy")); // 절대 노출되면 안됨
        System.out.println(jwt);
        // when
        // then
    }
}
