package noel.example.board.model.dto;

import noel.example.board.persistence.entity.Comment;

import java.time.LocalDateTime;

public record CommentDto(
        long id,
        Long parentId,
        String text,
        boolean status,
        String createdBy,
        LocalDateTime createdAt
) {

    public CommentDto(Comment comment) {
        this(
                comment.getId(),
                comment.getParentId(),
                comment.getText(),
                comment.isStatus(),
                comment.getCreatedBy(),
                comment.getCreatedAt()
        );
    }
}
