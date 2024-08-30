package com.busanit.spring_study.buva.noticeBoard.question;

import com.busanit.spring_study.buva.noticeBoard.DataNotFoundException;
import com.busanit.spring_study.buva.noticeBoard.user.SiteUser;
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

// 만드는 순서 8
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    // 모든 질문 조회
    public List<Question> getList() {
        return questionRepository.findAll();
    }

    // 페이지네이션 + 게시물 최신순 정렬
    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>(); // 게시물 최신순 정렬
        sorts.add(Sort.Order.desc("createDate"));   // 게시물 생성날짜 순으로 정렬
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }

    // id로 질문 조회
    public Question getDetail(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    // 질문 작성
    public void create(String subject, String content, SiteUser author) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setSiteUser(author);
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);
    }
    // 비회원 질문 작성
    public void create(String subject, String content) {
        create(subject, content, null);
    }

    // 수정
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public void like(Question question, SiteUser siteUser) {
        // add한 정보가 중복이 되면 자동으로 무시된다
        question.getLike().add(siteUser);
        questionRepository.save(question);
    }
}
