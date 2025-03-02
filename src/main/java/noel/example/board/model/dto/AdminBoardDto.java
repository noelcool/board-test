package noel.example.board.model.dto;

import noel.example.board.model.type.BoardStatus;
import noel.example.board.persistence.entity.Board;

import java.time.LocalDateTime;

/**
 * @param title - 제목
 * @param policy - 댓글/답글 정책
 * @param startedAt - 시작일
 * @param endedAt - 종료일, null 인 경우 종료일 없음
 * @param status - 활성화 상태
 * @param createdAt - 생성일
 * @param updatedAt - 수정일
 * @param createdBy - 생성자
 * @param updatedBy - 수정자
 * */
public record AdminBoardDto(
        Long id,
        String title,
        BoardPolicyDto policy,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        BoardStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {

    public AdminBoardDto(Board board) {
        this(
                board.getId(),
                board.getTitle(),
                new BoardPolicyDto(board.getPolicy()),
                board.getStartedAt(),
                board.getEndedAt(),
                board.getStatus(),
                board.getCreatedAt(),
                board.getUpdatedAt(),
                board.getCreatedBy(),
                board.getUpdatedBy()
        );
    }
}
