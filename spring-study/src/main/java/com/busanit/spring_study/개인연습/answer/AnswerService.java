package com.busanit.spring_study.개인연습.answer;

import com.busanit.spring_study.개인연습.question.Question;
import com.busanit.spring_study.개인연습.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    public void create(Question question, String content, User user) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setCreateDate(LocalDateTime.now());
        answerRepository.save(answer);
    }
}
