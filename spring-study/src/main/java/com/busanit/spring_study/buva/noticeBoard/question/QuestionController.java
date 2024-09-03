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

    /* Model (데이터 전달용 빈 객체)
    있는 경우 : 입력받은 값 또는 기존에 존재하는 값을 화면에 출력하려면 그 정보를 전달용 빈 객체(Model)가 필요하다.
    없는 경우 : 입력받은 값을 출력할 필요가 없다면 전달용 빈 객체(Model)를 만들 필요가 없다.
    */

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @GetMapping("/question/list")
    public String list(Model model,
                       // @RequestParam : url에서 주는 정보에서 키 값을 기준으로 정보를 분류해서 가져옴
                       // 즉, html 에서 /question/list?kw=spring&page=2 이렇게 만들면 kw 이걸 키값으로 정보를 분류해서 spring 이걸 가져온다.
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        // Page 인 이유가 getList 의 return 타입이 Page 이기 때문이다.
        Page<Question> questionList = questionService.getList(page, kw);
        // Model model = new Model : model은 마치 빈 객체를 만들어 정보를 저장하고 html에 전달하는 기능이라고 보면 됨
        // 여기서 Model 이 필요한 이유는 questionList 이게 정보를 담아있기 때문에 html에 정보를 전달할 매개체가 Model이기 때문이다.
        model.addAttribute("questionList", questionList);
        model.addAttribute("kw", kw);

        // model.addAttribute("page", page); 가 없어도 되는 이유는 Page라는 타입 안에 이미 현재 페이지의 정보가 담겨 있기 때문이다.
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
    @PostMapping("/question/create")
                                // @Valid : QuestionForm 객체의 필드에 설정된 유효성 제약 조건을 검사합니다. 예를 들어, 제목이나 내용의 필수 입력, 길이 제한 등을 체크
                                                                // BindingResult : @Valid 어노테이션이 붙은 객체의 유효성 검사 결과를 담는 객체, 스프링에서 만듬
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) { // Principal : 현재 로그인한 사용자의 정보를 담고 있는 객체, 스프링에서 만듬
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
                                 Principal principal) {  // Principal : 현재 로그인한 사용자의 정보를 담고 있는 객체, 스프링에서 만듬
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
    public String questionModify(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult,
                                 @PathVariable("id") Integer id,
                                 Principal principal) {  // Principal : 현재 로그인한 사용자의 정보를 담고 있는 객체, 스프링에서 만듬
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/like/{id}")
    public String questionLike(@PathVariable("id") Integer id,
                                 Principal principal) {
        Question question = questionService.getDetail(id);
        SiteUser siteUser = userService.getUser(principal.getName());
        questionService.like(question, siteUser);
        String url = String.format("redirect:/question/detail/%s", id);
        return url;
    }
}
