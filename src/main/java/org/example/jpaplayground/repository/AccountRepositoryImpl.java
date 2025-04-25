package org.example.jpaplayground.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.jpaplayground.domain.Account;
import org.example.jpaplayground.domain.CountryCode;
import org.example.jpaplayground.domain.QAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Account> findByCountryCodeUsingQueryDsl(CountryCode countryCode) {
        QAccount account = QAccount.account;

        return queryFactory
                .selectFrom(account)
                .where(account.region.countryCode.eq(countryCode))
                .fetch();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Account> findDeletedAccounts() {
        // Native Query 사용
        return (List<Account>) entityManager.createNativeQuery(
                        "SELECT * FROM account_info WHERE delete_yn = 'Y'",
                        Account.class
                ).getResultList().stream()
                .map(result -> (Account) result)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAccountName(Long accountId, String newName) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QAccount account = QAccount.account;

        queryFactory
                .update(account)
                .set(account.accountName, newName)
                .where(account.id.eq(accountId))
                .execute();
    }
}