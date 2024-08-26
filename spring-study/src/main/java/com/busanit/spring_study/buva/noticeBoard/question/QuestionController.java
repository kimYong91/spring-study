package com.busanit.spring_study.buva.noticeBoard.question;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> questionList = questionService.getList(page);
        model.addAttribute("questionList", questionList);
        return "question_list"; // resources/templates 경로 안에 question_list 파일을 만들면 알아서 읽음
    }

    @GetMapping(value = "question/detail/{id}") // value = {} : {}의 요소가 동적으로 바뀐다는 의미
    public String detail(Model model, @PathVariable("id") Integer id) { // "question/detail/{id}"이곳에 있는 id를  @PathVariable("id") 이곳에서 받아서 Integer id 이곳에 준다.
        Question question = questionService.getDetail(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/question/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

//    @PostMapping("/question/create")
//    public String questionCreate(@RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content) {
//        String url = "redirect:/question/list";
//        questionService.create(subject,content);
//        return url;
//    }

    @PostMapping("/question/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "question_form";
        }
        String url = "redirect:/question/list";
        questionService.create(questionForm.getSubject(), questionForm.getContent());
        return url;
    }
}
