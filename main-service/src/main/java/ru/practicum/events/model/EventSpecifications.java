package ru.practicum.events.model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.List;

public class EventSpecifications {
    public static Specification<Event> withText(String text) {
        return (root, query, criteriaBuilder) -> {
            if (text != null && !text.isEmpty()) {
                String searchText = "%" + text + "%";
                Predicate annotationPredicate = criteriaBuilder.like(root.get("annotation"), searchText);
                Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), searchText);
                return criteriaBuilder.or(annotationPredicate, descriptionPredicate);
            }
            return null;
        };
    }

    public static Specification<Event> withCategories(List<Integer> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories != null && !categories.isEmpty()) {
                return root.join("category").get("id").in(categories);
            }
            return null;
        };
    }

    public static Specification<Event> withPaid(Boolean paid) {
        return (root, query, criteriaBuilder) -> {
            if (paid != null) {
                return criteriaBuilder.equal(root.get("paid"), paid);
            }
            return null;
        };
    }

    public static Specification<Event> withDateRange(LocalDateTime start, LocalDateTime end) {
        return (root, query, criteriaBuilder) -> {
            if (start != null && end != null) {
                return criteriaBuilder.between(root.get("eventDate"), start, end);
            }
            return null;
        };
    }

    public static Specification<Event> withUsers(List<Integer> users) {
        return (root, query, criteriaBuilder) -> {
            if (users != null && !users.isEmpty()) {
                return root.join("initiator").get("id").in(users);
            }
            return null;
        };
    }

    public static Specification<Event> withStates(List<State> states) {
        return (root, query, criteriaBuilder) -> {
            if (states != null && !states.isEmpty()) {
                return root.get("state").in(states);
            }
            return null;
        };
    }
}
