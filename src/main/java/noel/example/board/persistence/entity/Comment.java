package noel.example.board.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import noel.example.board.persistence.entity.model.BlockDetail;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static noel.example.board.model.constant.CommentConstant.SYSTEM_ID;

@Entity
@Table(name = "comment")
@RequiredArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "parentId")
    private Long parentId;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked = false;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "block_detail")
    private BlockDetail blockDetail = BlockDetail.DEFAULT;

    @CreatedBy
    @Column(name = "created_no", nullable = false, updatable = false)
    private Long createdNo;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Comment(Long boardId, Long parentId, String text, String createdBy, Long createdNo) {
        this.boardId = boardId;
        this.parentId = parentId;
        this.text = text;
        this.createdBy = createdBy;
        this.createdNo = createdNo;
    }

    public void update(String text) {
        this.text = text;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void block(Long adminNo, boolean blockStatus) {
        this.isBlocked = blockStatus;
        this.blockDetail = new BlockDetail(adminNo, adminNo.toString(), LocalDateTime.now());
    }

    public void autoBlock() {
        this.isBlocked = true;
        this.blockDetail = new BlockDetail(null, SYSTEM_ID, LocalDateTime.now());
    }

}
