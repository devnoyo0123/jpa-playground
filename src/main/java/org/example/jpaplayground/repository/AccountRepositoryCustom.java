package org.example.jpaplayground.repository;

import org.example.jpaplayground.domain.Account;
import org.example.jpaplayground.domain.CountryCode;

import java.util.List;

public interface AccountRepositoryCustom {

    List<Account> findByCountryCodeUsingQueryDsl(CountryCode countryCode);

    List<Account> findDeletedAccounts();

    void updateAccountName(Long accountId, String newName);
}