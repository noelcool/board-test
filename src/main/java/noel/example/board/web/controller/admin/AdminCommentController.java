package noel.example.board.web.controller.admin;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import noel.example.board.config.resolver.Admin;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.service.admin.AdminCommentService;
import noel.example.board.web.request.admin.AdminCommentCreateRequest;
import noel.example.board.web.request.admin.AdminCommentUpdateRequest;
import noel.example.board.web.vm.admin.AdminCommentCreateVm;
import noel.example.board.web.vm.admin.AdminCommentUpdateVm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/comment")
@RequiredArgsConstructor
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    /**
     * 관리자 - 댓글/답글 생성
     */
    @PostMapping("/{boardId}")
    public ApiResponse<AdminCommentCreateVm> createComment(
            @RequestBody AdminCommentCreateRequest request,
            @Admin Long adminNo
    ) {
        var commentDto = adminCommentService.createComment(request, adminNo);
        return new ApiResponse<>(null, new AdminCommentCreateVm(commentDto));
    }

    /**
     * 관리자 - 댓글/답글 수정
     */
    @PutMapping("/{commentId}")
    public ApiResponse<AdminCommentUpdateVm> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody AdminCommentUpdateRequest request,
            @Admin Long adminNo
    ) {
        var commentDto = adminCommentService.updateComment(commentId, request, adminNo);
        return new ApiResponse<>(null, new AdminCommentUpdateVm(commentDto));
    }

    /**
     * 관리자 - 댓글/답글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable("commentId") Long commentId,
            @Admin Long adminNo
    ) {
        adminCommentService.deleteComment(commentId, adminNo);
        return new ApiResponse<>("댓글/답글이 삭제되었습니다.", null);
    }

    /**
     * 관리자 - 댓글/답글 차단
     */
    @PostMapping("/blind/{commentId}")
    public ApiResponse<Void> blindComment(
            @PathVariable("commentId") Long commentId,
            @Admin Long adminNo
    ) {
        adminCommentService.blindComment(commentId, adminNo);
        return new ApiResponse<>("댓글/답글이 차단되었습니다.", null);
    }

}
