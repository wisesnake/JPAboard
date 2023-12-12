package org.zerock.b01.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(){
        //생성자방식으로 Bean 주입.
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //userDetails : 사용자 인증과 관련된 정보들을 저장하는 역할.
        //스프링 시큐리티에서는 해당 타입의 객체를 이용해서 패스워드를 검사하고, 사용자 권한을 확인하는 방식으로 동작함.
        log.info("loadUserByUsername : " + username);

        UserDetails userDetails = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("1111")) // 패스워드 인코딩.
                .authorities("ROLE_USER")
                .build();

        return userDetails;
    }
}
