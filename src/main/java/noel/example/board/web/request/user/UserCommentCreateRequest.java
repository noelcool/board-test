package noel.example.board.web.request.user;

import jakarta.validation.constraints.NotBlank;

public record UserCommentCreateRequest(
        Long parentId,
        @NotBlank(message = "댓글/답글 내용은 필수입니다.")
        String text
) {
}
