package com.busanit.spring_study.buva.noticeBoard.answer;

import com.busanit.spring_study.buva.noticeBoard.question.Question;
import com.busanit.spring_study.buva.noticeBoard.question.QuestionService;
import com.busanit.spring_study.buva.noticeBoard.user.SiteUser;
import com.busanit.spring_study.buva.noticeBoard.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

// 만드는 순서 10
@Controller
public class AnswerController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;

    @PreAuthorize("isAuthenticated()") // 로그인 되었을때만 동작한다.
    @PostMapping(value = "/answer/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @RequestParam(value = "content") String content,
                               Principal principal) {
        Question question = questionService.getDetail(id);

//        // 비회원도 답변을 달 수 있게 만드는 코드 비회원이기에 입력자는 나오지 않음
//        if (principal == null) {
//            answerService.create(question, content);
//        } else {
//            SiteUser siteUser = userService.getUser(principal.getName());
//            answerService.create(question, content, siteUser);
//        }

        SiteUser siteUser = userService.getUser(principal.getName());
        answerService.create(question, content, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/modify/{id}")
    public String answerModify(AnswerForm answerForm,
                                 @PathVariable("id") Integer id,
                                 Principal principal) {

        Answer answer = answerService.getDetail(id);
        if (!answer.getSiteUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        answerForm.setContent(answerForm.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/modify/{id}")
    public String answerModify(AnswerForm answerForm,
                                 BindingResult bindingResult,
                                 @PathVariable("id") Integer id,
                                 Principal principal) {
        if(bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = answerService.getDetail(id);
        if (!answer.getSiteUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        String url = String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
        answerService.modify(answer, answerForm.getContent());
        return url;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/delete/{id}")
    public String answerDelete(@PathVariable("id") Integer id,
                                 Principal principal) {
        Answer answer = answerService.getDetail(id);
        if (!answer.getSiteUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        String url = String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
        answerService.delete(answer);
        return url;
    }
}
