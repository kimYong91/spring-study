package com.busanit.spring_study.buva.noticeBoard.answer;


import com.busanit.spring_study.buva.noticeBoard.question.Question;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content; // 답변

//    @Column(name = "writeDate", table = "BBS")    // 설정을 하면 테이블명을 원하는데로 정할 수 있음
    private LocalDateTime createDate;

    @ManyToOne
    private Question question;
}

