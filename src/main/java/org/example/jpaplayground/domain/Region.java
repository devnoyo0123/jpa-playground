package org.example.jpaplayground.domain;

import org.example.jpaplayground.domain.converter.LocaleConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Region {

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10, name = "nation_cd", nullable = false)
    private CountryCode countryCode;

    @Convert(converter = LocaleConverter.class)
    @Column(length = 10, name = "locale_cd", nullable = false)
    private LocaleCode localeCode;

    private Region(CountryCode countryCode, LocaleCode localeCode) {
        this.countryCode = countryCode;
        this.localeCode = localeCode;
    }

    public static Region korea() {
        return new Region(CountryCode.KOR, LocaleCode.KO_KR);
    }
}