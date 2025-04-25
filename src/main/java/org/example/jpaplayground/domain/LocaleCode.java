package org.example.jpaplayground.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LocaleCode {
    KO_KR("KO-KR");

    private final String code;

    LocaleCode(String code) {
        this.code = code;
    }

    public static LocaleCode find(String code) {
        return Arrays.stream(values())
                .filter(a -> a.code.equalsIgnoreCase(code))
                .findAny()
                .orElse(KO_KR);
    }
}