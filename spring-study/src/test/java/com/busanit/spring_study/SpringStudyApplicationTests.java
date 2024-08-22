package com.busanit.spring_study;

import com.busanit.spring_study.buva.noticeBoard.question.Question;
import com.busanit.spring_study.buva.noticeBoard.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class SpringStudyApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void contextLoads() {
		Question q1 = new Question();
		q1.setSubject("sbb 보드는 뭔가요?");
		q1.setContent("제곳네");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트는 뭔가요?");
		q2.setContent("제곳네");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

}
