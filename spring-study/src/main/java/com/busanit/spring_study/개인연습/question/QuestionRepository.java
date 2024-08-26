package com.busanit.spring_study.개인연습.question;

import org.springframework.data.jpa.repository.JpaRepository;

// 만드는 순서 4                                                     Integer 인 이유 : Question의 프라이커리키의 타입을 따라감
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
