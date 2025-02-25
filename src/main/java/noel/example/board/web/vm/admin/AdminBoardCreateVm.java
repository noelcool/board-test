package noel.example.board.web.vm.admin;

import noel.example.board.model.type.BoardStatus;
import noel.example.board.model.dto.AdminBoardDto;
import noel.example.board.model.dto.BoardPolicyDto;

import java.time.LocalDateTime;

/**
 * id - 게시판 아이디
 * title - 제목
 * policy - 댓글/답글 정책
 * startedAt - 시작일
 * endedAt - 종료일, null 인 경우 종료일 없음
 * status - 활성화 상태
 * createdAt - 생성일
 * updatedAt - 수정일
 * createdBy - 생성자
 * updatedBy - 수정자
 */
public record AdminBoardCreateVm(
        Long id,
        String title,
        BoardPolicyDto policy,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        BoardStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
    public AdminBoardCreateVm(AdminBoardDto dto) {
        this(
                dto.id(),
                dto.title(),
                dto.policy(),
                dto.startedAt(),
                dto.endedAt(),
                dto.status(),
                dto.createdAt(),
                dto.updatedAt(),
                dto.createdBy(),
                dto.updatedBy()
        );
    }
}
