package org.zerock.b01.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Member;
import org.zerock.b01.repository.MemberRepository;
import org.zerock.b01.security.dto.MemberSecurityDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
//    private PasswordEncoder passwordEncoder;

//    public CustomUserDetailsService(){
//        //생성자방식으로 Bean 주입.
//        this.passwordEncoder = new BCryptPasswordEncoder();
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //userDetails : 사용자 인증과 관련된 정보들을 저장하는 역할.
        //스프링 시큐리티에서는 해당 타입의 객체를 이용해서 패스워드를 검사하고, 사용자 권한을 확인하는 방식으로 동작함.
        log.info("loadUserByUsername : " + username);


        Optional<Member> result = memberRepository.getWithRoles(username);

        if(result.isEmpty()){//회원DB 에 username mid 가 없을경우
            throw new UsernameNotFoundException("username not found.....");
        }

        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                member.getMid(),
                member.getMpw(),
                member.getEmail(),
                member.isDel(),
                false,
                member.getRoleSet()
                        .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name()))
                        .collect(Collectors.toList())
        );
        log.info("memberSecurityDTO");
        log.info(memberSecurityDTO);

        return memberSecurityDTO;

//        for (UserDetails user : users) {
//            if (user.getUsername().equals(username)) {
//                return user;
//            }
//        }
//        List<UserDetails> users = new ArrayList<>();

//        UserDetails userDetails = User.builder()
//                .username("user1")
//                .password(passwordEncoder.encode("1111")) // 패스워드 인코딩.
//                .authorities("ROLE_USER")
//                .build();
//
//        users.add(userDetails);
//
//        UserDetails user2Details = User.builder()
//                .username("user2")
//                .password(passwordEncoder.encode("1111")) // 패스워드 인코딩.
//                .authorities("ROLE_USER")
//                .build();
//        users.add(user2Details);

//        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
