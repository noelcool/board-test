package noel.example.board.web.controller.user;

import lombok.RequiredArgsConstructor;
import noel.example.board.resolver.User;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.service.user.UserCommentService;
import noel.example.board.web.request.user.UserCommentCreateRequest;
import noel.example.board.web.request.user.UserCommentReportRequest;
import noel.example.board.web.request.user.UsersCommentUpdateRequest;
import noel.example.board.web.vm.user.UserCommentCreateVm;
import noel.example.board.web.vm.user.UserCommentUpdateVm;
import org.springframework.web.bind.annotation.*;

import static noel.example.board.model.constant.ResponseConstant.*;

@RestController
@RequestMapping("/v1/user/comment")
@RequiredArgsConstructor
public class UserCommentController {

    private final UserCommentService userCommentService;

    /**
     * 사용자 - 댓글 생성
     */
    @PostMapping("/{boardId}")
    public ApiResponse<UserCommentCreateVm> createComment(
            @PathVariable("boardId") Long boardId,
            @RequestBody UserCommentCreateRequest request,
            @User Long userNo) {
        var dto = userCommentService.createComment(boardId, request, userNo);
        return new ApiResponse<>(null, new UserCommentCreateVm(dto));
    }

    /**
     * 사용자 - 댓글 수정
     */
    @PutMapping("/{commentId}")
    public ApiResponse<UserCommentUpdateVm> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody UsersCommentUpdateRequest request,
            @User Long userNo) {
        var dto = userCommentService.updateComment(commentId, request, userNo);
        return new ApiResponse<>(null, new UserCommentUpdateVm(dto));
    }

    /**
     * 사용자 - 댓글 삭제(soft)
     */
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable("commentId") Long commentId,
            @User Long userNo) {
        userCommentService.deleteComment(commentId, userNo);
        return new ApiResponse<>(COMMENT_DELETE_COMPLETE, null);
    }

    /**
     * 사용자 - 댓글 신고
     */
    @PostMapping("/report/{commentId}")
    public ApiResponse<Void> reportComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody UserCommentReportRequest request,
            @User Long userNo) {
        userCommentService.reportComment(commentId, request, userNo);
        return new ApiResponse<>(COMMENT_REPORT_COMPLETE, null);
    }

    /**
     * 사용자 - 댓글 공감
     */
    @PostMapping("/like/{commentId}")
    public ApiResponse<Void> likeComment(
            @PathVariable("commentId") Long commentId,
            @User Long userNo) {
        userCommentService.likeComment(commentId, userNo);
        return new ApiResponse<>(COMMENT_LIKE_COMPLETE, null);
    }

    /**
     * 사용자 - 댓글 공감해제
     */
    @PutMapping("/unlike/{commentId}")
    public ApiResponse<Void> unlikeComment(
            @PathVariable("commentId") Long commentId,
            @User Long userNo) {
        userCommentService.unlikeComment(commentId, userNo);
        return new ApiResponse<>(COMMENT_UNLIKE_COMPLETE, null);
    }

}
