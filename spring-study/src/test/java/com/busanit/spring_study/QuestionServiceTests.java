package com.busanit.spring_study;

import com.busanit.spring_study.buva.noticeBoard.question.Question;
import com.busanit.spring_study.buva.noticeBoard.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
public class QuestionServiceTests {
    @Autowired
    QuestionService questionService;

    @Test
    void pageGetList() {
//        Page<Question> page = questionService.getList(2);
//        System.out.println("page size : " + page.getSize());
    }
}
