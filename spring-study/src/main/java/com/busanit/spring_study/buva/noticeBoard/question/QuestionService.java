package com.busanit.spring_study.buva.noticeBoard.question;

import com.busanit.spring_study.buva.noticeBoard.DataNotFoundException;
import com.busanit.spring_study.buva.noticeBoard.answer.Answer;
import com.busanit.spring_study.buva.noticeBoard.user.SiteUser;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    // 페이지네이션 + 게시물 최신순 정렬 + 검색어 받기
    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>(); // 게시물 최신순 정렬시키는 빈 리스트
        sorts.add(Sort.Order.desc("createDate"));   // 게시물 생성날짜 순으로 정렬
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
//        return questionRepository.findAll(spec, pageable);
        return questionRepository.findAllByKeyword(kw, pageable);
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

    @SuppressWarnings("unused")
    private Specification<Question> search(String kw) {
        return new Specification<Question>() {
            // serialVersionUID : 일단 그냥 해야 한다고만 생각하면 됨
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);

                /*
                DB 쿼리 :
                "select " +
                "distinct q " +
                "from Question q " +
                "left outer join SiteUser u1 on q.siteUser = u1 " +
                "left outer join Answer a on a.question = q " +
                "left outer join SiteUser u2 on a.siteUser = u2 " +
                "where " +
                "q.subject like %:kw% " +
                "or q.content like %:kw% " +
                "or u1.username like %:kw% " +
                "or a.content like %:kw% " +
                "or u2.username like %:kw% "
                동일한 역할
                 */

                // left outer join site_user s on q.site_user_id = s.id
                Join<Question, SiteUser> u1 = q.join("siteUser", JoinType.LEFT); // siteUser : Question 클래스 안의 siteUser 객체 이름, 외래키 이름
                // left outer join answer a on q.id = a.question_id
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT); // answerList : Question 클래스 안의 answerList 객체 이름, 외래키 이름
                // left outer join site_user s on a.site_user_id = s.id
                Join<Answer, SiteUser> u2 = a.join("siteUser", JoinType.LEFT); // siteUser : Answer 클래스 안의 siteUser 객체 이름, 외래키 이름
                return cb.or(
                        cb.like(q.get("subject"), "%" + kw + "%"),    // 질문 제목으로 검색
                        cb.like(q.get("content"), "%" + kw + "%"),      // 질문 내용으로 검색
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자로 검색
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용으로 검색
                        cb.like(u2.get("username"), "%" + kw + "%")     // 답변 작성자로 검색
                );
            }
        };
    }
}
