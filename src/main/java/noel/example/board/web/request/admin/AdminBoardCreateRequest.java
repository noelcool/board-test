package noel.example.board.web.request.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import noel.example.board.model.dto.BoardPolicyDto;
import noel.example.board.model.type.BoardStatus;

import java.time.LocalDateTime;

import static noel.example.board.model.constant.BoardConstant.*;

/**
 * @param title     - 제목
 * @param policy    - 댓글/답글 정책
 * @param startedAt - 시작일
 * @param endedAt   - 종료일, null 인 경우 종료일 없음
 * @param status    - 활성화 상태
 */
public record AdminBoardCreateRequest(
        @NotBlank(message = REQUIRED_TITLE)
        String title,
        @NotBlank(message = REQUIRED_POLICY)
        BoardPolicyDto policy,
        @NotBlank(message = REQUIRED_STARTED_AT)
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        @NotBlank(message = REQUIRED_STATUS)
        BoardStatus status
) {
}
