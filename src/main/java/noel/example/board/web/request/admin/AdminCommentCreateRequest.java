package noel.example.board.web.request.admin;

import jakarta.validation.constraints.NotBlank;

import static noel.example.board.model.constant.CommentConstant.REQUIRED_CONTENT;

public record AdminCommentCreateRequest(
        Long parentId,
        @NotBlank(message = REQUIRED_CONTENT)
        String text
) {
}
