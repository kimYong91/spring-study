package com.busanit.spring_study;

import com.busanit.spring_study.buva.noticeBoard.PasswordValidator;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;

//요구사항
//비밀번호는 최소 8자 이상 12자 이하여야 한다.
//비밀번호가 8자 미만 또는 12자 초과인 경우 IllegalArgumentException 예외를 발생 시킨다.
//경계조건에 대해 테스트 코드를 작성해야 한다.

class PasswordValidatorTest {

    @DisplayName("비밀번호가 최소 8글자 이상, 12자 이하면 예외를 발생하지 않는다.")
    @Test
    void validatePassword() {
        //given
        String password = "abcdefgh";
        PasswordValidator passwordValidator = new PasswordValidator();

        //when, then
        assertThatCode(() -> passwordValidator.validate(password)).doesNotThrowAnyException();
    }

    @DisplayName("비밀번호가 8자 미만 또는 초과시 IllegalArgumentException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"aabbcce" + "aasdfasdfasdfasdf"})
    void validatePassword2( String value) {
        // given
        PasswordValidator passwordValidator = new PasswordValidator();

        // when, then
        assertThatCode(() -> passwordValidator.validate(value))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessage("비밀번호는 최소 8자 이상 12자 이하여야 합니다.");
    }
}