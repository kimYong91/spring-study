package com.busanit.spring_study.buva.noticeBoard.question;

import com.busanit.spring_study.buva.noticeBoard.answer.Answer;
import com.busanit.spring_study.buva.noticeBoard.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

// 만드는 순서 2
@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)   // 한글이 차지하는 크기 = 200자, 용량은 600바이트
    private String subject;

    @Column(columnDefinition = "TEXT")  // 긴 텍스트, 설명, 글 등과 같은 데이터를 저장
//    @Column(columnDefinition = "BLOB")    // 이미지, 파일, 비디오 등과 같은 바이너리 데이터를 저장
    private String content;

    private LocalDateTime createDate;

    // 외래 키
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    // CascadeType.REMOVE 질문을 지우면 답변 리스트도 같이 지워짐, 사용하는데 주의 요함 회사에서는 댓글을 몇년동안 보관함
    private List<Answer> answerList;

    // 사용자 한명이 여러 질문을 할 수 있다.
    // 여러 질문을 할 수 있다. 사용자 한 명이
    @ManyToOne
    private SiteUser siteUser;

    private LocalDateTime ModifyDate;

    // 여러 사람이 여러 질문에 여러 좋아요를 할 수 있다.
    // 한 사람이 여러 좋아요를 할 수 있다.
    // 한 질문은 여러 좋아요를 받을 수 있다.
    @ManyToMany
    private Set<SiteUser> Like;
}
