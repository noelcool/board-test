package noel.example.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "관리자가 존재하지 않습니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다"),


    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다"),
    NON_EXISTENT_BOARD(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다."),
    NON_EXISTENT_COMMENT(HttpStatus.NOT_FOUND, "존재하지 않는 댓글/답글 입니다.")
    ;

    public final HttpStatus httpStatus;
    public final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
