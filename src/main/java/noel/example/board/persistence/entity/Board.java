package noel.example.board.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import noel.example.board.model.type.BoardStatus;
import noel.example.board.persistence.entity.model.BoardPolicy;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static noel.example.board.model.type.BoardStatus.DISABLED;

@Entity
@Table(name = "board")
@RequiredArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "policy", nullable = false)
    private BoardPolicy policy;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BoardStatus status;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Board(String title, BoardPolicy policy,
                 LocalDateTime startedAt, LocalDateTime endedAt,
                 BoardStatus status, String createdBy) {
        this.title = title;
        this.policy = policy;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.status = status;
        this.createdBy = createdBy;
    }

    public void update(String title, BoardPolicy policy,
                        LocalDateTime startedAt, LocalDateTime endedAt,
                        BoardStatus status, String updatedBy) {
        this.title = title;
        this.policy = policy;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.status = status;
        this.updatedBy = updatedBy;
    }

    public void delete(String updatedBy) {
        this.status = DISABLED;
        this.updatedBy = updatedBy;
    }
}
