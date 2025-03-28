package noel.example.board.web.controller.user;

import lombok.RequiredArgsConstructor;
import noel.example.board.resolver.User;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.service.user.UserCommentService;
import noel.example.board.web.vm.user.UserBoardCommentSearchVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/user/board")
@RequiredArgsConstructor
public class UserBoardController {

    private final UserCommentService userCommentService;

    /**
     * 사용자 - 게시판 1개의 댓글 목록 조회
     */
    @GetMapping("/{boardId}")
    public ApiResponse<Page<UserBoardCommentSearchVm>> getBoardComments(
            @PathVariable("boardId") Long boardId,
            @PageableDefault(page = 1, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @User Long userNo) {
        var dto = userCommentService.getBoardComments(boardId, pageable, userNo);
        return new ApiResponse<>(null, dto.map(UserBoardCommentSearchVm::new));
    }

}
