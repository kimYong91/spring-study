package com.busanit.spring_study.개인연습.answer;


import com.busanit.spring_study.개인연습.question.Question;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// 만드는 순서 1
// 데이터 베이스의 컬럼들을 만드는 클래스
@Entity
@Data
public class Answer {
    // 프라이머리 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // 외래 키
    @ManyToOne // 많은 답변(Answer)이 있을 수 있음 하나의 질문(question)에
    private Question question;
}
