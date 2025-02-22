package noel.example.board.web.vm.admin;

import noel.example.board.model.dto.CommentDto;

import java.time.LocalDateTime;

import static noel.example.board.model.constant.CommentConstant.BLIND_COMMENT;
import static noel.example.board.model.type.CommentStatus.BLIND;

public record AdminCommentCreateVm(
        long id,
        Long parentId,
        String text,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public AdminCommentCreateVm(CommentDto dto) {
        this(
                dto.id(),
                dto.parentId(),
                BLIND.equals(dto.status()) ? BLIND_COMMENT : dto.text(),
                dto.createdBy(),
                dto.createdAt(),
                dto.updatedAt()
        );
    }
}
