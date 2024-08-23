package com.busanit.spring_study.buva.noticeBoard.answer;

import com.busanit.spring_study.buva.noticeBoard.question.Question;
import com.busanit.spring_study.buva.noticeBoard.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AnswerController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @PostMapping(value = "/answer/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @RequestParam(value = "content") String content) {
        Question question = questionService.getDetail(id);
        answerService.create(question, content);
        return String.format("redirect:/question/detail/%s", id);
    }
}
