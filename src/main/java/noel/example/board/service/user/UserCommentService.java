package noel.example.board.service.user;

import noel.example.board.model.dto.CommentDto;
import noel.example.board.web.request.user.UserCommentCreateRequest;
import noel.example.board.web.request.user.UserCommentReportRequest;
import noel.example.board.web.request.user.UsersCommentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserCommentService {

    public Page<CommentDto> getBoardComments(Pageable pageable, Long userNo) {
        return null;
    }

    public CommentDto createComment(UserCommentCreateRequest request, Long userNo) {
        return null;
    }

    public CommentDto updateComment(Long commentId, UsersCommentUpdateRequest request, Long userNo) {
        return null;
    }

    public void deleteComment(Long commentId, Long userNo) {
    }

    public void reportComment(Long commentId, UserCommentReportRequest request, Long userNo) {

    }

    public void likeComment(Long commentId, Long userNo) {
    }

    public void unlikeComment(Long commentId, Long userNo) {

    }
}
