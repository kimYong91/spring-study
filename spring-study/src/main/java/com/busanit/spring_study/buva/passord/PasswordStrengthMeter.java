package com.busanit.spring_study.buva.passord;

public class PasswordStrengthMeter {
    public PasswordStrength meter(String s) {
        if(s.length() < 8) {
            return PasswordStrength.NORMAL;
        } else {
            return PasswordStrength.STRONG;
        }
    }
}
