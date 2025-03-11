package noel.example.board.web.vm.admin;

import noel.example.board.model.dto.CommentDto;

import java.time.LocalDateTime;

import static noel.example.board.model.constant.CommentConstant.BLIND_COMMENT;

public record AdminCommentUpdateVm(
        long id,
        Long parentId,
        String text,
        String createdBy,
        LocalDateTime createdAt
) {

    public AdminCommentUpdateVm(CommentDto dto) {
        this(
                dto.id(),
                dto.parentId(),
                dto.status() ? BLIND_COMMENT : dto.text(),
                dto.createdBy(),
                dto.createdAt()
        );
    }
}
