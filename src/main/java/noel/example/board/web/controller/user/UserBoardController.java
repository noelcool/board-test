package noel.example.board.web.controller.user;

import lombok.RequiredArgsConstructor;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.web.vm.user.UserBoardCommentSearchVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/user/board")
@RequiredArgsConstructor
public class UserBoardController {

    /**
     * 사용자 - 게시판 1개의 댓글 목록 조회
     */
    @GetMapping("/{boardId}")
    public ApiResponse<Page<UserBoardCommentSearchVm>> searchBoardComment(
            @RequestParam(required = false, defaultValue = "") String search,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return new ApiResponse<>(null, null);
    }

    /**
     * 사용자 - 게시판 공감
     */
    @PostMapping("/like/{boardId}")
    public ApiResponse<Void> likeBoard(
            @PathVariable("boardId") Long boardId
    ) {
        return new ApiResponse<>(null, null);
    }

    /**
     * 사용자 - 게시판 공감해제
     */
    @PutMapping("/unlike/{boardId}")
    public ApiResponse<Void> unlikeBoard(
            @PathVariable("boardId") Long boardId
    ) {
        return new ApiResponse<>(null, null);
    }
}
