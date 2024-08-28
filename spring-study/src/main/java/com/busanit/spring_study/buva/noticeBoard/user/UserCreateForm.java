package com.busanit.spring_study.buva.noticeBoard.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

// 만드는 순서
@Data
public class UserCreateForm {

    @Size(min = 3, max = 25) // "크기가 3에서 25 사이여야 합니다" 조건 불충족시 자동으로 텍스트가 입력됨
    @NotEmpty(message = "사용자 ID는 필수 항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    @Email // 이메일 패턴인지 자동으로 검사 (백엔드 서버에서 검사하는 것), 정규식을 사용한 것과 비슷
    private String email;
}
