package noel.example.board.persistence.repository;

import noel.example.board.persistence.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentIdAndCreatedNo(Long commentId, Long userNo);

    List<CommentLike> findAllByCommentIdInAndCreatedNo(List<Long> commentIds);
}
