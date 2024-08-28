package com.busanit.spring_study.buva.noticeBoard.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 만드는 순서 20
@Controller
// RequestMapping() : '()'안의 주소가 이 클래스안에 만드는 모든 메서드의 공통 주소가 됨
// 예 : 메서드 주소 /create > /user/create
@RequestMapping("/user")
// 매개변수 생성자
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 매개변수를 직접적으로 사용하지 않아도 필수로 작성해야함
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            // DataIntegrityViolationException 중복 데이터 에러
            e.printStackTrace();
            bindingResult.reject("signupFailed",
                    "이미 등록된 사용자 입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed",
                    e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}
