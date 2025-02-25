package noel.example.board.web.request.admin;

import noel.example.board.model.type.BoardStatus;

import java.time.LocalDateTime;

public record AdminBoardSearchRequest(
        BoardStatus boardStatus,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        AdminBoardSearchKey searchKey,
        String searchValue
) {

    public enum AdminBoardSearchKey {
        NAME
        ;
    }

}
