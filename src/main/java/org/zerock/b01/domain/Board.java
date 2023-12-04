package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false) //컬럼의 길이와 null허용여부
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    @OneToMany(mappedBy = "board") //BoardImage의 board변수
    //@ManyToOne Board board; 를 선언한 BoardImage와의 양방향 관계
    //mappedBy 는, 서로 참조를 유지하는 양방향 참조 상황에서 사용됨. 이는 '어떤 엔티티의 속성으로 매핑되는가'를 의미함.
    //게시물 관점에서는, 첨부파일은 하나의 첨부파일이 여러개의 게시물에서 사용될 수 있는 구조를 가정하고 생성함,
    //반면에, 첨부파일 관점에서는 하나의 게시물을 참조하는 구조가 필요하므로, mappedBy 를 이용해 첨부파일쪽의 해석을 적용함을 명시함.
    @Builder.Default
    private Set<BoardImage> imageSet = new HashSet<>();

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }


}
