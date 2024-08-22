package com.busanit.spring_study.buva.noticeBoard.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionController {

//    @Autowired
//    QuestionRepository questionRepository;

    @GetMapping("/question/list")
    public String list() {

        return "question_list";
    }
}
