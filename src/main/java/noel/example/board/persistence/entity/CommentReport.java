package noel.example.board.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import noel.example.board.model.type.ReportReason;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_report")
@RequiredArgsConstructor
@Getter
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "reason", nullable = false)
    private ReportReason reason;

    @CreatedBy
    @Column(name = "created_no", nullable = false, updatable = false)
    private Long createdNo;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public CommentReport(Long commentId, ReportReason reason, Long createdNo, String createdBy) {
        this.commentId = commentId;
        this.reason = reason;
        this.createdNo = createdNo;
        this.createdBy = createdBy;
    }

}
