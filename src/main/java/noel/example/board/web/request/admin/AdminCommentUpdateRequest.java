package noel.example.board.web.request.admin;

import jakarta.validation.constraints.NotBlank;

import static noel.example.board.model.constant.CommentConstant.REQUIRED_CONTENT;

public record AdminCommentUpdateRequest(
        @NotBlank(message = REQUIRED_CONTENT)
        String text
) {
}
