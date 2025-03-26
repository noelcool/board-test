package noel.example.board.web.vm.user;

import noel.example.board.model.dto.CommentDto;

import java.time.LocalDateTime;

public record UserBoardCommentSearchVm(
        long id,
        Long parentId,
        String text,
        boolean isAuthor,
        String createdBy,
        LocalDateTime createdAt,
        boolean isLike
) {

    public UserBoardCommentSearchVm(CommentDto dto) {
        this(
                dto.id(),
                dto.parentId(),
                dto.text(),
                dto.isAuthor(),
                dto.createdBy(),
                dto.createdAt(),
                dto.isLike()
        );
    }
}
