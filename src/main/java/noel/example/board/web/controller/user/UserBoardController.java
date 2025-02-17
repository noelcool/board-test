package noel.example.board.web.controller.user;

import lombok.RequiredArgsConstructor;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.model.common.BoardStatus;
import noel.example.board.web.vm.admin.AdminBoardSearchVM;
import noel.example.board.web.vm.user.UserBoardCommentSearchVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController("/v1/user/board")
@RequiredArgsConstructor
public class UserBoardController {

    @GetMapping("/{boardId}")
    public ApiResponse<Page<UserBoardCommentSearchVm>> searchBoardComment(
            @RequestParam(required = false, defaultValue = "") String search,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    @PostMapping("/like/{boardId}")
    public ApiResponse<Void> likeBoard(
            @PathVariable("boardId") Long boardId
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    @PutMapping("/unlike/{boardId}")
    public ApiResponse<Void> unlikeBoard(
            @PathVariable("boardId") Long boardId
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }
}
