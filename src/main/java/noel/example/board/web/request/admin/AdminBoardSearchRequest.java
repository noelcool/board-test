package noel.example.board.web.request.admin;

import noel.example.board.model.type.BoardStatus;

import java.time.LocalDateTime;

public record AdminBoardSearchRequest(
        BoardStatus status,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        String title
) {
}
