package noel.example.board.web.controller.user;

import lombok.RequiredArgsConstructor;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.web.request.user.UserCommentCreateRequest;
import noel.example.board.web.request.user.UserCommentReportRequest;
import noel.example.board.web.request.user.UsersCommentUpdateRequest;
import noel.example.board.web.vm.user.UserCommentCreateVm;
import noel.example.board.web.vm.user.UserCommentUpdateVm;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/v1/user/comment")
@RequiredArgsConstructor
public class UserCommentController {

    /**
     * 사용자 - 댓글 생성
     */
    @PostMapping
    public ApiResponse<UserCommentCreateVm> createComment(
            @RequestBody UserCommentCreateRequest request
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    /**
     * 사용자 - 댓글 수정
     */
    @PutMapping("/{commentId}")
    public ApiResponse<UserCommentUpdateVm> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody UsersCommentUpdateRequest request
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    /**
     * 사용자 - 댓글 삭제(soft)
     */
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable("commentId") Long commentId
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    /**
     * 사용자 - 댓글 신고
     */
    @PostMapping("/report/{commentId}")
    public ApiResponse<Void> reportComment(
            @RequestBody UserCommentReportRequest request
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    /**
     * 사용자 - 댓글 공감
     */
    @PostMapping("/like/{commentId}")
    public ApiResponse<Void> likeBoard(
            @PathVariable("commentId") Long commentId
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    /**
     * 사용자 - 댓글 공감해제
     */
    @PutMapping("/unlike/{commentId}")
    public ApiResponse<Void> unlikeBoard(
            @PathVariable("commentId") Long commentId
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }
}
