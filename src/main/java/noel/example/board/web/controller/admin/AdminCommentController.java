package noel.example.board.web.controller.admin;

import lombok.RequiredArgsConstructor;
import noel.example.board.resolver.Admin;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.service.admin.AdminCommentService;
import noel.example.board.web.request.admin.AdminCommentCreateRequest;
import noel.example.board.web.request.admin.AdminCommentUpdateRequest;
import noel.example.board.web.vm.admin.AdminCommentCreateVm;
import noel.example.board.web.vm.admin.AdminCommentUpdateVm;
import noel.example.board.web.vm.admin.AdminCommentVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static noel.example.board.model.constant.ResponseConstant.COMMENT_BLIND_COMPLETE;
import static noel.example.board.model.constant.ResponseConstant.COMMENT_DELETE_COMPLETE;

@RestController
@RequestMapping("/v1/admin/comment")
@RequiredArgsConstructor
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    /**
     * 관리자 - 댓글/답글 단건 조회
     */
    @GetMapping("/{commentId}")
    public ApiResponse<AdminCommentVm> findComment(
            @PathVariable("commentId") Long commentId,
            @Admin Long adminNo) {
        var dto = adminCommentService.findComment(commentId);
        return new ApiResponse<>(null, new AdminCommentVm(dto));
    }

    /**
     * 관리자 - 댓글 하나의 답글 목록 조회
     */
    @GetMapping("/{commentId}/replies")
    public ApiResponse<Page<AdminCommentVm>> findReplies(
            @PathVariable("commentId") Long commentId,
            @Admin Long adminNo,
            @PageableDefault(page = 1, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var dto = adminCommentService.findReplies(commentId, pageable);
        return new ApiResponse<>(null, dto.map(AdminCommentVm::new));
    }

    /**
     * 관리자 - 댓글/답글 생성
     */
    @PostMapping("/{boardId}")
    public ApiResponse<AdminCommentCreateVm> createComment(
            @RequestBody AdminCommentCreateRequest request,
            @Admin Long adminNo) {
        var dto = adminCommentService.createComment(request, adminNo);
        return new ApiResponse<>(null, new AdminCommentCreateVm(dto));
    }

    /**
     * 관리자 - 댓글/답글 수정
     */
    @PutMapping("/{commentId}")
    public ApiResponse<AdminCommentUpdateVm> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody AdminCommentUpdateRequest request,
            @Admin Long adminNo) {
        var dto = adminCommentService.updateComment(commentId, request, adminNo);
        return new ApiResponse<>(null, new AdminCommentUpdateVm(dto));
    }

    /**
     * 관리자 - 댓글/답글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable("commentId") Long commentId,
            @Admin Long adminNo) {
        adminCommentService.deleteComment(commentId, adminNo);
        return new ApiResponse<>(COMMENT_DELETE_COMPLETE, null);
    }

    /**
     * 관리자 - 댓글/답글 차단
     */
    @PostMapping("/blind/{commentId}")
    public ApiResponse<Void> blindComment(
            @PathVariable("commentId") Long commentId,
            @Admin Long adminNo) {
        adminCommentService.blindComment(commentId, adminNo);
        return new ApiResponse<>(COMMENT_BLIND_COMPLETE, null);
    }

}
