package noel.example.board.web.vm.admin;

import noel.example.board.model.dto.CommentDto;

import java.time.LocalDateTime;

public record AdminCommentCreateVm(
        long id,
        Long parentId,
        String text,
        String createdBy,
        LocalDateTime createdAt
) {

    public AdminCommentCreateVm(CommentDto dto) {
        this(
                dto.id(),
                dto.parentId(),
                dto.text(),
                dto.createdBy(),
                dto.createdAt()
        );
    }
}
