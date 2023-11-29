package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long rno;

    @NotNull
    private Long bno; // 계층구조를 위한 foreign key 역할을 할 BoardDTO의 bno 를 여기도 선언.
    @NotEmpty
    private String replyText;
    @NotEmpty
    private String replyer;

    private LocalDateTime regDate, modDate;

}
