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
        return questionRepository.findAll(spec, pageable);
//        return questionRepository.findAllByKeyword(kw, pageable);
    }

    // id로 질문 조회
    public Question getDetail(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
                // question.get() 에서 get()을 쓰는 이유 : Optional은 값이 존재할 때 get을 써야지 값을 반환한다.
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    // 질문 작성
    public void create(String subject, String content, SiteUser author) {
        // 새로운 질문을 만들어야 하기 때문에 Question q = new Question(); 를 해서 새 질문을 담을 빈 질문 객체를 만든다
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
                    // Question : 파라미터 값으로 Question을 하는 이유는 기본의 질문을 수정하는것이라서 기존에 존재하는 정보를 받아서 써야한다.
                    // 그래서 파라미터로 기존에 존재하는 질문의 정보를 가지고 있는 Question을 받아야 한다.
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

                    // Question, SiteUser : 파라미터로 Question, SiteUser 받는 이유는 기본의 질문에 있는 like를 눌러야 하는것이고,
                    // 회원이 로그인 해야 가능하기 때문에 기존에 존재하는 정보를 받아야 함
    public void like(Question question, SiteUser siteUser) {
        // like를 Set으로 만들어서 add한 정보가 중복이 되면 자동으로 무시된다
        // question.getLike().add(siteUser) : like라는 곳에 유저 정보를 저장하는 기능, 그래야지 좋아요를 눌렀을때 몇명이 눌렀는지를 카운트 할 수 있다.
        question.getLike().add(siteUser);
        questionRepository.save(question);
    }

    @SuppressWarnings("unused")
            // Specification : 복잡한 쿼리를 생성할 수 있도록 도와줌,
            // 여러 가지 검색 조건을 결합하거나, 조건에 따라 쿼리의 내용이 달라져야 할 때 유용하게 사용
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

                // LEFT OUTER JOIN site_user u1 ON q.site_user_id = u1.id
                Join<Question, SiteUser> u1 = q.join("siteUser", JoinType.LEFT); // siteUser : Question 클래스 안의 siteUser 객체 이름, 외래키 이름
                // LEFT OUTER JOIN answer a ON q.id = a.question_id
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT); // answerList : Question 클래스 안의 answerList 객체 이름, 외래키 이름
                // LEFT OUTER JOIN site_user u2 ON a.site_user_id = u2.id
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
