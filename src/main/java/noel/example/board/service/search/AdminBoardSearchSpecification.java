package noel.example.board.service.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import noel.example.board.model.type.BoardStatus;
import noel.example.board.persistence.entity.Board;
import noel.example.board.web.request.admin.AdminBoardSearchRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminBoardSearchSpecification {

    public static Specification<Board> search(AdminBoardSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            addStatusPredicate(predicates, root, criteriaBuilder, request.status());
            addDatePredicate(predicates, root, criteriaBuilder, request.startedAt(), request.endedAt());
            addTitlePredicate(predicates, root, criteriaBuilder, request.title());

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addStatusPredicate(List<Predicate> predicates, Root<Board> root, CriteriaBuilder criteriaBuilder, BoardStatus status) {
        if (status != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), status));
        }
    }

    private static void addDatePredicate(List<Predicate> predicates, Root<Board> root, CriteriaBuilder criteriaBuilder, LocalDateTime startedAt, LocalDateTime endedAt) {
        if (startedAt != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startedAt"), startedAt));
        }
        if (endedAt != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endedAt"), endedAt));
        }
    }

    private static void addTitlePredicate(List<Predicate> predicates, Root<Board> root, CriteriaBuilder criteriaBuilder, String title) {
        if (title != null && !title.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
        }
    }

}


