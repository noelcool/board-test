package noel.example.board.web.vm.user;

import noel.example.board.model.dto.CommentDto;

import java.time.LocalDateTime;

public record UserBoardCommentSearchVm(
        long id,
        Long parentId,
        String text,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public UserBoardCommentSearchVm(CommentDto dto) {
        this(
                dto.id(),
                dto.parentId(),
                dto.text(),
                dto.createdBy(),
                dto.createdAt(),
                dto.updatedAt()
        );
    }
}
