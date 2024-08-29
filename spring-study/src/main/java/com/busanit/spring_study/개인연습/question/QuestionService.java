package com.busanit.spring_study.개인연습.question;

import com.busanit.spring_study.개인연습.DataNotFoundException;
import com.busanit.spring_study.개인연습.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public List<Question> getList() {
        return questionRepository.findAll();
    }

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }

    public Question getDetail(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, User user) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setUser(user);
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    public void create(String subject, String content) {
        create(subject, content, null);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }
}
