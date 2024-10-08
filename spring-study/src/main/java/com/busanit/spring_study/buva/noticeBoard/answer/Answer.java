package com.busanit.spring_study.buva.noticeBoard.answer;


import com.busanit.spring_study.buva.noticeBoard.question.Question;
import com.busanit.spring_study.buva.noticeBoard.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

// 만드는 순서 1
@Data
@Entity
//@Table(name = "tbl_answer")   // 테이블 명을 클래스명과 다르게 설정하고 싶을 때 사용
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // DB에 저장 될 시 Long으로 저장 되어서 타입을 Long으로 하면 되지만 Integer은 자주 사용할 수 있음(예상)

//    @Column(columnDefinition = "TEXT", name = "ch_content") // name = 컬럼명 설정 가능, 없으면 필드명으로 됨
    @Column(columnDefinition = "TEXT")
    private String content; // 답변

//    @Column(name = "writeDate", table = "BBS")    // 설정을 하면 테이블명을 원하는데로 정할 수 있음
    private LocalDateTime createDate;

    // 하나의 질문에 여러 답변을 할 수 있다.
    @ManyToOne
    private Question question;

    // 한 사람이 여러 답변을 할 수 있다.
    @ManyToOne
    private SiteUser siteUser;

    private LocalDateTime modifyDate;

    // 커스텀 생성자를 사용할 시 기본 생성자는 필수로 만들어야함
    public Answer(){}
    public Answer(String table_name){}

    // 여러 사람이 여러 답변에 여러 좋아요를 할 수 있다.
    // 한 사람이 여러 좋아요를 할 수 있다.
    // 한 답변은 여러 좋아요를 받을 수 있다.
    @ManyToMany
    private Set<SiteUser> Like;
}

