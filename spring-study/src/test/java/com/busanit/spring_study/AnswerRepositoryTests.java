package com.busanit.spring_study;

import com.busanit.spring_study.buva.noticeBoard.answer.Answer;
import com.busanit.spring_study.buva.noticeBoard.answer.AnswerRepository;
import com.busanit.spring_study.buva.noticeBoard.question.Question;
import com.busanit.spring_study.buva.noticeBoard.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AnswerRepositoryTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void contextLoads() {
		Optional<Question> op = this.questionRepository.findById(1);
		assertTrue(op.isPresent());
		Question q = op.get();
		Answer a = new Answer();
		a.setContent("ChatGPT에 물어보세요");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	// 답변 데이터로 질문 데이터 가져오기
	@Test
	void selectAnswer() {
		Optional<Answer> op = this.answerRepository.findById(1);
		assertTrue(op.isPresent());
		Answer a = op.get();
		assertEquals(1, a.getId());
		Question q = a.getQuestion();
		q.getId();
		assertEquals(1, a.getQuestion().getId());
	}

	// 질문 데이터로 답변 데이터 가져오기
	@Transactional
	@Test
	void selectQuestion() {
		Optional<Question> op = this.questionRepository.findById(1);
		assertTrue(op.isPresent());
		Question q = op.get();
		List<Answer> aList = q.getAnswerList();
		assertEquals(1, aList.size());
//		aList.get(0).setContent("이거 수정");
//		this.answerRepository.save(aList.get(0));
	}
}
