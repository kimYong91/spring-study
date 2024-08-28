package com.busanit.spring_study;

import com.busanit.spring_study.buva.noticeBoard.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    UserService userService;

    @Test
    void createUser() {
        userService.create("kimyong", "rladyd@naver.com", "1234");
    }
}
