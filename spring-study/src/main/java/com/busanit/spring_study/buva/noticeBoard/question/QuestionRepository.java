package com.busanit.spring_study.buva.noticeBoard.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    // 쿼리문을 직접적으로 사용하고 싶을때 사용 (QuestionService 에서 사용 위치 : 39)
    @Query(
            "select " +
                    "distinct q.id " +
                    "q.site_user_id " +
                    "q.subject " +
                    "q.content " +
                    "q.create_date " +
                    "q.modify_date " +
                    "u1.username " +
                    "from question q " +
                    "left outer join site_user u1 on q.site_user_id=u1.id " +
                    "left outer join answer a on q.id = a.question_id " +
                    "left outer join site_user u2 on a.site_user_id=u2.id " +
                    "where " +
                    "q.subject like '%:kw%' " +
                    "or q.content like '%:kw%' " +
                    "or u1.username like '%:kw%' " +
                    "or a.content like '%:kw%' " +
                    "or u2.username like '%:kw%' "
    )
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
