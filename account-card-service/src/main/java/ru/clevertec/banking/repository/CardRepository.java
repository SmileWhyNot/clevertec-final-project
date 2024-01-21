package ru.clevertec.banking.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.clevertec.banking.entity.Card;

import java.util.Optional;

public interface CardRepository extends PagingAndSortingRepository<Card, String>, JpaSpecificationExecutor<Card> {

    Card save(Card card);

    Optional<Card> findCardByCardNumber(String cardNumber);

    void deleteCardByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);

    void deleteAll();
}
