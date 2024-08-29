package com.busanit.spring_study.buva.noticeBoard.answer;

import com.busanit.spring_study.buva.noticeBoard.DataNotFoundException;
import com.busanit.spring_study.buva.noticeBoard.question.Question;
import com.busanit.spring_study.buva.noticeBoard.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

// 만드는 순서 7
@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    // 처음 파라미터 값으로 Question question, String content 만 있는것을 만든 상황에서
    // SiteUser author 이걸 추가 해야 한다면 밑의 코드와 같이 만들어 놓으면 다른 곳에서
    // 메서드를 사용한 것들에 영향을 주지 않을 수 있다.
    public void create(Question question, String content) {
        create(question, content, null);
    }

    public void create(Question question, String content, SiteUser author){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setSiteUser(author);
        answer.setCreateDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    public Answer getDetail(Integer id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}
