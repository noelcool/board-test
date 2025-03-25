package noel.example.board.service.user;

import lombok.RequiredArgsConstructor;
import noel.example.board.exception.BusinessException;
import noel.example.board.model.dto.CommentDto;
import noel.example.board.persistence.entity.Comment;
import noel.example.board.persistence.entity.CommentReport;
import noel.example.board.persistence.repository.CommentReportRepository;
import noel.example.board.persistence.repository.CommentRepository;
import noel.example.board.web.request.user.UserCommentCreateRequest;
import noel.example.board.web.request.user.UserCommentReportRequest;
import noel.example.board.web.request.user.UsersCommentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static noel.example.board.exception.ErrorCode.NON_EXISTENT_COMMENT;

@Service
@RequiredArgsConstructor
public class UserCommentService {

    private final CommentRepository commentRepository;
    private final CommentReportRepository commentReportRepository;

    public Page<CommentDto> getBoardComments(Pageable pageable, Long userNo) {
        return null;
    }

    public CommentDto createComment(UserCommentCreateRequest request, Long userNo) {
        return null;
    }

    public CommentDto updateComment(Long commentId, UsersCommentUpdateRequest request, Long userNo) {
        return null;
    }

    @Transactional
    public void deleteComment(Long commentId, Long userNo) {
        var comment = commentRepository.findByIdAndCreatedNo(commentId, userNo)
                .orElseThrow(() -> new BusinessException(NON_EXISTENT_COMMENT));
        comment.delete();
    }

    @Transactional
    public void reportComment(Long commentId, UserCommentReportRequest request, Long userNo) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NON_EXISTENT_COMMENT));
        var commentReport = CommentReport.builder()
                .commentId(commentId)
                .reason(request.reason())
                .createdNo(userNo)
                .createdBy(userNo.toString())
                .build();
        commentReportRepository.save(commentReport);

        int counted = commentReportRepository.countByCommentId(commentId);
        if(counted > 10) {
            comment.autoBlock();
            commentRepository.save(comment);
        }
    }

    public void likeComment(Long commentId, Long userNo) {
    }

    public void unlikeComment(Long commentId, Long userNo) {

    }
}
