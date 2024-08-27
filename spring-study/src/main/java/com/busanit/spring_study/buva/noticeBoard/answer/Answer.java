package com.busanit.spring_study.buva.noticeBoard.answer;


import com.busanit.spring_study.buva.noticeBoard.question.Question;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// 만드는 순서 1
@Data
@Entity
//@Table(name = "tbl_answer")   // 테이블 명을 클래스명과 다르게 설정하고 싶을 때 사용
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(columnDefinition = "TEXT", name = "ch_content") // name = 컬럼명 설정 가능, 없으면 필드명으로 됨
    @Column(columnDefinition = "TEXT")
    private String content; // 답변

//    @Column(name = "writeDate", table = "BBS")    // 설정을 하면 테이블명을 원하는데로 정할 수 있음
    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    // 커스텀 생성자를 사용할 시 기본 생성자는 필수로 만들어야함
    public Answer(){}
    public Answer(String table_name){}

}

