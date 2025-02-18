package noel.example.board.service.admin;

import lombok.AllArgsConstructor;
import noel.example.board.model.BoardStatus;
import noel.example.board.model.dto.AdminBoardDto;
import noel.example.board.model.dto.BoardPolicyDto;
import noel.example.board.web.request.admin.AdminBoardCreateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AdminBoardService {

    public AdminBoardDto createBoard(AdminBoardCreateRequest request) {
        var now = LocalDateTime.now();
        return new AdminBoardDto(
                "",
                new BoardPolicyDto(
                        true,
                        true,
                        new BoardPolicyDto.CommentPolicy(
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

}
