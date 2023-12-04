package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.security.PublicKey;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class BoardImage implements Comparable<BoardImage>{
    @Id
    private String uuid;

    private String fileName;

    //순번
    private int ord;

    @ManyToOne
    //@나To참조객체
    //헷갈리지 말자!
    private Board board;

    @Override
    public int compareTo(BoardImage other){
        return this.ord - other.ord;
    }

    public void changeBoard(Board board){
        this.board = board;
    }
}
