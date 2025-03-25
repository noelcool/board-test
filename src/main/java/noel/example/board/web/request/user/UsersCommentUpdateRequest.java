package noel.example.board.web.request.user;

import jakarta.validation.constraints.NotBlank;

import static noel.example.board.model.constant.CommentConstant.REQUIRED_CONTENT;

public record UsersCommentUpdateRequest(
        @NotBlank(message = REQUIRED_CONTENT)
        String text
) {
}
