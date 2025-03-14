package noel.example.board.web.vm.admin;

import noel.example.board.model.dto.CommentDto;

import java.time.LocalDateTime;

public record AdminCommentVm(
        long id,
        Long parentId,
        String text,
        boolean status,
        String createdBy,
        LocalDateTime createdAt
) {

    public AdminCommentVm(CommentDto dto) {
        this(
                dto.id(),
                dto.parentId(),
                dto.text(),
                dto.status(),
                dto.createdBy(),
                dto.createdAt()
        );
    }
}
