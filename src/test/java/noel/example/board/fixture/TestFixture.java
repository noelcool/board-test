package noel.example.board.fixture;

import noel.example.board.model.dto.AdminBoardDto;
import noel.example.board.model.dto.BoardPolicyDto;
import noel.example.board.model.dto.CommentDto;
import noel.example.board.model.type.BoardStatus;

import java.time.LocalDateTime;

public class TestFixture {

    public static AdminBoardDto getAdminBoardDto() {
        var now = LocalDateTime.now();

        return new AdminBoardDto(
                1L,
                "",
                new BoardPolicyDto(
                        true,
                        true,
                        new BoardPolicyDto.CommentPolicyDto(
                                true,
                                true,
                                1,
                                1)
                ),
                now.minusDays(1),
                now.plusDays(1),
                BoardStatus.ENABLED,
                now.minusHours(1),
                now.plusHours(1),
                "김모카",
                "김모카");
    }

    public static BoardPolicyDto getBoardPolicyDto() {
        return new BoardPolicyDto(
                true,
                true,
                new BoardPolicyDto.CommentPolicyDto(true, true, 0, 0)
        );
    }

    public static CommentDto getCommentDto() {
        return new CommentDto(
                1L,
                null,
                "text",
                "김모카",
                LocalDateTime.now()
        );
    }
}
