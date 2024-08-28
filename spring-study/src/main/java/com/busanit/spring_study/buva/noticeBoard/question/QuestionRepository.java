package com.busanit.spring_study.buva.noticeBoard.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 만드는 순서 5
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);

    // 페이지네이션
    @Override
    Page<Question> findAll(Pageable pageable);
//    Page<Question> findAllOrderByCreateDateDsc(Pageable pageable);

}
