package com.busanit.spring_study.buva.noticeBoard.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

// 만드는 순서 7
@Data
public class QuestionForm {

    @NotEmpty(message = "제목은 필수 입력 항목입니다.")
    @Size(max = 200, message = "제목은 200글자 까지 입니다.")
    private String subject;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
}
