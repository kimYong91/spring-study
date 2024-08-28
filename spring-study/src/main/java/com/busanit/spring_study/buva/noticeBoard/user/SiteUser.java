package com.busanit.spring_study.buva.noticeBoard.user;

import com.busanit.spring_study.buva.noticeBoard.answer.Answer;
import com.busanit.spring_study.buva.noticeBoard.question.Question;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

// 만드는 순서 3
@Entity
@Data
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "siteUser")
    private List<Question> questionList;

    @OneToMany(mappedBy = "siteUser")
    private List<Answer> answerList;
}
