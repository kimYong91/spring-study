package com.busanit.spring_study.buva.noticeBoard.question;

import com.busanit.spring_study.buva.noticeBoard.answer.AnswerForm;
import com.busanit.spring_study.buva.noticeBoard.user.SiteUser;
import com.busanit.spring_study.buva.noticeBoard.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

// 만드는 순서 17
@Slf4j // log 를 사용할 수 있게 만드는 어노테이션
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @GetMapping("/question/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> questionList = questionService.getList(page);
        model.addAttribute("questionList", questionList);
        log.debug(model.toString());
        log.debug("dkdkdkdk");
        log.info("dddd");
        log.warn("dddd");
        log.error("eeeeee");
        return "question_list"; // resources/templates 경로 안에 question_list 파일을 만들면 알아서 읽음
    }

    @GetMapping(value = "question/detail/{id}") // value = {} : {}의 요소가 동적으로 바뀐다는 의미
    public String detail(Model model, @PathVariable("id") Integer id, // "question/detail/{id}"이곳에 있는 id를  @PathVariable("id") 이곳에서 받아서 Integer id 이곳에 준다.
                         AnswerForm answerForm) {
        Question question = questionService.getDetail(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()") // 로그인 되었을때만 동작한다.
    @GetMapping("/question/create")   // QuestionForm questionForm : 안쓰더라도 꼭 입력을 해야 에러가 안남
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()") // 로그인 되었을때만 동작한다.
    @PostMapping("/question/create")                                                            // Principal : 로그인 된 유저 네임을 가져올수 있음, 스프링에서 만듬
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "question_form";
        }
        String url = "redirect:/question/list";
        SiteUser siteUser = userService.getUser(principal.getName());
        log.debug(principal.getName());
        questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return url;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/modify/{id}")
    public String questionModify(QuestionForm questionForm,
                                 @PathVariable("id") Integer id,
                                 Principal principal) {
        Question question = questionService.getDetail(id);
        if (!question.getSiteUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/modify/{id}")
    public String questionModify(QuestionForm questionForm,
                                 BindingResult bindingResult,
                                 @PathVariable("id") Integer id,
                                 Principal principal) {
        if(bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = questionService.getDetail(id);
        if (!question.getSiteUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        String url = "redirect:/question/list";
        return url;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/delete/{id}")
    public String questionDelete(@PathVariable("id") Integer id,
                                 Principal principal) {
        Question question = questionService.getDetail(id);
        if (!question.getSiteUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionService.delete(question);
        return "redirect:/";
    }
}
