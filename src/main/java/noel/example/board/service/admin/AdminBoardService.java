package noel.example.board.service.admin;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import noel.example.board.exception.BusinessException;
import noel.example.board.model.dto.AdminBoardDto;
import noel.example.board.persistence.entity.Board;
import noel.example.board.persistence.entity.model.BoardPolicy;
import noel.example.board.persistence.repository.BoardRepository;
import noel.example.board.web.request.admin.AdminBoardCreateRequest;
import noel.example.board.web.request.admin.AdminBoardSearchRequest;
import noel.example.board.web.request.admin.AdminBoardUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static noel.example.board.exception.ErrorCode.NON_EXISTENT_BOARD;

@Service
@AllArgsConstructor
public class AdminBoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public AdminBoardDto createBoard(AdminBoardCreateRequest request, Long adminNo) {
        var policy = BoardPolicy.builder()
                .isReplyEnabled(request.policy().isReplyEnabled())
                .isCommentEnabled(request.policy().isCommentEnabled())
                .commentPolicy(new BoardPolicy.CommentPolicy(request.policy().commentPolicy()))
                .build();

        var board = Board.builder()
                .title(request.title())
                .policy(policy)
                .startedAt(request.startedAt())
                .endedAt(request.endedAt())
                .status(request.status())
                .createdBy(adminNo.toString())
                .build();
        boardRepository.save(board);
        return new AdminBoardDto(board);
    }

    @Transactional
    public AdminBoardDto updateBoard(Long boardId, AdminBoardUpdateRequest request, Long adminNo) {
        var board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessException(NON_EXISTENT_BOARD));

        var policy = BoardPolicy.builder()
                .isReplyEnabled(request.policy().isReplyEnabled())
                .isCommentEnabled(request.policy().isCommentEnabled())
                .commentPolicy(new BoardPolicy.CommentPolicy(request.policy().commentPolicy()))
                .build();

        board.update(
                request.title(),
                policy,
                request.startedAt(),
                request.endedAt(),
                request.status(),
                adminNo.toString()
        );
        return new AdminBoardDto(board);
    }

    @Transactional
    public void disableBoard(Long boardId, Long adminNo) {
        var board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessException(NON_EXISTENT_BOARD));
        board.delete(adminNo.toString());
    }

    public AdminBoardDto findBoard(Long boardId) {
        return null;
    }

    public Page<AdminBoardDto> searchBoard(AdminBoardSearchRequest request, Pageable pageable) {
        return null;
    }
}
