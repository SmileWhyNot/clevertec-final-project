package ru.clevertec.banking.repository.specifications;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FilterSpecifications<T extends Object> {
    public Specification<T> filter(String customerId) {
        return filter(customerId, null, null);
    }

    public Specification<T> filter(String customerId, String iban) {
        return filter(customerId, iban, null);
    }

    public Specification<T> filter(String customerId, String iban, String cardNumber) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(iban)
                    .filter(StringUtils::isNotBlank)
                    .ifPresent(value -> predicates.add(criteriaBuilder.equal(root.get("iban"), value)));

            Optional.ofNullable(customerId)
                    .filter(StringUtils::isNotBlank)
                    .ifPresent(value -> predicates.add(criteriaBuilder.equal(root.get("customerId"), value)));

            Optional.ofNullable(cardNumber)
                    .filter(StringUtils::isNotBlank)
                    .ifPresent(value -> predicates.add(criteriaBuilder.equal(root.get("cardNumber"), value)));


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
