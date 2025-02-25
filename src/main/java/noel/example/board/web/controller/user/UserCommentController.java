package noel.example.board.web.controller.user;

import lombok.RequiredArgsConstructor;
import noel.example.board.config.resolver.User;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.service.admin.UserCommentService;
import noel.example.board.web.request.user.UserCommentCreateRequest;
import noel.example.board.web.request.user.UserCommentReportRequest;
import noel.example.board.web.request.user.UsersCommentUpdateRequest;
import noel.example.board.web.vm.user.UserCommentCreateVm;
import noel.example.board.web.vm.user.UserCommentUpdateVm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user/comment")
@RequiredArgsConstructor
public class UserCommentController {

    private final UserCommentService userCommentService;

    /**
     * 사용자 - 댓글 생성
     */
    @PostMapping
    public ApiResponse<UserCommentCreateVm> createComment(
            @RequestBody UserCommentCreateRequest request,
            @User Long userNo) {
        var commentDto = userCommentService.createComment(request, userNo);
        return new ApiResponse<>(null, new UserCommentCreateVm(commentDto));
    }

    /**
     * 사용자 - 댓글 수정
     */
    @PutMapping("/{commentId}")
    public ApiResponse<UserCommentUpdateVm> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody UsersCommentUpdateRequest request,
            @User Long userNo) {
        var commentDto = userCommentService.updateComment(commentId, request, userNo);
        return new ApiResponse<>(null, new UserCommentUpdateVm(commentDto));
    }

    /**
     * 사용자 - 댓글 삭제(soft)
     */
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable("commentId") Long commentId,
            @User Long userNo) {
        return new ApiResponse<>("댓글/답글이 삭제되었습니다.", null);
    }

    /**
     * 사용자 - 댓글 신고
     */
    @PostMapping("/report/{commentId}")
    public ApiResponse<Void> reportComment(
            @RequestBody UserCommentReportRequest request,
            @User Long userNo) {
        return new ApiResponse<>(null, null);
    }

    /**
     * 사용자 - 댓글 공감
     */
    @PostMapping("/like/{commentId}")
    public ApiResponse<Void> likeBoard(
            @PathVariable("commentId") Long commentId,
            @User Long userNo) {
        return new ApiResponse<>(null, null);
    }

    /**
     * 사용자 - 댓글 공감해제
     */
    @PutMapping("/unlike/{commentId}")
    public ApiResponse<Void> unlikeBoard(
            @PathVariable("commentId") Long commentId,
            @User Long userNo) {
        return new ApiResponse<>(null, null);
    }

}
