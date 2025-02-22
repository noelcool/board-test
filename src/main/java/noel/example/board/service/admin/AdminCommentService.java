package noel.example.board.service.admin;

import lombok.RequiredArgsConstructor;
import noel.example.board.model.dto.CommentDto;
import noel.example.board.model.type.CommentStatus;
import noel.example.board.web.request.admin.AdminCommentCreateRequest;
import noel.example.board.web.request.admin.AdminCommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminCommentService {

    /**
     * 관리자 - 댓글 생성
     */
    public CommentDto createComment(AdminCommentCreateRequest request, Long adminNo) {
        return new CommentDto(
                1L,
                null,
                "text",
                CommentStatus.NULL,
                "김모카",
                LocalDateTime.now(),
                null
        );
    }

    /**
     * 관리자 - 댓글 수정
     */
    public CommentDto updateComment(Long commentId, AdminCommentUpdateRequest request, Long adminNo) {
        return new CommentDto(
                1L,
                null,
                "text",
                CommentStatus.NULL,
                "김모카",
                LocalDateTime.now(),
                null
        );
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
}
