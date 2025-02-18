package noel.example.board.model.common;

import noel.example.board.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
        HttpStatus httpStatus,
        String message,
        T data
) {

    public ApiResponse(String message, T data) {
        this(
                null,
                message,
                data
        );
    }

    public ApiResponse (ErrorCode errorCode) {
        this(
                errorCode.getHttpStatus(),
                errorCode.getMessage(),
                null
        );
    }
}
