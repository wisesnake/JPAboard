package org.zerock.b01.domain;

import lombok.*;
import javax.persistence.*;


@Entity
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    //(bno,title,writer,content....등)
    //Board객체 타입의 참조를 board변수를 통해 참조하는데, 다대일 관계로 구성되었음.

    private String replyText;

    private String replyer;

    public void changeText(String text){
        this.replyText = text;
    }
    //댓글 수정은 내용만 되어야 하므로 setter개념의 메소드 하나 생성해두기.

}


