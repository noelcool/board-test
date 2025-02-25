package noel.example.board.web.controller.user;

import lombok.RequiredArgsConstructor;
import noel.example.board.config.resolver.User;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.service.admin.UserCommentService;
import noel.example.board.web.vm.user.UserBoardCommentSearchVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


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
            @PageableDefault(page = 1, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @User Long userNo) {
        var commentDtos = userCommentService.getBoardComments(pageable, userNo);
        return new ApiResponse<>(null,
                commentDtos.map(UserBoardCommentSearchVm::new)
        );
    }

}
