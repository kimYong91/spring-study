package com.busanit.spring_study.개인연습.user;

import com.busanit.spring_study.개인연습.answer.Answer;
import com.busanit.spring_study.개인연습.question.Question;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Question> questionList;

    @OneToMany(mappedBy = "user")
    private List<Answer> answerList;
}
