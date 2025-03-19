package noel.example.board.service.admin;

import lombok.RequiredArgsConstructor;
import noel.example.board.exception.BusinessException;
import noel.example.board.model.dto.CommentDto;
import noel.example.board.persistence.entity.Comment;
import noel.example.board.persistence.repository.CommentRepository;
import noel.example.board.web.request.admin.AdminCommentCreateRequest;
import noel.example.board.web.request.admin.AdminCommentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static noel.example.board.exception.ErrorCode.NON_EXISTENT_COMMENT;

@Service
@RequiredArgsConstructor
public class AdminCommentService {

    private final CommentRepository commentRepository;

    /**
     * 관리자 - 댓글/답글 단건 조회
     */
    @Transactional(readOnly = true)
    public CommentDto findComment(Long commentId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NON_EXISTENT_COMMENT));
        return new CommentDto(comment);
    }

    /**
     * 관리자 - 댓글 하나의 답글 목록 조회
     */
    @Transactional(readOnly = true)
    public Page<CommentDto> findReplies(Long commentId, Pageable pageable) {
        var comments = commentRepository.findAllByParentIdAndIsDeletedIsFalse(commentId, pageable);
        return comments.map(CommentDto::new);
    }

    /**
     * 관리자 - 댓글/답글 생성
     * 관리자는 게시판 정책과 상관 없이 댓글/답글 생성이 가능하다
     */
    @Transactional
    public CommentDto createComment(Long boardId, AdminCommentCreateRequest request, Long adminNo) {
        var comment = Comment.builder()
                .boardId(boardId)
                .parentId(request.parentId())
                .text(request.text())
                .createdBy(adminNo.toString())
                .build();
        commentRepository.save(comment);
        return new CommentDto(comment);
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
        var boards = commentRepository.findAllByBoardIdAndParentIdIsNullAndIsDeletedIsFalse(boardId, pageable);
        return boards.map(CommentDto::new);
    }

}
