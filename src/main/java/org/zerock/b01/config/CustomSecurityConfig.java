package org.zerock.b01.config;

import com.querydsl.core.annotations.Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
@RequiredArgsConstructor
//컨트롤러의 특정 메소드 호출하는 요청 시 권한설정을 할 수 있는데, 이러한 권한설정을
//밑의 어노테이션과, prePostEnbabled = true 속성 설정을 통해,
//원하는 곳에 @PreAuthorize 혹은 @PostAuthorize 어노테이션을 이용해 사전 혹은 사후의 권한을 체크 할 수 있음.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        //여러 PasswordEncoder 타입 클래스 중 제일 무난한놈
        //비밀번호를 해시 알고리즘으로 암호화를 하는데, 매번 해시 처리된 결과가 다르므로 비번 암호화에 많이 쓰임.
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("---------------------configure-------------------");

//        //로그인 화면에서 로그인을 진행한다는 설정
//        http.formLogin();

        // 위의 주석처리한 코드는 Spring Security 에서 디폴트로 제공하는 로그인 페이지를 쓰나,
        // 별도로 디자인한 로그인 페이지를 사용하기 위해 아래와 같이 코딩 가능.
        http.formLogin().loginPage("/member/login");

        //csrf 끄기.
        http.csrf().disable();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("---------------web configure--------------");
        //정적 자원들에 대해서는 스프링 시큐리티 필터를 적용 할 필요가 없으므로 빼주는 설정.
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
