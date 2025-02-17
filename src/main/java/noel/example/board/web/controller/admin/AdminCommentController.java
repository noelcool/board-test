package noel.example.board.web.controller.admin;

import lombok.RequiredArgsConstructor;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.web.request.admin.AdminCommentCreateRequest;
import noel.example.board.web.request.admin.AdminCommentUpdateRequest;
import noel.example.board.web.vm.admin.AdminCommentCreateVm;
import noel.example.board.web.vm.admin.AdminCommentUpdateVm;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/v1/admin/comment")
@RequiredArgsConstructor
public class AdminCommentController {

    /**
     * 관리자 - 댓글 생성
     * */
    @PostMapping
    public ApiResponse<AdminCommentCreateVm> createComment(
            @RequestBody AdminCommentCreateRequest request
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    /**
     * 관리자 - 댓글 수정
     * */
    @PutMapping("/{commentId}")
    public ApiResponse<AdminCommentUpdateVm> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody AdminCommentUpdateRequest request
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    /**
     * 관리자 - 댓글 삭제
     * */
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable("commentId") Long commentId
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

}
