package noel.example.board.service.admin;

import noel.example.board.model.dto.CommentDto;
import noel.example.board.model.type.CommentStatus;
import noel.example.board.web.request.user.UserCommentCreateRequest;
import noel.example.board.web.request.user.UsersCommentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserCommentService {

    public Page<CommentDto> getBoardComments(Pageable pageable, Long userNo) {
        var commentDtos = List.of(new CommentDto(
                1L,
                null,
                "text",
                CommentStatus.NULL,
                "김모카",
                LocalDateTime.now(),
                null
        ));
        return new PageImpl<>(commentDtos, pageable, commentDtos.size());
    }

    public CommentDto createComment(UserCommentCreateRequest request, Long userNo) {
        return null;
    }

    public CommentDto updateComment(Long commentId, UsersCommentUpdateRequest request, Long userNo) {
        return null;
    }

    public void deleteComment(Long commentId, Long userNo) {
    }
}
