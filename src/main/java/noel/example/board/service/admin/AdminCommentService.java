package noel.example.board.service.admin;

import lombok.RequiredArgsConstructor;
import noel.example.board.model.dto.CommentDto;
import noel.example.board.persistence.repository.CommentRepository;
import noel.example.board.web.request.admin.AdminCommentCreateRequest;
import noel.example.board.web.request.admin.AdminCommentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCommentService {

    private final CommentRepository commentRepository;

    /**
     * 관리자 - 댓글/답글 단건 조회
     */
    public CommentDto findComment(Long commentId) {
        return null;
    }

    /**
     * 관리자 - 댓글 하나의 답글 목록 조회
     */
    public Page<CommentDto> findReplies(Long commentId) {
        return null;
    }

    /**
     * 관리자 - 댓글 생성
     */
    public CommentDto createComment(AdminCommentCreateRequest request, Long adminNo) {
        return null;
    }

    /**
     * 관리자 - 댓글 수정
     */
    public CommentDto updateComment(Long commentId, AdminCommentUpdateRequest request, Long adminNo) {
        return null;
    }

    /**
     * 관리자 - 댓글/답글 삭제
     */
    public void deleteComment(Long commentId, Long adminNo) {

    }

    /**
     * 관리자 - 댓글/답글 차단
     */
    public void blindComment(Long commentId, Long adminNo) {
    }

    /**
     * 관리자 - 게시판 1개의 모든 댓글 목록 조회
     */
    public Page<CommentDto> findCommentsByBoardId(Long boardId, Pageable pageable) {
        var boards = commentRepository.findAllByBoardIdAndParentIdIsNull(boardId, pageable);
        return boards.map(CommentDto::new);
    }

}
