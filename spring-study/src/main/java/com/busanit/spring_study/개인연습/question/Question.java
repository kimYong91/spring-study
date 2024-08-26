package com.busanit.spring_study.개인연습.question;

import com.busanit.spring_study.개인연습.answer.Answer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

// 만드는 순서 2
// 데이터 베이스의 컬러들을 생성하는 클래스
@Data
@Entity
public class Question {

    // 프라이머리 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String subject;

    @Column
    private String content;

    private LocalDateTime createDate;

    // 외래 키
    // 하나의 질문(Question)에 여러 답변(answerList)이 있을 수 있어서
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)  // 질문을 지우면 그 질문에 해당하는 답변들이 다 지워짐
    private List<Answer> answerList;    // 리스트인 이유 : 하나의 질문에 여러 답변이 있을 수 있어서
}
