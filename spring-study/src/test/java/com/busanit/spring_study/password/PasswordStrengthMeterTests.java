package com.busanit.spring_study.password;

import com.busanit.spring_study.buva.passord.PasswordStrength;
import com.busanit.spring_study.buva.passord.PasswordStrengthMeter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordStrengthMeterTests {

    @Test
    @DisplayName("8자 이상, 수자, 대문자. 모든 항목을 충족하는 경우")
    // 테스트 메서드들은 다 void 이다.
    void meetsAllCriteriaThenStrong() {
        PasswordStrengthMeter meter = new PasswordStrengthMeter();
        PasswordStrength result = meter.meter("ab12!ABC");
        assertEquals(PasswordStrength.STRONG, result);
    }

    @Test
    @DisplayName("8자 미만이고 다른 항목은 충족하는 경우")
    void metsOtherCriteriaExpectForLengthThenNormal() {
        PasswordStrengthMeter meter = new PasswordStrengthMeter();
        PasswordStrength result = meter.meter("ab12!@A");
        assertEquals(PasswordStrength.NORMAL, result);
    }
}
