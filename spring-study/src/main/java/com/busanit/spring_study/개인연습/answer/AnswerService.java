package com.busanit.spring_study.개인연습.answer;

import com.busanit.spring_study.개인연습.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// 만드는 순서 5
@Service
public class AnswerService {

    @Autowired  // new 생선자를 대신 만들어줌
    private AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answerRepository.save(answer);
    }
}
