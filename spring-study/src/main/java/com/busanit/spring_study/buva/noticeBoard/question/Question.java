package com.busanit.spring_study.buva.noticeBoard.question;

import com.busanit.spring_study.buva.noticeBoard.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    // CascadeType.REMOVE 질문을 지우면 답변 리스트도 같이 지워짐, 사용하는데 주의 요함 회사에서는 댓글을 몇년동안 보관함
    private List<Answer> answerList;
}