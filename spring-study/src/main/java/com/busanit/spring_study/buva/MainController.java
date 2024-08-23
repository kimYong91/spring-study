package com.busanit.spring_study.buva;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        System.out.println("sbb");
        return "<html><body><h1>Spring Boot Batch</h1></body></html>";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/question/list"; // '/'만 하더라도 /question/list 이곳으로 갈 수 있게 만듬
    }
}
