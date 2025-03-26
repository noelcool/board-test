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
        boolean isAuthor,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isLike
) {

    public CommentDto(Comment comment) {
        this(
                comment.getId(),
                comment.getParentId(),
                comment.isBlocked() ? BLOCK_COMMENT : comment.getText(),
                comment.getBlockDetail().adminNo(),
                comment.getBlockDetail().blindedAt(),
                false, // FIXME
                comment.getCreatedBy(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                false
        );
    }

    public CommentDto(Comment comment, Long userNo, boolean isLike) {
        this(
                comment.getId(),
                comment.getParentId(),
                comment.isBlocked() ? BLOCK_COMMENT : comment.getText(),
                comment.getBlockDetail().adminNo(),
                comment.getBlockDetail().blindedAt(),
                comment.getCreatedNo().equals(userNo),
                comment.getCreatedBy(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                isLike
        );
    }
}
