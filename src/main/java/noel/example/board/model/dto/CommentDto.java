package noel.example.board.model.dto;

import noel.example.board.persistence.entity.Comment;

import java.time.LocalDateTime;

import static noel.example.board.model.constant.CommentConstant.BLOCK_COMMENT;

public record CommentDto(
        long id,
        Long parentId,
        String text,
        Long blindAdminNo,
        LocalDateTime blindedAt,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public CommentDto(Comment comment) {
        this(
                comment.getId(),
                comment.getParentId(),
                comment.isBlocked() ? BLOCK_COMMENT : comment.getText(),
                comment.getBlockDetail().adminNo(),
                comment.getBlockDetail().blindedAt(),
                comment.getCreatedBy(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

}
