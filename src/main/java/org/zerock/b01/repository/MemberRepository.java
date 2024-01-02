package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {

    @EntityGraph(attributePaths = "roleSet")
    //밑의 메소드가 의미하는것은, 메소드 호출 시 getWithRoles("user1234") 로 호출할 경우, :mid 부분에 String 타입 mid객체에 담겨있는 값인
    //user1234 가 대입되어 최종적으로 select m from Member m where m.mid = user1234 and m.social = false 와 같은 쿼리가 완성되어
    //user1234 유저의 정보가 돌아온다.
    @Query("select m from Member m where m.mid = :mid and m.social = false")
    Optional<Member> getWithRoles(String mid);

}
