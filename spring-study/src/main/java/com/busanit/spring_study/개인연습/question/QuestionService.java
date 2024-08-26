package com.busanit.spring_study.개인연습.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// 만드는 순서 6
@Service
public class QuestionService {

    @Autowired  // new 생성자를 대신 만들어 줌
    QuestionRepository questionRepository;

    // 질문 리스트를 읽어 옴
    public List<Question> getList() {
        return questionRepository.findAll(); // findAll : 스프링에서 만든 메서드(내가 만든것이 아님)
    }

    public void create(String subject, String content) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);  // save : 스프링에서 만든 메서드(내가 만든것이 아님)
    }
}
