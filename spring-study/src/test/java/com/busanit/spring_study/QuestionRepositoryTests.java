package com.busanit.spring_study;

import com.busanit.spring_study.buva.noticeBoard.question.Question;
import com.busanit.spring_study.buva.noticeBoard.question.QuestionRepository;
import com.busanit.spring_study.buva.noticeBoard.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QuestionRepositoryTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionService questionService;

	@Test
	void contextLoads() {
//		List<Question> all = this.questionRepository.findAll();
//		assertEquals(2, all.size());	// 비교하여 맞는지 틀린지 확인. 실행시 맞으면 이상없이 실행, 틀리면 에러 표시
//
//		Question q1 = all.get(0);
//		assertEquals("sbb 보드는 뭔가요?", q1.getSubject()); // sbb 보드는 뭔가요?


//		Optional<Question> op = this.questionRepository.findById(1);
//		if(op.isPresent()) {
//			Question q2 = op.get();
//			assertEquals("sbb 보드는 뭔가요?", q2.getSubject());
//		}
	}

	@Test
	void findAll() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());	// 비교하여 맞는지 틀린지 확인. 실행시 맞으면 이상없이 실행, 틀리면 에러 표시

		Question q1 = all.get(0);
		assertEquals("sbb 보드는 뭔가요?", q1.getSubject()); // sbb 보드는 뭔가요?
	}

	@Test
	void findById() {
		Optional<Question> op = this.questionRepository.findById(1);
		if(op.isPresent()) {
			Question q2 = op.get();
			assertEquals("sbb 보드는 뭔가요?", q2.getSubject());
		}
	}

	@Test
	void findBySubject() {
		Question q1 = this.questionRepository.findBySubject("sbb 보드는 뭔가요?");
		assertEquals(1, q1.getId());
	}

	@Test
	void findBySubjectLike() {
		List<Question> qList = this.questionRepository.findBySubjectLike("%sbb%");
		assertEquals(1, qList.size());
	}

	@Test
	void findBySubjectAndContent() {
		Question qList = this.questionRepository.findBySubjectAndContent("sbb 보드는 뭔가요?", "제곳네");
		assertEquals(1, qList.getId());
	}

	@Test
	void subjectEdit() {
		Optional<Question> op = this.questionRepository.findById(2);
		if (op.isPresent()) {
			Question q1 = op.get();
			q1.setSubject("수정된 제목");
			this.questionRepository.save(q1);
			assertEquals(2, q1.getId());
		}
	}

	@Test
	void delete() {
		assertEquals(2, this.questionRepository.count());	// count : 정보의 전체 갯수 조회
		Optional<Question> op = this.questionRepository.findById(2);
		if (op.isPresent()) {
			Question q1 = op.get();
			this.questionRepository.delete(q1);
		}

		assertEquals(1, this.questionRepository.count());
	}

	// 더미 데이터 생성
	@Test
	void createTestData() {
		for(int i = 1; i <= 298; i++) {
			String subject = String.format("테스트 데이터:[%03d]", i);
			String content = "제곳네";
			questionService.create(subject,content);
		}
	}
}
