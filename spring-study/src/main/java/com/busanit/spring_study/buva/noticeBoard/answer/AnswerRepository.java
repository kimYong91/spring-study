package com.busanit.spring_study.buva.noticeBoard.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 만드는 순서 3
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
