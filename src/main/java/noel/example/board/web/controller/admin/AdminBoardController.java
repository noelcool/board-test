package noel.example.board.web.controller.admin;

import lombok.RequiredArgsConstructor;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.resolver.Admin;
import noel.example.board.service.admin.AdminBoardService;
import noel.example.board.service.admin.AdminCommentService;
import noel.example.board.web.request.admin.AdminBoardCreateRequest;
import noel.example.board.web.request.admin.AdminBoardSearchRequest;
import noel.example.board.web.request.admin.AdminBoardUpdateRequest;
import noel.example.board.web.vm.admin.AdminBoardCreateVm;
import noel.example.board.web.vm.admin.AdminBoardSearchVm;
import noel.example.board.web.vm.admin.AdminBoardUpdateVm;
import noel.example.board.web.vm.admin.AdminCommentVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static noel.example.board.model.constant.ResponseConstant.BOARD_UPDATE_NOT_USE;

@RestController
@RequestMapping("/v1/admin/board")
@RequiredArgsConstructor
public class AdminBoardController {

    private final AdminBoardService adminBoardService;
    private final AdminCommentService adminCommentService;

    /**
     * 관리자 - 게시판 생성
     */
    @PostMapping
    public ApiResponse<AdminBoardCreateVm> createBoard(
            @RequestBody AdminBoardCreateRequest request,
            @Admin Long adminNo) {
        var dto = adminBoardService.createBoard(request, adminNo);
        return new ApiResponse<>(null, new AdminBoardCreateVm(dto));
    }

    /**
     * 관리자 - 게시판 수정
     */
    @PutMapping("/{boardId}")
    public ApiResponse<AdminBoardUpdateVm> updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestBody AdminBoardUpdateRequest request,
            @Admin Long adminNo) {
        var dto = adminBoardService.updateBoard(boardId, request, adminNo);
        return new ApiResponse<>(null, new AdminBoardUpdateVm(dto));
    }

    /**
     * 관리자 - 게시판 미사용 상태로 수정
     */
    @DeleteMapping("/{boardId}")
    public ApiResponse<Void> disableBoard(
            @PathVariable("boardId") Long boardId,
            @Admin Long adminNo) {
        adminBoardService.disableBoard(boardId, adminNo);
        return new ApiResponse<>(BOARD_UPDATE_NOT_USE, null);
    }

    /**
     * 관리자 - 게시판 1개 조회
     */
    @GetMapping("/{boardId}")
    public ApiResponse<AdminBoardSearchVm> findBoard(
            @PathVariable("boardId") Long boardId,
            @Admin Long adminNo) {
        var dto = adminBoardService.findBoard(boardId);
        return new ApiResponse<>(null, new AdminBoardSearchVm(dto));
    }

    /**
     * 관리자 - 게시판 1개의 모든 댓글 목록 조회
     */
    @GetMapping("/{boardId}/comments")
    public ApiResponse<Page<AdminCommentVm>> findComments(
            @PathVariable("boardId") Long boardId,
            @Admin Long adminNo,
            @PageableDefault(page = 1, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var dtos = adminCommentService.findCommentsByBoardId(boardId, pageable);
        return new ApiResponse<>(null, dtos.map(AdminCommentVm::new));
    }

    /**
     * 관리자 - 게시판 목록 조회
     */
    @GetMapping("/list")
    public ApiResponse<Page<AdminBoardSearchVm>> searchBoard(
            @ModelAttribute AdminBoardSearchRequest request,
            @PageableDefault(page = 1, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @Admin Long adminNo) {
        var pageableDto = adminBoardService.searchBoard(request, pageable);
        return new ApiResponse<>(null, pageableDto.map(AdminBoardSearchVm::new));
    }

}
