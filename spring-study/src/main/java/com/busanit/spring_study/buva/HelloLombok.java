package com.busanit.spring_study.buva;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Getter
@Controller
public class HelloLombok {
    private String hello;
    private int lombok;

    @GetMapping("/ssb")
    @ResponseBody
    public String study() {
        return "김용";
    }
}
