package noel.example.board.persistence.repository;

import noel.example.board.persistence.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    Page<Comment> findAllByBoardIdAndParentIdIsNullAndIsDeletedIsFalse(Long boardId, Pageable pageable);

    Page<Comment> findAllByParentIdAndIsDeletedIsFalse(Long commentId, Pageable pageable);

    Optional<Comment> findByIdAndCreatedNo(Long commentId, Long createdNo);

    Optional<Comment> findByIdAndIsDeletedIsFalse(Long commentId);

    Optional<Comment> findByIdAndIsDeletedIsFalseAndCreatedNo(Long commentId, Long userNo);

    Page<Comment> findByBoardId(Long boardId, Pageable pageable);
}
