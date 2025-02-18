package noel.example.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.message);
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return errorCode.httpStatus;
    }

    public String getMessage() {
        return errorCode.message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
