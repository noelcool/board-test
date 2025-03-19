package noel.example.board.model.dto;

import noel.example.board.persistence.entity.Comment;

import java.time.LocalDateTime;

import static noel.example.board.model.constant.CommentConstant.BLIND_COMMENT;

public record CommentDto(
        long id,
        Long parentId,
        String text,
        String createdBy,
        LocalDateTime createdAt,
        Long blindAdminNo,
        LocalDateTime blindedAt

) {

    public CommentDto(Comment comment) {
        this(
                comment.getId(),
                comment.getParentId(),
                comment.isBlinded() ? BLIND_COMMENT : comment.getText(),
                comment.getCreatedBy(),
                comment.getCreatedAt(),
                comment.getBlindDetail().adminNo(),
                comment.getBlindDetail().blindedAt()
        );
    }
}
