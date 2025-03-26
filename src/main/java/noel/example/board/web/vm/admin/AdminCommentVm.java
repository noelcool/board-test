package noel.example.board.web.vm.admin;

import noel.example.board.model.dto.CommentDto;

import java.time.LocalDateTime;

public record AdminCommentVm(
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

    public AdminCommentVm(CommentDto dto) {
        this(
                dto.id(),
                dto.parentId(),
                dto.text(),
                dto.blindAdminNo(),
                dto.blindedAt(),
                dto.isAuthor(),
                dto.createdBy(),
                dto.createdAt(),
                dto.updatedAt(),
                dto.isLike()
        );
    }
}
