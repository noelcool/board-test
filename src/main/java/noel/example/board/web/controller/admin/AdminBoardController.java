package noel.example.board.web.controller.admin;

import noel.example.board.config.resolver.Admin;
import noel.example.board.model.BoardStatus;
import noel.example.board.model.common.ApiResponse;
import noel.example.board.service.admin.AdminBoardService;
import noel.example.board.web.request.admin.AdminBoardCreateRequest;
import noel.example.board.web.request.admin.AdminBoardUpdateRequest;
import noel.example.board.web.vm.admin.AdminBoardCreateVm;
import noel.example.board.web.vm.admin.AdminBoardSearchVM;
import noel.example.board.web.vm.admin.AdminBoardUpdateVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/admin/board")
public class AdminBoardController {

    private final AdminBoardService adminBoardService;

    public AdminBoardController(AdminBoardService adminBoardService) {
        this.adminBoardService = adminBoardService;
    }

    /**
     * 관리자 - 게시판 생성
     */
    @PostMapping
    public ApiResponse<AdminBoardCreateVm> createBoard(
            @RequestBody AdminBoardCreateRequest request,
            @Admin Long adminNo
    ) {
        var adminBoardDto = adminBoardService.createBoard(request, adminNo);
        return new ApiResponse<>(null,
                new AdminBoardCreateVm(adminBoardDto)
        );
    }

    /**
     * 관리자 - 게시판 수정
     */
    @PutMapping("/{boardId}")
    public ApiResponse<AdminBoardUpdateVm> updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestBody AdminBoardUpdateRequest request,
            @Admin Long adminNo
    ) {
        var adminBoardDto = adminBoardService.updateBoard(boardId, request, adminNo);
        return new ApiResponse<>(null,
                new AdminBoardUpdateVm(adminBoardDto)
        );
    }

    /**
     * 관리자 - 게시판 미사용 상태로 수정
     */
    @DeleteMapping("/{boardId}")
    public ApiResponse<Void> deleteBoard(
            @PathVariable("boardId") Long boardId,
            @Admin Long adminNo
    ) {
        adminBoardService.deleteBoard(boardId, adminNo);
        return new ApiResponse<>("게시판이 미사용 상태로 변경되었습니다.", null);
    }

    /**
     * 관리자 - 게시판 1개, 게시판 1개의 모든 댓글 목록 조회
     */
    @GetMapping("/{boardId}")
    public ApiResponse<AdminBoardSearchVM> findBoard(
            @PathVariable("boardId") Long boardId
    ) {
        return new ApiResponse<>(null, null);
    }

    /**
     * 관리자 - 게시판 목록 조회
     */
    @GetMapping("/list")
    public ApiResponse<Page<AdminBoardSearchVM>> searchBoard(
            @RequestParam(required = false, defaultValue = "ENABLED") BoardStatus boardStatus,
            @RequestParam(required = false, defaultValue = "2020-01-01") LocalDateTime startedAt,
            @RequestParam(required = false, defaultValue = "2099-12-31") LocalDateTime endedAt,
            @RequestParam(required = false, defaultValue = "") String search,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return new ApiResponse<>(null, null);
    }
}
