package noel.example.board.service.admin;

import lombok.AllArgsConstructor;
import noel.example.board.model.type.BoardStatus;
import noel.example.board.model.dto.AdminBoardDto;
import noel.example.board.model.dto.BoardPolicyDto;
import noel.example.board.web.request.admin.AdminBoardCreateRequest;
import noel.example.board.web.request.admin.AdminBoardSearchRequest;
import noel.example.board.web.request.admin.AdminBoardUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminBoardService {

    public AdminBoardDto createBoard(AdminBoardCreateRequest request, Long adminNo) {
        return null;
    }

    public AdminBoardDto updateBoard(Long boardId, AdminBoardUpdateRequest request, Long adminNo) {
        return null;
    }

    public void deleteBoard(Long boardId, Long adminNo) {

    }

    public AdminBoardDto findBoard(Long boardId) {
        return null;
    }

    public Page<AdminBoardDto> searchBoard(AdminBoardSearchRequest request, Pageable pageable) {
        return null;
    }
}
