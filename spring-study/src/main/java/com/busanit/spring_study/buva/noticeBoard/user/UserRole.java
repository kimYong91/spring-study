package com.busanit.spring_study.buva.noticeBoard.user;

import lombok.Getter;

// 만드는 순서
// 읽기만 할 것이라서 Getter만 사용
@Getter
public enum UserRole {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
