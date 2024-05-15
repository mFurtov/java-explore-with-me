package ru.practicum.users.model;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;

@UtilityClass
public class UserSpecifications {
    public Specification<User> withRates(List<Integer> rates) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (rates != null && !rates.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("rate").in(rates));
            }

            predicate = criteriaBuilder.and(predicate, criteriaBuilder.isNotNull(root.get("rate")));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("rate"), 0)); // Исключаем события с оценкой 0

            return predicate;
        };
    }
}
