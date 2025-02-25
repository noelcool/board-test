package noel.example.board.web.request.admin;

import noel.example.board.model.type.BoardStatus;
import noel.example.board.model.dto.BoardPolicyDto;

import java.time.LocalDateTime;

/**
 * @param title - 제목
 * @param policy - 댓글/답글 정책
 * @param startedAt - 시작일
 * @param endedAt - 종료일, null 인 경우 종료일 없음
 * @param status - 활성화 상태
 */
public record AdminBoardUpdateRequest(
        String title,
        BoardPolicyDto policy,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        BoardStatus status
) {
}
