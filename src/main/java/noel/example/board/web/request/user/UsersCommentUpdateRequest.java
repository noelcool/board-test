package noel.example.board.web.request.user;

import jakarta.validation.constraints.NotBlank;

import static noel.example.board.model.constant.CommentConstant.REQUIRED_CONTENT;

public record UsersCommentUpdateRequest(
        Long parentId,
        @NotBlank(message = REQUIRED_CONTENT)
        String text
) {
}
