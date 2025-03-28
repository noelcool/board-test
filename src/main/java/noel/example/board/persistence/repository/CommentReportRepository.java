package noel.example.board.persistence.repository;

import noel.example.board.persistence.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

    int countByCommentId(Long commentId);

}
