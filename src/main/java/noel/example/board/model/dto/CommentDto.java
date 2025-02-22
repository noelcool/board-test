package noel.example.board.model.dto;

import noel.example.board.model.type.CommentStatus;

import java.time.LocalDateTime;

public record CommentDto(
        long id,
        Long parentId,
        String text,
        CommentStatus status,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
