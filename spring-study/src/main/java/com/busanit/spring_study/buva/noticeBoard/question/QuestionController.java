package com.busanit.spring_study.buva.noticeBoard.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/list")
    public String list(Model model) {
        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList", questionList);
        model.addAttribute("hello_template", "Hello Template");
        return "question_list"; // resources/templates 경로 안에 question_list 파일을 만들면 알아서 읽음
    }

    @GetMapping(value = "question/detail/{id}") // value = {} : {}의 요소가 동적으로 바뀐다는 의미
    public String detail(Model model, @PathVariable("id") Integer id) { // "question/detail/{id}"이곳에 있는 id를  @PathVariable("id") 이곳에서 받아서 Integer id 이곳에 준다.
        Question question = questionService.getDetail(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
