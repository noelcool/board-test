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

    @PostMapping
    public ApiResponse<AdminCommentCreateVm> createComment(
            @RequestBody AdminCommentCreateRequest request
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    @PutMapping("/{commentId}")
    public ApiResponse<AdminCommentUpdateVm> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody AdminCommentUpdateRequest request
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable("commentId") Long commentId
    ) {
        return new ApiResponse<>(HttpStatus.OK, null);
    }

}
