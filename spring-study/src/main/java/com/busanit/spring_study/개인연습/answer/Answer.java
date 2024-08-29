package com.busanit.spring_study.개인연습.answer;

import com.busanit.spring_study.개인연습.question.Question;
import com.busanit.spring_study.개인연습.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private User user;
}
