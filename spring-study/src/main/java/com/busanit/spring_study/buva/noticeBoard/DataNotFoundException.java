package com.busanit.spring_study.buva.noticeBoard;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 만드는 순서 7
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1l;
    public DataNotFoundException(String message) {
        super(message);
    }
}
