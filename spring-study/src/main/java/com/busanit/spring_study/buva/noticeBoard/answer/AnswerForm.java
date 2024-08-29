package com.busanit.spring_study.buva.noticeBoard.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnswerForm {

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
}
